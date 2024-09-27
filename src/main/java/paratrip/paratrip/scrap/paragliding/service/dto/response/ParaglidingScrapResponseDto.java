package paratrip.paratrip.scrap.paragliding.service.dto.response;

import paratrip.paratrip.paragliding.entity.Region;

public class ParaglidingScrapResponseDto {
	public record SaveParaglidingScrapResponseDto(
		Long paraglidingScrapSeq
	) {

	}

	public record GetParaglidingScrapResponseDto(
		Long paraglidingSeq,
		String name,
		int heart,
		Double cost,
		Region region,
		String imageUrl
	) {

	}
}
