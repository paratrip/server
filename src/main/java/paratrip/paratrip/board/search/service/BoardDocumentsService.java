package paratrip.paratrip.board.search.service;

import static paratrip.paratrip.board.search.service.dto.response.BoardDocumentsResponseDto.*;
import static paratrip.paratrip.board.search.service.dto.response.BoardDocumentsResponseDto.GetBoardDocumentsResponseDto.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import paratrip.paratrip.board.main.entity.BoardEntity;
import paratrip.paratrip.board.main.repository.BoardImageRepository;
import paratrip.paratrip.board.main.repository.BoardRepository;
import paratrip.paratrip.board.scrap.repository.BoardScrapRepository;
import paratrip.paratrip.board.search.repository.BoardDocumentsRepository;
import paratrip.paratrip.comment.repository.CommentRepository;
import paratrip.paratrip.member.entity.MemberEntity;
import paratrip.paratrip.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class BoardDocumentsService {
	private final MemberRepository memberRepository;
	private final BoardDocumentsRepository boardDocumentsRepository;
	private final BoardRepository boardRepository;
	private final BoardImageRepository boardImageRepository;
	private final CommentRepository commentRepository;
	private final BoardScrapRepository boardScrapRepository;

	@Transactional(readOnly = true)
	public List<GetBoardDocumentsResponseDto> getBoardDocuments(Long memberSeq, String title, Pageable pageable) {
		/*
		 1. Member 유효성 검사
		*/
		MemberEntity memberEntity = memberRepository.findByMemberSeq(memberSeq);

		return boardDocumentsRepository.findByTitleContains(title, pageable)
			.stream()
			.map(boardDocuments -> {
				// Board Info 생성
				BoardEntity boardEntity = boardRepository.findByBoardSeq(boardDocuments.getBoardSeq());
				List<String> boardImageURLs = boardImageRepository.extractImageURLsByBoardEntity(boardEntity);
				BoardInfo boardInfo = new BoardInfo(
					boardEntity.getBoardSeq(),
					boardEntity.getTitle(),
					boardEntity.getContent(),
					boardEntity.getLocation(),
					boardEntity.getUpdatedAt(),
					boardImageURLs
				);

				// Board Creator Info 생성
				MemberEntity boardCreatorMemberEntity = boardEntity.getCreatorMemberEntity();
				BoardCreatorInfo boardCreatorInfo = new BoardCreatorInfo(
					boardCreatorMemberEntity.getMemberSeq(),
					boardCreatorMemberEntity.getUserId()
				);

				// Count Info 생성
				long commentCount = commentRepository.countByBoardEntity(boardEntity);
				long scrapCount = boardScrapRepository.countByBoardEntity(boardEntity);
				long hearts = boardEntity.getHearts();
				CountInfo countInfo = new CountInfo(
					commentCount,
					hearts,
					scrapCount
				);

				return new GetBoardDocumentsResponseDto(boardCreatorInfo, boardInfo, countInfo);
			})
			.collect(Collectors.toList());
	}
}
