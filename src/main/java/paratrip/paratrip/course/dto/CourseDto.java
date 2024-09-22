package paratrip.paratrip.course.dto;

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
        String spotAddress1,  // 관광지 1의 세부 주소
        String spotAddress2   // 관광지 2의 세부 주소
) {}
