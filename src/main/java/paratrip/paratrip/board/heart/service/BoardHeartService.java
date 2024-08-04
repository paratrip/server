package paratrip.paratrip.board.heart.service;

import static paratrip.paratrip.board.heart.service.dto.request.BoardHeartRequestDto.*;
import static paratrip.paratrip.board.heart.service.dto.response.BoardHeartResponseDto.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import paratrip.paratrip.board.heart.entity.BoardHeartEntity;
import paratrip.paratrip.board.heart.mapper.BoardHeartMapper;
import paratrip.paratrip.board.heart.repository.BoardHeartRepository;
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
	public AddBoardHeartResponseDto saveBoardHeart(AddBoardHeartRequestDto addBoardHeartRequestDto) {
		/*
		 1. Member 유효성 검사
		 2. Board 유효성 검사
		 3. 작성자 유효성 검사
		*/
		MemberEntity memberEntity = memberRepository.findByMemberSeq(addBoardHeartRequestDto.memberSeq());
		boardRepository.findByBoardSeq(addBoardHeartRequestDto.boardSeq());
		BoardEntity boardEntity = boardRepository.findByMemberEntity(memberEntity);

		// BoardHeart 저장
		BoardHeartEntity boardHeartEntity
			= boardHeartRepository.saveBoardHeartEntity(boardHeartMapper.toBoardHeartEntity(memberEntity, boardEntity));

		return new AddBoardHeartResponseDto(boardHeartEntity.getBoardHeartSeq());
	}
}
