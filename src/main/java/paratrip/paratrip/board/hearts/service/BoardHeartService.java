package paratrip.paratrip.board.hearts.service;

import static paratrip.paratrip.board.hearts.service.dto.BoardHeartRequestDto.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import paratrip.paratrip.board.main.entity.BoardEntity;
import paratrip.paratrip.board.main.repository.BoardRepository;
import paratrip.paratrip.member.entity.MemberEntity;
import paratrip.paratrip.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class BoardHeartService {
	private final MemberRepository memberRepository;
	private final BoardRepository boardRepository;

	@Transactional
	public void increaseBoardHearts(IncreaseBoardHeartsRequestDto increaseBoardHeartsRequestDto) {
		/*
		 1. Member 유효성 검사
		 2. Board 유효성 검사
		*/
		MemberEntity memberEntity = memberRepository.findByMemberSeq(increaseBoardHeartsRequestDto.memberSeq());
		BoardEntity boardEntity = boardRepository.findByBoardSeq(increaseBoardHeartsRequestDto.boardSeq());

		// Hearts 1 증가
		BoardEntity newBoardEntity = boardEntity.increaseHearts();

		// 저장
		boardRepository.saveBoardEntity(newBoardEntity);
	}

	@Transactional
	public void decreaseBoardHearts(DecreaseBoardHeartsRequestDto decreaseBoardHeartsRequestDto) {
		/*
		 1. Member 유효성 검사
		 2. Board 유효성 검사
		*/
		MemberEntity memberEntity = memberRepository.findByMemberSeq(decreaseBoardHeartsRequestDto.memberSeq());
		BoardEntity boardEntity = boardRepository.findByBoardSeq(decreaseBoardHeartsRequestDto.boardSeq());

		// Hearts 1 감소 -> 0보다 작으면 0으로
		BoardEntity newBoardEntity = boardEntity.decreaseHearts();

		// 저장
		boardRepository.saveBoardEntity(newBoardEntity);
	}
}
