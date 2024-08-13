package paratrip.paratrip.board.search.service.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public class BoardDocumentsResponseDto {
	public record GetBoardDocumentsResponseDto(
		BoardCreatorInfo boardCreatorInfo,
		BoardInfo boardInfo,
		CountInfo countInfo
	) {
		public record BoardCreatorInfo(
			Long memberSeq,
			String userId
		) {

		}

		public record BoardInfo(
			Long boardSeq,
			String title,
			String content,
			String location,
			LocalDateTime updatedAt,
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
