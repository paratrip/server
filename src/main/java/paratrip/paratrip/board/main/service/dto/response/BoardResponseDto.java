package paratrip.paratrip.board.main.service.dto.response;

import static paratrip.paratrip.board.main.controller.vo.response.BoardResponseVo.*;

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
