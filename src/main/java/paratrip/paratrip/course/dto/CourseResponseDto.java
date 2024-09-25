package paratrip.paratrip.course.dto;

import java.util.List;

public record CourseResponseDto(
        Long courseId,              // 코스 ID
        String paraglidingName,      // 패러글라이딩 이름
        String touristSpotName1,     // 관광지 1 이름
        String touristSpotName2,     // 관광지 2 이름
        String paraglidingRegion,    // 패러글라이딩 지역
        List<String> touristSpotTags1, // 관광지 1의 태그
        List<String> touristSpotTags2, // 관광지 2의 태그
        String paraglidingImageUrl,  // 패러글라이딩 이미지 URL
        String touristSpotImageUrl1, // 관광지 1 이미지 URL
        String touristSpotImageUrl2  // 관광지 2 이미지 URL
) {}
