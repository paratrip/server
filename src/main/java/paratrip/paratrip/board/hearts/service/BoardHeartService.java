package paratrip.paratrip.board.hearts.service;

import static paratrip.paratrip.board.hearts.service.dto.request.BoardHeartRequestDto.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import paratrip.paratrip.board.hearts.entity.BoardHeartEntity;
import paratrip.paratrip.board.hearts.mapper.BoardHeartMapper;
import paratrip.paratrip.board.hearts.repoisitory.BoardHeartRepository;
import paratrip.paratrip.board.hearts.service.dto.response.BoardHeartResponseDto;
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

	private final BoardHeartMapper boardHeartMapper;

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
		BoardHeartEntity boardHeartEntity
			= boardHeartRepository.saveBoardHeartEntity(boardHeartMapper.toBoardHeartEntity(boardEntity, memberEntity));

		// 저장
		boardRepository.saveBoardEntity(newBoardEntity);

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
