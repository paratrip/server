package paratrip.paratrip.board.service.dto.response;

import static paratrip.paratrip.board.controller.vo.response.BoardResponseVo.*;

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
}
