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
}
