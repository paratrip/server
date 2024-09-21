package paratrip.paratrip.course.dto;

public record TouristSpotDto(
        String basicAddress,
        String largeCategory,
        String middleCategory,
        String smallCategory,
        String touristSpotName,
        String regionCode
) {}
