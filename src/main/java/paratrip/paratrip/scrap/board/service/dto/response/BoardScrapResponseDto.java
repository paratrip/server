package paratrip.paratrip.scrap.board.service.dto.response;

import static paratrip.paratrip.scrap.board.controller.vo.response.BoardScrapResponseVo.*;

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
