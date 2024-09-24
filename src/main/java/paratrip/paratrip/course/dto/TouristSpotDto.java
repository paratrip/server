package paratrip.paratrip.course.dto;

public record TouristSpotDto(
        String basicAddress,
        String largeCategory,
        String middleCategory,
        String smallCategory,
        String rlteCtgrySclsNm,
        String touristSpotName,
        String regionCode,
        String imageUrl  // 이미지 URL 추가

) {}
