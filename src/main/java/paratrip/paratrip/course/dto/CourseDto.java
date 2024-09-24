package paratrip.paratrip.course.dto;

import java.util.List;

public record CourseDto(
        String paraglidingName,
        String touristSpotName1,
        String touristSpotName2,
        String region,
        String imageUrlParagliding,
        String imageUrl1,
        String imageUrl2,
        String category1,
        String category2,
        String spotAddress1,
        String spotAddress2,
        List<String> tags  // 태그 리스트 추가
) {}
