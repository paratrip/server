//package paratrip.paratrip.course.util;
//
//import paratrip.paratrip.course.dto.CourseDto;
//import paratrip.paratrip.course.entity.TourCourse;
//
//public class CourseMapper {
//
//    public static CourseDto toCourseDto(TourCourse course) {
//        return new CourseDto(
//                course.getParagliding().getName(),
//                course.getTouristSpot1().getRlteTatsNm(),
//                course.getTouristSpot2().getRlteTatsNm(),
//                course.getRegion(),  // Region 값 매핑
//                course.getStringRegion(),  // stringRegion 값 매핑
//                course.getImageUrlParagliding(),
//                course.getImageUrl1(),
//                course.getImageUrl2(),
//                course.getCategory1(),
//                course.getCategory2(),
//                course.getSpotAddress1(),
//                course.getSpotAddress2(),
//                course.getTags()
//        );
//    }
//}
