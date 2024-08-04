package paratrip.paratrip.board.scrap.service.dto.request;

public class BoardScarpRequestDto {
	public record AddBoardScrapRequestDto(
		Long memberSeq,
		Long boardSeq
	) {

	}

	public record DeleteBoardScrapRequestDto(
		Long memberSeq,
		Long boardScrapSeq
	) {

	}
}
