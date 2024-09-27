package paratrip.paratrip.course.dto;

public record CourseResponseDto(
        Long courseId,                 // 코스 ID
        String paraglidingName,         // 패러글라이딩 이름
        String touristSpotName1,        // 관광지 1 이름
        String touristSpotName2,        // 관광지 2 이름
        String paraglidingRegion,       // 패러글라이딩의 지역 (추가됨)
        String touristSpotTag1,         // 관광지 1의 태그
        String touristSpotTag2,         // 관광지 2의 태그
        String paraglidingImageUrl,     // 패러글라이딩 이미지 URL
        String touristSpotImageUrl1,    // 관광지 1 이미지 URL
        String touristSpotImageUrl2,    // 관광지 2 이미지 URL
        String rlteTatsNm1,             // 관광지 1의 rlteTatsNm (추가됨)
        String rlteTatsNm2              // 관광지 2의 rlteTatsNm (추가됨)
) {}
