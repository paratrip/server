package paratrip.paratrip.scrap.board.service;

import static paratrip.paratrip.board.main.service.dto.response.BoardResponseDto.*;
import static paratrip.paratrip.scrap.board.service.dto.request.BoardScarpRequestDto.*;
import static paratrip.paratrip.scrap.board.service.dto.response.BoardScrapResponseDto.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import paratrip.paratrip.alarm.mapper.AlarmMapper;
import paratrip.paratrip.alarm.repository.AlarmRepository;
import paratrip.paratrip.alarm.utils.Type;
import paratrip.paratrip.board.main.entity.BoardEntity;
import paratrip.paratrip.board.main.repository.BoardImageRepository;
import paratrip.paratrip.board.main.repository.BoardRepository;
import paratrip.paratrip.core.utils.LocalDateTimeConverter;
import paratrip.paratrip.scrap.board.entity.BoardScrapEntity;
import paratrip.paratrip.scrap.board.mapper.BoardScrapMapper;
import paratrip.paratrip.scrap.board.repository.BoardScrapRepository;
import paratrip.paratrip.comment.repository.CommentRepository;
import paratrip.paratrip.member.entity.MemberEntity;
import paratrip.paratrip.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class BoardScrapService {
	private final MemberRepository memberRepository;
	private final BoardRepository boardRepository;
	private final BoardScrapRepository boardScrapRepository;
	private final CommentRepository commentRepository;
	private final BoardImageRepository boardImageRepository;
	private final AlarmRepository alarmRepository;

	private final BoardScrapMapper boardScrapMapper;
	private final AlarmMapper alarmMapper;

	private final LocalDateTimeConverter converter;

	@Transactional
	public AddBoardScrapResponseDto saveBoardScrap(AddBoardScrapRequestDto addBoardScrapRequestDto) {
		/*
		 1. Member 유효성 검사
		 2. Board 유효성 검사
		 3. 작성자 유효성 검사
		*/
		MemberEntity memberEntity = memberRepository.findByMemberSeq(addBoardScrapRequestDto.memberSeq());
		BoardEntity boardEntity = boardRepository.findByBoardSeq(addBoardScrapRequestDto.boardSeq());
		boardScrapRepository.duplicateBoardScrap(memberEntity, boardEntity);

		/*
		 1. BoardScarpEntity 저장
		 2. AlarmEntity 저장
		*/
		BoardScrapEntity boardScrapEntity = boardScrapRepository.saveBoardScrapEntity(
			boardScrapMapper.toBoardScrapEntity(memberEntity, boardEntity)
		);
		alarmRepository.saveAlarmEntity(alarmMapper.toAlarmEntity(boardEntity, memberEntity, Type.SCRAP));

		return new AddBoardScrapResponseDto(boardScrapEntity.getBoardScrapSeq());
	}

	@Transactional
	public void deleteBoardScrap(DeleteBoardScrapRequestDto deleteBoardScrapRequestDto) {
		/*
		 1. Member 유효성 검사
		 2. Board Scrap 유효성 검사
		 3. 작성자 유효성 검사
		*/
		MemberEntity memberEntity = memberRepository.findByMemberSeq(deleteBoardScrapRequestDto.memberSeq());
		boardScrapRepository.findByBoardScrapSeq(deleteBoardScrapRequestDto.boardScrapSeq());
		BoardScrapEntity boardScrapEntity = boardScrapRepository.findByMemberEntityAndBoardScrapSeq(
			memberEntity,
			deleteBoardScrapRequestDto.boardScrapSeq()
		);

		// 삭제
		boardScrapRepository.deleteBoardScrapEntity(boardScrapEntity);
	}

	@Transactional(readOnly = true)
	public Page<GetAllBoardResponseDto> getBoardScarp(Long memberSeq, Pageable pageable) {
		/*
		 1. Member 유효성 검사
		*/
		MemberEntity memberEntity = memberRepository.findByMemberSeq(memberSeq);
		Page<BoardScrapEntity> boardScrapEntityPage
			= boardScrapRepository.findAllByMemberEntity(memberEntity, pageable);

		return boardScrapEntityPage.map(boardScrapEntity -> {
			// 댓글 개수와 스크랩 개수를 계산
			long scrapCount = boardScrapRepository.countByBoardEntity(boardScrapEntity.getBoardEntity());
			long commentCount = commentRepository.countByBoardEntity(boardScrapEntity.getBoardEntity());
			List<String> imageURLs
				= boardImageRepository.extractImageURLsByBoardEntity(boardScrapEntity.getBoardEntity());

			// BoardEntity에서 필요한 정보를 추출하여 Dto로 매핑
			GetAllBoardResponseDto.AllBoardMemberInfo memberInfo = new GetAllBoardResponseDto.AllBoardMemberInfo(
				boardScrapEntity.getMemberEntity().getMemberSeq(),
				boardScrapEntity.getMemberEntity().getUserId(),
				boardScrapEntity.getMemberEntity().getProfileImage()
			);

			GetAllBoardResponseDto.AllBoardBoardInfo boardInfo = new GetAllBoardResponseDto.AllBoardBoardInfo(
				boardScrapEntity.getBoardEntity().getBoardSeq(),
				boardScrapEntity.getBoardEntity().getTitle(),
				boardScrapEntity.getBoardEntity().getLocation(),
				boardScrapEntity.getBoardEntity().getContent(),
				converter.convertToKoreanTime(boardScrapEntity.getBoardEntity().getUpdatedAt()),
				imageURLs
			);

			GetAllBoardResponseDto.AllBoardCountInfo countInfo = new GetAllBoardResponseDto.AllBoardCountInfo(
				commentCount,
				boardScrapEntity.getBoardEntity().getHearts(),
				scrapCount
			);

			return new GetAllBoardResponseDto(memberInfo, boardInfo, countInfo);
		});
	}
}
