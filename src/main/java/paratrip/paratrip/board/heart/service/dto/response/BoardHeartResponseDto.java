package paratrip.paratrip.board.heart.service.dto.response;

import static paratrip.paratrip.board.heart.controller.vo.response.BoardHeartResponseVo.*;

public class BoardHeartResponseDto {
	public record AddBoardHeartResponseDto(
		Long boardHeartSeq
	) {
		public AddBoardHeartResponse toAddBoardHeartResponse() {
			return new AddBoardHeartResponse(
				this.boardHeartSeq
			);
		}
	}
}
