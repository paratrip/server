package paratrip.paratrip.board.search.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import paratrip.paratrip.board.main.entity.BoardEntity;
import paratrip.paratrip.board.main.repository.BoardImageRepository;
import paratrip.paratrip.board.main.repository.BoardRepository;
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

	private final LocalDateTimeConverter converter;

	@Transactional(readOnly = true)
	public List<BoardDocumentsResponseDto.GetBoardDocumentsResponseDto> getBoardDocuments(
		String title,
		Pageable pageable
	) {
		return boardDocumentsRepository.findByTitleContains(title, pageable)
			.stream()
			.map(boardDocuments -> {
				// Board Info 생성
				BoardEntity boardEntity = boardRepository.findByBoardSeq(boardDocuments.getBoardSeq());
				List<String> boardImageURLs = boardImageRepository.extractImageURLsByBoardEntity(boardEntity);
				BoardDocumentsResponseDto.GetBoardDocumentsResponseDto.BoardInfo boardInfo
					= new BoardDocumentsResponseDto.GetBoardDocumentsResponseDto.BoardInfo(
					boardEntity.getBoardSeq(),
					boardEntity.getTitle(),
					boardEntity.getContent(),
					boardEntity.getLocation(),
					converter.convertToKoreanTime(boardEntity.getUpdatedAt()),
					boardImageURLs
				);

				// Board Creator Info 생성
				MemberEntity boardCreatorMemberEntity = boardEntity.getCreatorMemberEntity();
				BoardDocumentsResponseDto.GetBoardDocumentsResponseDto.BoardCreatorInfo boardCreatorInfo
					= new BoardDocumentsResponseDto.GetBoardDocumentsResponseDto.BoardCreatorInfo(
					boardCreatorMemberEntity.getMemberSeq(),
					boardCreatorMemberEntity.getUserId(),
					boardCreatorMemberEntity.getProfileImage()
				);

				// Count Info 생성
				long commentCount = commentRepository.countByBoardEntity(boardEntity);
				long scrapCount = boardScrapRepository.countByBoardEntity(boardEntity);
				long hearts = boardEntity.getHearts();
				BoardDocumentsResponseDto.GetBoardDocumentsResponseDto.CountInfo countInfo
					= new BoardDocumentsResponseDto.GetBoardDocumentsResponseDto.CountInfo(
					commentCount,
					hearts,
					scrapCount
				);

				return new BoardDocumentsResponseDto.GetBoardDocumentsResponseDto(
					boardCreatorInfo,
					boardInfo,
					countInfo
				);
			})
			.collect(Collectors.toList());
	}
}
