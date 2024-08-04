package paratrip.paratrip.board.scrap.service.dto.response;

import static paratrip.paratrip.board.scrap.controller.vo.response.BoardScrapResponseVo.*;

public class BoardScrapResponseDto {
	public record AddBoardScrapResponseDto(
		Long boardScarpSeq
	) {
		public AddBoardScrapResponse toAddBoardScrapResponse() {
			return new AddBoardScrapResponse(
				this.boardScarpSeq
			);
		}
	}
}
