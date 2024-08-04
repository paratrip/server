package paratrip.paratrip.board.scrap.controller.vo.request;

import static paratrip.paratrip.board.scrap.service.dto.request.BoardScarpRequestDto.*;

public class BoardScrapRequestVo {
	public record AddBoardScrapRequest(
		Long memberSeq,
		Long boardSeq
	) {
		public AddBoardScrapRequestDto toAddBoardScrapRequestDto() {
			return new AddBoardScrapRequestDto(
				this.memberSeq,
				this.boardSeq
			);
		}
	}
}
