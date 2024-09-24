package paratrip.paratrip.board.search.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import paratrip.paratrip.board.main.domain.BoardDomain;
import paratrip.paratrip.board.main.entity.BoardEntity;
import paratrip.paratrip.board.main.repository.BoardImageRepository;
import paratrip.paratrip.board.main.repository.BoardRepository;
import paratrip.paratrip.board.main.service.dto.response.BoardResponseDto;
import paratrip.paratrip.core.utils.LocalDateTimeConverter;
import paratrip.paratrip.scrap.board.repository.BoardScrapRepository;
import paratrip.paratrip.board.search.repository.BoardDocumentsRepository;
import paratrip.paratrip.board.search.service.dto.response.BoardDocumentsResponseDto;
import paratrip.paratrip.comment.repository.CommentRepository;
import paratrip.paratrip.member.entity.MemberEntity;

@Service
@RequiredArgsConstructor
public class BoardDocumentsService {
	private final BoardDocumentsRepository boardDocumentsRepository;
	private final BoardRepository boardRepository;
	private final BoardImageRepository boardImageRepository;
	private final CommentRepository commentRepository;
	private final BoardScrapRepository boardScrapRepository;

	private final BoardDomain boardDomain;

	@Transactional(readOnly = true)
	public List<BoardResponseDto.GetAllBoardResponseDto> getBoardDocuments(
		String title,
		Pageable pageable
	) {
		return boardDocumentsRepository.findByTitleContains(title, pageable)
			.stream()
			.map(boardDocuments -> {
				// Board Info 생성
				BoardEntity boardEntity = boardRepository.findByBoardSeq(boardDocuments.getBoardSeq());
				List<String> boardImageURLs = boardImageRepository.extractImageURLsByBoardEntity(boardEntity);

				// Board Info 생성
				BoardResponseDto.GetAllBoardResponseDto.AllBoardBoardInfo boardInfo
					= boardDomain.convertToAllBoardBoardInfo(boardEntity, boardImageURLs);

				// Board Creator Info 생성
				BoardResponseDto.GetAllBoardResponseDto.AllBoardMemberInfo boardCreatorInfo
					= boardDomain.convertToAllBoardMemberInfo(boardEntity);

				// Count Info 생성
				long commentCount = commentRepository.countByBoardEntity(boardEntity);
				long scrapCount = boardScrapRepository.countByBoardEntity(boardEntity);
				BoardResponseDto.GetAllBoardResponseDto.AllBoardCountInfo countInfo
					= boardDomain.convertToAllBoardCountInfo(boardEntity, commentCount, scrapCount);

				return new BoardResponseDto.GetAllBoardResponseDto(
					boardCreatorInfo,
					boardInfo,
					countInfo
				);
			})
			.collect(Collectors.toList());
	}
}
