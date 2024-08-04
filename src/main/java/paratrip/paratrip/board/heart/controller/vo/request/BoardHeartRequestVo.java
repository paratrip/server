package paratrip.paratrip.board.heart.controller.vo.request;

import static paratrip.paratrip.board.heart.service.dto.request.BoardHeartRequestDto.*;

public class BoardHeartRequestVo {
	public record AddBoardHeartRequest(
		Long memberSeq,
		Long boardSeq
	) {
		public AddBoardHeartRequestDto toAddBoardHeartRequestDto() {
			return new AddBoardHeartRequestDto(
				this.memberSeq,
				this.boardSeq
			);
		}
	}

	public record DeleteBoardHeartRequest(
		Long memberSeq,
		Long boardHeartSeq
	) {
		public DeleteBoardHeartRequestDto toDeleteBoardHeartRequestDto() {
			return new DeleteBoardHeartRequestDto(
				this.memberSeq,
				this.boardHeartSeq
			);
		}
	}
}
