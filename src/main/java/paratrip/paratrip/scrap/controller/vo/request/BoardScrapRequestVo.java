package paratrip.paratrip.scrap.controller.vo.request;

import static paratrip.paratrip.scrap.service.dto.request.BoardScarpRequestDto.*;

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

	public record DeleteBoardScrapRequest(
		Long memberSeq,
		Long boardScrapSeq
	) {
		public DeleteBoardScrapRequestDto toDeleteBoardScrapRequestDto() {
			return new DeleteBoardScrapRequestDto(
				this.memberSeq,
				this.boardScrapSeq
			);
		}
	}
}
