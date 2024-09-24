package paratrip.paratrip.board.main.service;

import static paratrip.paratrip.board.main.service.dto.request.BoardRequestDto.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import paratrip.paratrip.alarm.repository.AlarmRepository;
import paratrip.paratrip.comment.domain.CommentDomain;
import paratrip.paratrip.comment.entity.CommentEntity;
import paratrip.paratrip.core.utils.LocalDateTimeConverter;
import paratrip.paratrip.hearts.repoisitory.BoardHeartRepository;
import paratrip.paratrip.board.main.domain.BoardDomain;
import paratrip.paratrip.board.main.entity.BoardEntity;
import paratrip.paratrip.board.main.entity.BoardImageEntity;
import paratrip.paratrip.board.main.mapper.BoardImageMapper;
import paratrip.paratrip.board.main.mapper.BoardMapper;
import paratrip.paratrip.board.main.repository.BoardImageRepository;
import paratrip.paratrip.board.main.repository.BoardRepository;
import paratrip.paratrip.board.main.service.dto.response.BoardResponseDto;
import paratrip.paratrip.scrap.board.repository.BoardScrapRepository;
import paratrip.paratrip.board.search.mapper.BoardDocumentsMapper;
import paratrip.paratrip.board.search.repository.BoardDocumentsRepository;
import paratrip.paratrip.comment.repository.CommentRepository;
import paratrip.paratrip.member.entity.MemberEntity;
import paratrip.paratrip.member.repository.MemberRepository;
import paratrip.paratrip.s3.domain.S3Domain;

@Service
@RequiredArgsConstructor
public class BoardService {
	private final MemberRepository memberRepository;
	private final BoardRepository boardRepository;
	private final BoardImageRepository boardImageRepository;
	private final BoardDocumentsRepository boardDocumentsRepository;
	private final BoardScrapRepository boardScrapRepository;
	private final CommentRepository commentRepository;
	private final BoardHeartRepository boardHeartRepository;
	private final AlarmRepository alarmRepository;

	private final S3Domain s3Domain;
	private final BoardDomain boardDomain;
	private final CommentDomain commentDomain;

	private final BoardMapper boardMapper;
	private final BoardImageMapper boardImageMapper;
	private final BoardDocumentsMapper boardDocumentsMapper;

	@Transactional
	public BoardResponseDto.AddBoardResponseDto saveBoard(AddBoardRequestDto addBoardRequestDto) throws IOException {
		/*
		 1. MemberSeq 유효성 검사
		*/
		MemberEntity memberEntity = memberRepository.findByMemberSeq(addBoardRequestDto.memberSeq());

		BoardEntity boardEntity = boardMapper.toBoardEntity(
			addBoardRequestDto.title(),
			addBoardRequestDto.content(),
			addBoardRequestDto.location(),
			0L,
			memberEntity
		);

		boardEntity = boardRepository.saveBoardEntity(boardEntity);

		// BoardDocumentsEntity 저장
		boardDocumentsRepository.saveBoardDocuments(boardDocumentsMapper.toBoardDocuments(
			boardEntity.getBoardSeq(),
			boardEntity.getTitle()
		));

		// Board Image Entity 저장
		if (boardDomain.checkImages(addBoardRequestDto.images())) {
			for (MultipartFile multipartFile : addBoardRequestDto.images()) {
				String imageURL = s3Domain.uploadMultipartFile(multipartFile);
				boardImageRepository.saveBoardImageEntity(boardImageMapper.toBoardImageEntity(boardEntity, imageURL));
			}
		}

		return new BoardResponseDto.AddBoardResponseDto(boardEntity.getBoardSeq());
	}

	@Transactional
	public void modifyBoard(ModifyBoardRequestDto modifyBoardRequestDto) throws IOException {
		/*
		 1. MemberSeq 유효성 검사
		 2. BoardSeq 유효성 검사
		 3. 작성자 확인
		*/
		MemberEntity memberEntity = memberRepository.findByMemberSeq(modifyBoardRequestDto.memberSeq());
		boardRepository.findByBoardSeq(modifyBoardRequestDto.boardSeq());
		BoardEntity boardEntity
			= boardRepository.findByCreatorMemberEntityAndBoardSeq(memberEntity, modifyBoardRequestDto.boardSeq());

		// Board Image Entity 전체 삭제 (Update 불가능)
		List<BoardImageEntity> boardImageEntities = boardImageRepository.findAllByBoardEntity(boardEntity);
		boardImageRepository.deleteAll(boardImageEntities);

		// Update BoardEntity
		BoardEntity updateBoardEntity = boardEntity.updateBoardEntity(
			modifyBoardRequestDto.title(),
			modifyBoardRequestDto.content(),
			modifyBoardRequestDto.location()
		);
		boardRepository.saveBoardEntity(updateBoardEntity);

		// Board Image Entity 저장
		if (boardDomain.checkImages(modifyBoardRequestDto.images())) {
			for (MultipartFile multipartFile : modifyBoardRequestDto.images()) {
				String imageURL = s3Domain.uploadMultipartFile(multipartFile);
				boardImageRepository.saveBoardImageEntity(boardImageMapper.toBoardImageEntity(boardEntity, imageURL));
			}
		}
	}

	@Transactional(readOnly = true)
	public Page<BoardResponseDto.GetAllBoardResponseDto> getAllBoard(Pageable pageable) {
		Page<BoardEntity> boardEntityPage = boardRepository.findAllBoardEntity(pageable);

		return boardEntityPage.map(boardEntity -> {
			// 댓글 개수와 스크랩 개수를 계산
			long scrapCount = boardScrapRepository.countByBoardEntity(boardEntity);
			long commentCount = commentRepository.countByBoardEntity(boardEntity);
			List<String> imageURLs = boardImageRepository.extractImageURLsByBoardEntity(boardEntity);

			// MemberInfo 생성
			BoardResponseDto.GetAllBoardResponseDto.AllBoardMemberInfo memberInfo
				= boardDomain.convertToAllBoardMemberInfo(boardEntity);

			// BoardInfo 생성
			BoardResponseDto.GetAllBoardResponseDto.AllBoardBoardInfo boardInfo
				= boardDomain.convertToAllBoardBoardInfo(boardEntity, imageURLs);

			// CountInfo 생성
			BoardResponseDto.GetAllBoardResponseDto.AllBoardCountInfo countInfo
				= boardDomain.convertToAllBoardCountInfo(boardEntity, commentCount, scrapCount);

			return new BoardResponseDto.GetAllBoardResponseDto(memberInfo, boardInfo, countInfo);
		});
	}

	@Transactional(readOnly = true)
	public BoardResponseDto.GetBoardResponseDto getBoard(Long boardSeq, Long memberSeq) {
		if (boardDomain.checkLoginStatus(memberSeq))
			return getBoardDetailsLoginMember(memberSeq, boardSeq);
		else
			return getBoardDetailsNotLoginMember(boardSeq);
	}

	@Transactional(readOnly = true)
	public BoardResponseDto.GetBoardResponseDto getBoardDetailsLoginMember(Long memberSeq, Long boardSeq) {
		MemberEntity memberEntity = memberRepository.findByMemberSeq(memberSeq);
		BoardEntity boardEntity = boardRepository.findByBoardSeq(boardSeq);
		List<String> imageURLs = boardImageRepository.extractImageURLsByBoardEntity(boardEntity);

		// Board Info 생성
		BoardResponseDto.GetBoardResponseDto.BoardInfo boardInfo
			= boardDomain.convertToBoardInfo(boardEntity, imageURLs);

		// Board Creator Info 생성
		BoardResponseDto.GetBoardResponseDto.BoardCreatorInfo boardCreatorInfo
			= boardDomain.convertToBoardCreatorInfo(boardEntity);

		/*
		 Comment Info 생성
		 TODO COMMENT 조회 API로 따로 분리
		*/
		List<CommentEntity> commentEntities = commentRepository.findByBoardEntity(boardEntity);
		List<BoardResponseDto.GetBoardResponseDto.CommentInfo> commentInfos
			= commentDomain.convertToCommentInfos(commentEntities);

		// Count Info 생성
		long commentCnt = commentRepository.countByBoardEntity(boardEntity);
		long scrapCnt = boardScrapRepository.countByBoardEntity(boardEntity);
		boolean heart = boardHeartRepository.existsByBoardEntityAndMemberEntity(boardEntity, memberEntity);
		boolean scrap = boardScrapRepository.existsByBoardEntityAndMemberEntity(memberEntity, boardEntity);
		BoardResponseDto.GetBoardResponseDto.CountInfo countInfo
			= boardDomain.convertToCountInfo(boardEntity, commentCnt, scrapCnt, heart, scrap);

		return new BoardResponseDto.GetBoardResponseDto(boardCreatorInfo, boardInfo, countInfo, commentInfos);
	}

	@Transactional(readOnly = true)
	public BoardResponseDto.GetBoardResponseDto getBoardDetailsNotLoginMember(Long boardSeq) {
		BoardEntity boardEntity = boardRepository.findByBoardSeq(boardSeq);
		List<String> imageURLs = boardImageRepository.extractImageURLsByBoardEntity(boardEntity);

		// Board Info 생성
		BoardResponseDto.GetBoardResponseDto.BoardInfo boardInfo
			= boardDomain.convertToBoardInfo(boardEntity, imageURLs);

		// Board Creator Info 생성
		BoardResponseDto.GetBoardResponseDto.BoardCreatorInfo boardCreatorInfo
			= boardDomain.convertToBoardCreatorInfo(boardEntity);

		/*
		 Comment Info 생성
		 TODO COMMENT 조회 API로 따로 분리
		*/
		List<CommentEntity> commentEntities = commentRepository.findByBoardEntity(boardEntity);
		List<BoardResponseDto.GetBoardResponseDto.CommentInfo> commentInfos
			= commentDomain.convertToCommentInfos(commentEntities);

		// Count Info 생성
		long commentCnt = commentRepository.countByBoardEntity(boardEntity);
		long scrapCnt = boardScrapRepository.countByBoardEntity(boardEntity);
		BoardResponseDto.GetBoardResponseDto.CountInfo countInfo
			= boardDomain.convertToCountInfo(boardEntity, commentCnt, scrapCnt, false, false);

		return new BoardResponseDto.GetBoardResponseDto(boardCreatorInfo, boardInfo, countInfo, commentInfos);
	}

	@Transactional(readOnly = true)
	public List<BoardResponseDto.GetAllBoardResponseDto> getPopularityBoard(Pageable pageable) {
		Page<BoardEntity> boardEntityPage = boardRepository.findByPopularity(pageable);

		return boardEntityPage.stream()
			.map(boardEntity -> {
				// 댓글 개수와 스크랩 개수를 계산
				long scrapCount = boardScrapRepository.countByBoardEntity(boardEntity);
				long commentCount = commentRepository.countByBoardEntity(boardEntity);
				List<String> imageURLs = boardImageRepository.extractImageURLsByBoardEntity(boardEntity);

				// Board Info 생성
				BoardResponseDto.GetAllBoardResponseDto.AllBoardBoardInfo boardInfo
					= boardDomain.convertToAllBoardBoardInfo(boardEntity, imageURLs);

				// Board Creator Member Info 생성
				BoardResponseDto.GetAllBoardResponseDto.AllBoardMemberInfo memberInfo
					= boardDomain.convertToAllBoardMemberInfo(boardEntity);

				// Count Info 생성
				BoardResponseDto.GetAllBoardResponseDto.AllBoardCountInfo countInfo
					= boardDomain.convertToAllBoardCountInfo(boardEntity, commentCount, scrapCount);

				return new BoardResponseDto.GetAllBoardResponseDto(memberInfo, boardInfo, countInfo);
			})
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public Page<BoardResponseDto.GetAllBoardResponseDto> myBoard(Long memberSeq, Pageable pageable) {
		/*
		 1. Member 유효성 검사
		*/
		MemberEntity memberEntity = memberRepository.findByMemberSeq(memberSeq);
		Page<BoardEntity> boardEntityPage = boardRepository.findAllMyBoardEntity(memberEntity, pageable);

		return boardEntityPage.map(boardEntity -> {
			// 댓글 개수와 스크랩 개수를 계산
			long scrapCount = boardScrapRepository.countByBoardEntity(boardEntity);
			long commentCount = commentRepository.countByBoardEntity(boardEntity);
			List<String> imageURLs = boardImageRepository.extractImageURLsByBoardEntity(boardEntity);

			// Board Info 생성
			BoardResponseDto.GetAllBoardResponseDto.AllBoardBoardInfo boardInfo
				= boardDomain.convertToAllBoardBoardInfo(boardEntity, imageURLs);

			// Member Info 생성
			BoardResponseDto.GetAllBoardResponseDto.AllBoardMemberInfo memberInfo
				= boardDomain.convertToAllBoardMemberInfo(boardEntity);

			// Count Info 생성
			BoardResponseDto.GetAllBoardResponseDto.AllBoardCountInfo countInfo
				= boardDomain.convertToAllBoardCountInfo(boardEntity, commentCount, scrapCount);

			return new BoardResponseDto.GetAllBoardResponseDto(memberInfo, boardInfo, countInfo);
		});
	}

	@Transactional
	public void deleteBoard(DeleteBoardRequestDto request) {
		/*
		 1. Member 유효성 검사
		 2. 작성자 검사
		*/
		MemberEntity memberEntity = memberRepository.findByMemberSeq(request.memberSeq());
		BoardEntity boardEntity
			= boardRepository.findByCreatorMemberEntityAndBoardSeq(memberEntity, request.boardSeq());

		/*
		 [ 고아객체 삭제 ]
		 1. Board Image 삭제
		 2. Board Heart 삭제
		 3. Board Scrap 삭제
		 4. Alarm 삭제
		 5. Comment 삭제

		 -> Board 삭제
		*/
		boardImageRepository.deleteByBoardEntity(boardEntity);
		boardHeartRepository.deleteByBoardEntity(boardEntity);
		boardScrapRepository.deleteByBoardEntity(boardEntity);
		alarmRepository.deleteByBoardEntity(boardEntity);
		commentRepository.deleteByBoardEntity(boardEntity);
		boardRepository.deleteBoardEntity(boardEntity);
	}
}
