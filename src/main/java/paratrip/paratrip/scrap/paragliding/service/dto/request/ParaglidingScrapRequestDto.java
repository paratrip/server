package paratrip.paratrip.scrap.paragliding.service.dto.request;

public class ParaglidingScrapRequestDto {
	public record SaveParaglidingScrapRequestDto(
		Long memberSeq,
		Long paraglidingSeq
	) {

	}

	public record DeleteParaglidingScrapRequestDto(
		Long memberSeq,
		Long paraglidingSeq
	) {

	}
}
