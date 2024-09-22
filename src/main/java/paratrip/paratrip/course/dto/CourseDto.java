package paratrip.paratrip.course.dto;

public record CourseDto(
        String paraglidingName,
        String touristSpot1Name,
        String touristSpot2Name,
        String region,
        String imageUrlParagliding,
        String imageUrlTouristSpot1,
        String imageUrlTouristSpot2,
        String category1,
        String category2
) {}
