package paratrip.paratrip.board.scrap.service;

import static paratrip.paratrip.board.scrap.service.dto.request.BoardScarpRequestDto.*;
import static paratrip.paratrip.board.scrap.service.dto.response.BoardScrapResponseDto.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import paratrip.paratrip.board.main.entity.BoardEntity;
import paratrip.paratrip.board.main.repository.BoardRepository;
import paratrip.paratrip.board.scrap.entity.BoardScrapEntity;
import paratrip.paratrip.board.scrap.mapper.BoardScrapMapper;
import paratrip.paratrip.board.scrap.repository.BoardScrapRepository;
import paratrip.paratrip.member.entity.MemberEntity;
import paratrip.paratrip.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class BoardScrapService {
	private final MemberRepository memberRepository;
	private final BoardRepository boardRepository;
	private final BoardScrapRepository boardScrapRepository;

	private final BoardScrapMapper boardScrapMapper;

	@Transactional
	public AddBoardScrapResponseDto saveBoardScrap(AddBoardScrapRequestDto addBoardScrapRequestDto) {
		/*
		 1. Member 유효성 검사
		 2. Board 유효성 검사
		 3. 작성자 유효성 검사
		*/
		MemberEntity memberEntity = memberRepository.findByMemberSeq(addBoardScrapRequestDto.memberSeq());
		boardRepository.findByBoardSeq(addBoardScrapRequestDto.boardSeq());
		BoardEntity boardEntity
			= boardRepository.findByCreatorMemberEntityAndBoardSeq(memberEntity, addBoardScrapRequestDto.boardSeq());

		// 저장
		BoardScrapEntity boardScrapEntity = boardScrapRepository.saveBoardScrapEntity(
			boardScrapMapper.toBoardScrapEntity(memberEntity, boardEntity)
		);

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
}
