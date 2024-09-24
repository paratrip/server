package paratrip.paratrip.scrap.board.service.dto.request;

public class BoardScarpRequestDto {
	public record AddBoardScrapRequestDto(
		Long memberSeq,
		Long boardSeq
	) {

	}

	public record DeleteBoardScrapRequestDto(
		Long memberSeq,
		Long boardSeq
	) {

	}
}
