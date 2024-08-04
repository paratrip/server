package paratrip.paratrip.board.heart.service.dto.request;

public class BoardHeartRequestDto {
	public record AddBoardHeartRequestDto(
		Long memberSeq,
		Long boardSeq
	) {

	}

	public record DeleteBoardHeartRequestDto(
		Long memberSeq,
		Long boardHeartSeq
	) {

	}
}
