package paratrip.paratrip.board.main.service;

import static paratrip.paratrip.board.main.service.dto.request.BoardRequestDto.*;
import static paratrip.paratrip.board.main.service.dto.response.BoardResponseDto.*;
import static paratrip.paratrip.board.main.service.dto.response.BoardResponseDto.GetAllBoardResponseDto.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import paratrip.paratrip.board.main.domain.BoardDomain;
import paratrip.paratrip.board.main.entity.BoardEntity;
import paratrip.paratrip.board.main.entity.BoardImageEntity;
import paratrip.paratrip.board.main.mapper.BoardImageMapper;
import paratrip.paratrip.board.main.mapper.BoardMapper;
import paratrip.paratrip.board.main.repository.BoardImageRepository;
import paratrip.paratrip.board.main.repository.BoardRepository;
import paratrip.paratrip.board.scrap.repository.BoardScrapRepository;
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

	private final S3Domain s3Domain;
	private final BoardDomain boardDomain;

	private final BoardMapper boardMapper;
	private final BoardImageMapper boardImageMapper;
	private final BoardDocumentsMapper boardDocumentsMapper;

	@Transactional
	public AddBoardResponseDto saveBoard(AddBoardRequestDto addBoardRequestDto) throws IOException {
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

		return new AddBoardResponseDto(boardEntity.getBoardSeq());
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
	public Page<GetAllBoardResponseDto> getAllBoard(Pageable pageable) {
		Page<BoardEntity> boardEntityPage = boardRepository.findAllBoardEntity(pageable);

		return boardEntityPage.map(boardEntity -> {
			// 댓글 개수와 스크랩 개수를 계산
			long scrapCount = boardScrapRepository.countByBoardEntity(boardEntity);
			long commentCount = commentRepository.countByBoardEntity(boardEntity);
			List<String> imageURLs = boardImageRepository.extractImageURLsByBoardEntity(boardEntity);

			// BoardEntity에서 필요한 정보를 추출하여 Dto로 매핑
			MemberInfo memberInfo = new MemberInfo(
				boardEntity.getCreatorMemberEntity().getMemberSeq(),
				boardEntity.getCreatorMemberEntity().getUserId(),
				boardEntity.getCreatorMemberEntity().getProfileImage()
			);

			BoardInfo boardInfo = new BoardInfo(
				boardEntity.getBoardSeq(),
				boardEntity.getTitle(),
				boardEntity.getLocation(),
				boardEntity.getUpdatedAt(),
				imageURLs
			);

			CountInfo countInfo = new CountInfo(
				commentCount,
				boardEntity.getHearts(),
				scrapCount
			);

			return new GetAllBoardResponseDto(memberInfo, boardInfo, countInfo);
		});
	}

	@Transactional(readOnly = true)
	public GetBoardResponseDto getBoard(Long boardSeq) {
		/*
		 1. MemberSeq 유효성 검사
		 2. BoardSeq 유효성 검사
		*/
		BoardEntity boardEntity = boardRepository.findByBoardSeq(boardSeq);
		List<String> imageURLs = boardImageRepository.extractImageURLsByBoardEntity(boardEntity);

		// Board Info 생성
		GetBoardResponseDto.BoardInfo boardInfo = new GetBoardResponseDto.BoardInfo(
			boardEntity.getBoardSeq(),
			boardEntity.getTitle(),
			boardEntity.getContent(),
			boardEntity.getLocation(),
			boardEntity.getUpdatedAt(),
			imageURLs
		);

		// Board Creator Info 생성
		GetBoardResponseDto.BoardCreatorInfo boardCreatorInfo = new GetBoardResponseDto.BoardCreatorInfo(
			boardEntity.getCreatorMemberEntity().getMemberSeq(),
			boardEntity.getCreatorMemberEntity().getUserId(),
			boardEntity.getCreatorMemberEntity().getProfileImage()
		);

		// Count Info 생성
		GetBoardResponseDto.CountInfo countInfo = new GetBoardResponseDto.CountInfo(
			commentRepository.countByBoardEntity(boardEntity),
			false, // boardHeart는 현재 false로 고정
			// boardScrapRepository.existsByBoardEntityAndMemberEntity(memberEntity, boardEntity)
			false
		);

		// Comment Info 생성
		List<GetBoardResponseDto.CommentInfo> commentInfos = commentRepository.findByBoardEntity(boardEntity)
			.stream()
			.map(commentEntity -> new GetBoardResponseDto.CommentInfo(
				commentEntity.getCommentSeq(),
				commentEntity.getComment(),
				commentEntity.getUpdatedAt(),
				commentEntity.getMemberEntity().getMemberSeq(),
				commentEntity.getMemberEntity().getUserId(),
				commentEntity.getMemberEntity().getProfileImage()
			))
			.collect(Collectors.toList());

		return new GetBoardResponseDto(boardCreatorInfo, boardInfo, countInfo, commentInfos);
	}

	@Transactional(readOnly = true)
	public List<GetPopularityBoardResponseDto> getPopularityBoard(Pageable pageable) {
		Page<BoardEntity> boardEntityPage = boardRepository.findByPopularity(pageable);

		// Stream API를 사용하여 DTO로 변환
		return boardEntityPage.stream()
			.map(boardEntity -> {
				// Image 추출
				List<String> imageURLs = boardImageRepository.extractImageURLsByBoardEntity(boardEntity);

				// Board Info 생성
				GetPopularityBoardResponseDto.BoardInfo boardInfo = new GetPopularityBoardResponseDto.BoardInfo(
					boardEntity.getBoardSeq(),
					boardEntity.getTitle(),
					imageURLs
				);

				// Board Creator Member Info 생성
				GetPopularityBoardResponseDto.BoardCreatorMemberInfo boardCreatorMemberInfo = new GetPopularityBoardResponseDto.BoardCreatorMemberInfo(
					boardEntity.getCreatorMemberEntity().getMemberSeq(),
					boardEntity.getCreatorMemberEntity().getUserId(),
					boardEntity.getCreatorMemberEntity().getProfileImage()
				);

				// 최종 DTO 생성
				return new GetPopularityBoardResponseDto(boardCreatorMemberInfo, boardInfo);
			})
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public Page<GetAllBoardResponseDto> myBoard(Long memberSeq, Pageable pageable) {
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

			// BoardEntity에서 필요한 정보를 추출하여 Dto로 매핑
			MemberInfo memberInfo = new MemberInfo(
				boardEntity.getCreatorMemberEntity().getMemberSeq(),
				boardEntity.getCreatorMemberEntity().getUserId(),
				boardEntity.getCreatorMemberEntity().getProfileImage()
			);

			BoardInfo boardInfo = new BoardInfo(
				boardEntity.getBoardSeq(),
				boardEntity.getTitle(),
				boardEntity.getLocation(),
				boardEntity.getUpdatedAt(),
				imageURLs
			);

			CountInfo countInfo = new CountInfo(
				commentCount,
				boardEntity.getHearts(),
				scrapCount
			);

			return new GetAllBoardResponseDto(memberInfo, boardInfo, countInfo);
		});
	}
}
