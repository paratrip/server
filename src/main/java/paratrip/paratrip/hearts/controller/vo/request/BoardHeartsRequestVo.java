package paratrip.paratrip.hearts.controller.vo.request;

import static paratrip.paratrip.hearts.service.dto.request.BoardHeartRequestDto.*;

public class BoardHeartsRequestVo {
	public record IncreaseBoardHeartsRequest(
		Long memberSeq,
		Long boardSeq
	) {
		public IncreaseBoardHeartsRequestDto toIncreaseBoardHeartsRequestDto() {
			return new IncreaseBoardHeartsRequestDto(
				this.memberSeq,
				this.boardSeq
			);
		}
	}

	public record DecreaseBoardHeartsRequest(
		Long memberSeq,
		Long boardSeq
	) {
		public DecreaseBoardHeartsRequestDto toDecreaseBoardHeartsRequestDto() {
			return new DecreaseBoardHeartsRequestDto(
				this.memberSeq,
				this.boardSeq
			);
		}
	}
}
