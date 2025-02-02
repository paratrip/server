package paratrip.paratrip.board.search.service.dto.response;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

public class BoardDocumentsResponseDto {
	public record GetBoardDocumentsResponseDto(
		GetBoardDocumentsResponseDto.BoardCreatorInfo boardCreatorInfo,
		GetBoardDocumentsResponseDto.BoardInfo boardInfo,
		GetBoardDocumentsResponseDto.CountInfo countInfo
	) {
		public record BoardCreatorInfo(
			Long memberSeq,
			String userId,
			String profileImage
		) {

		}

		public record BoardInfo(
			Long boardSeq,
			String title,
			String content,
			String location,
			ZonedDateTime updatedAt,
			List<String> imageURLs
		) {

		}

		public record CountInfo(
			Long commentCnt,
			Long heartCnt,
			Long scrapCnt
		) {

		}
	}
}
