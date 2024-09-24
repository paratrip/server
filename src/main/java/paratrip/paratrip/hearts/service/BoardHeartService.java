package paratrip.paratrip.hearts.service;

import static paratrip.paratrip.hearts.service.dto.request.BoardHeartRequestDto.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import paratrip.paratrip.alarm.mapper.AlarmMapper;
import paratrip.paratrip.alarm.repository.AlarmRepository;
import paratrip.paratrip.alarm.utils.Type;
import paratrip.paratrip.hearts.entity.BoardHeartEntity;
import paratrip.paratrip.hearts.mapper.BoardHeartMapper;
import paratrip.paratrip.hearts.repoisitory.BoardHeartRepository;
import paratrip.paratrip.hearts.service.dto.response.BoardHeartResponseDto;
import paratrip.paratrip.board.main.entity.BoardEntity;
import paratrip.paratrip.board.main.repository.BoardRepository;
import paratrip.paratrip.member.entity.MemberEntity;
import paratrip.paratrip.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class BoardHeartService {
	private final MemberRepository memberRepository;
	private final BoardRepository boardRepository;
	private final BoardHeartRepository boardHeartRepository;
	private final AlarmRepository alarmRepository;

	private final BoardHeartMapper boardHeartMapper;
	private final AlarmMapper alarmMapper;

	@Transactional
	public BoardHeartResponseDto.AddBoardHeartResponseDto increaseBoardHearts(
		IncreaseBoardHeartsRequestDto increaseBoardHeartsRequestDto
	) {
		/*
		 1. Member 유효성 검사
		 2. Board 유효성 검사
		*/
		MemberEntity memberEntity = memberRepository.findByMemberSeq(increaseBoardHeartsRequestDto.memberSeq());
		BoardEntity boardEntity = boardRepository.findByBoardSeq(increaseBoardHeartsRequestDto.boardSeq());

		// Hearts 1 증가
		BoardEntity newBoardEntity = boardEntity.increaseHearts();
		boardRepository.saveBoardEntity(newBoardEntity);

		/*
		 1. BoardHeartEntity 저장
		 2. AlarmEntity 저장
		*/
		BoardHeartEntity boardHeartEntity
			= boardHeartRepository.saveBoardHeartEntity(boardHeartMapper.toBoardHeartEntity(boardEntity, memberEntity));
		alarmRepository.saveAlarmEntity(alarmMapper.toAlarmEntity(boardEntity, memberEntity, Type.HEART));

		return new BoardHeartResponseDto.AddBoardHeartResponseDto(boardHeartEntity.getBoardHeartSeq());
	}

	@Transactional
	public void decreaseBoardHearts(DecreaseBoardHeartsRequestDto decreaseBoardHeartsRequestDto) {
		/*
		 1. Member 유효성 검사
		 2. Board 유효성 검사
		*/
		MemberEntity memberEntity = memberRepository.findByMemberSeq(decreaseBoardHeartsRequestDto.memberSeq());
		BoardEntity boardEntity = boardRepository.findByBoardSeq(decreaseBoardHeartsRequestDto.boardSeq());
		BoardHeartEntity boardHeartEntity
			= boardHeartRepository.findByBoardHeartSeq(decreaseBoardHeartsRequestDto.boardHeartSeq());

		// Hearts 1 감소 -> 0보다 작으면 0으로
		BoardEntity newBoardEntity = boardEntity.decreaseHearts();

		// 저장
		boardRepository.saveBoardEntity(newBoardEntity);

		boardHeartRepository.deleteBoardHeartEntity(boardHeartEntity);
	}
}
