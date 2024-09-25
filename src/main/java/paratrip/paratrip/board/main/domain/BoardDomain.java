package paratrip.paratrip.board.main.domain;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import paratrip.paratrip.board.main.entity.BoardEntity;
import paratrip.paratrip.board.main.service.dto.response.BoardResponseDto;
import paratrip.paratrip.core.utils.LocalDateTimeConverter;

@Component
@RequiredArgsConstructor
public class BoardDomain {
	private final LocalDateTimeConverter converter;

	public boolean checkImages(List<MultipartFile> images) {
		return images != null;
	}

	public BoardResponseDto.GetAllBoardResponseDto.AllBoardMemberInfo convertToAllBoardMemberInfo(
		BoardEntity boardEntity) {
		return new BoardResponseDto.GetAllBoardResponseDto.AllBoardMemberInfo(
			boardEntity.getCreatorMemberEntity().getMemberSeq(),
			boardEntity.getCreatorMemberEntity().getUserId(),
			boardEntity.getCreatorMemberEntity().getProfileImage()
		);
	}

	public BoardResponseDto.GetAllBoardResponseDto.AllBoardBoardInfo convertToAllBoardBoardInfo(
		BoardEntity boardEntity,
		List<String> imageURLs
	) {
		return new BoardResponseDto.GetAllBoardResponseDto.AllBoardBoardInfo(
			boardEntity.getBoardSeq(),
			boardEntity.getTitle(),
			boardEntity.getLocation(),
			boardEntity.getContent(),
			converter.convertToKoreanTime(boardEntity.getUpdatedAt()),
			imageURLs
		);
	}

	public BoardResponseDto.GetAllBoardResponseDto.AllBoardCountInfo convertToAllBoardCountInfo(
		BoardEntity boardEntity,
		long commentCount,
		long scrapCount
	) {
		return new BoardResponseDto.GetAllBoardResponseDto.AllBoardCountInfo(
			commentCount,
			boardEntity.getHearts(),
			scrapCount
		);
	}

	public BoardResponseDto.GetBoardResponseDto.BoardInfo convertToBoardInfo(
		BoardEntity boardEntity,
		List<String> imageURLs
	) {
		return new BoardResponseDto.GetBoardResponseDto.BoardInfo(
			boardEntity.getBoardSeq(),
			boardEntity.getTitle(),
			boardEntity.getContent(),
			boardEntity.getLocation(),
			converter.convertToKoreanTime(boardEntity.getUpdatedAt()),
			imageURLs
		);
	}

	public BoardResponseDto.GetBoardResponseDto.BoardCreatorInfo convertToBoardCreatorInfo(BoardEntity boardEntity) {
		return new BoardResponseDto.GetBoardResponseDto.BoardCreatorInfo(
			boardEntity.getCreatorMemberEntity().getMemberSeq(),
			boardEntity.getCreatorMemberEntity().getUserId(),
			boardEntity.getCreatorMemberEntity().getProfileImage()
		);
	}

	public BoardResponseDto.GetBoardResponseDto.CountInfo convertToCountInfo(
		BoardEntity boardEntity, long commentCnt, long scrapCnt, boolean heart, boolean scrap
	) {
		return new BoardResponseDto.GetBoardResponseDto.CountInfo(
			commentCnt,
			boardEntity.getHearts(),
			scrapCnt,
			heart,
			scrap
		);
	}

	public boolean checkLoginStatus(Long memberSeq) {
		if (memberSeq == -1)
			return false;
		else
			return true;
	}
}
