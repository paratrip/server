package paratrip.paratrip.hearts.service.dto.request;

public class BoardHeartRequestDto {
	public record IncreaseBoardHeartsRequestDto(
		Long memberSeq,
		Long boardSeq
	) {

	}

	public record DecreaseBoardHeartsRequestDto(
		Long memberSeq,
		Long boardSeq,
		Long boardHeartSeq
	) {

	}
}
