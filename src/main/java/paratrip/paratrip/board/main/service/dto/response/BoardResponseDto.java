package paratrip.paratrip.board.main.service.dto.response;

import static paratrip.paratrip.board.main.controller.vo.response.BoardResponseVo.*;

import java.time.LocalDateTime;
import java.util.List;

public class BoardResponseDto {
	public record AddBoardResponseDto(
		Long boardSeq
	) {
		public AddBoardResponse toAddBoardResponse() {
			return new AddBoardResponse(
				this.boardSeq
			);
		}
	}

	public record GetAllBoardResponseDto(
		MemberInfo memberInfo,
		BoardInfo boardInfo,
		CountInfo countInfo
	) {
		public record MemberInfo(
			String userId
		) {

		}

		public record BoardInfo(
			Long boardSeq,
			String title,
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
