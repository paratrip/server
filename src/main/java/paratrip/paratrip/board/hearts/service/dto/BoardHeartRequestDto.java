package paratrip.paratrip.board.hearts.service.dto;

public class BoardHeartRequestDto {
	public record IncreaseBoardHeartsRequestDto(
		Long memberSeq,
		Long boardSeq
	) {

	}

	public record DecreaseBoardHeartsRequestDto(
		Long memberSeq,
		Long boardSeq
	) {

	}
}
