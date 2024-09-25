//package paratrip.paratrip.course.controller;
//
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//import paratrip.paratrip.course.dto.CourseDto;
//import paratrip.paratrip.course.entity.TourCourse;
//import paratrip.paratrip.course.service.CourseService;
//import paratrip.paratrip.course.util.CourseMapper;
//import paratrip.paratrip.paragliding.entity.Region;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequiredArgsConstructor
//public class CourseController {
//
//    private final CourseService courseService;
//
////    @Operation(summary = "코스 생성", description = "새로운 코스를 생성")
////    @ApiResponses(value = {
////            @ApiResponse(responseCode = "200", description = "코스 생성 완료"),
////            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
////    })
////    @PostMapping("/generate-courses")
////    public String generateCourses() {
////        courseService.generateCourses();
////        return "코스가 성공적으로 생성되었습니다.";
////    }
//
//    // 모든 코스 조회 API
////    @Operation(summary = "모든 코스 조회", description = "모든 패러글라이딩 코스 정보를 조회합니다.")
////    @ApiResponses(value = {
////            @ApiResponse(responseCode = "200", description = "코스 조회 성공"),
////            @ApiResponse(responseCode = "500", description = "서버 오류")
////    })
////    @GetMapping("/courses")
////    public ResponseEntity<List<CourseDto>> getAllCourses() {
////        List<TourCourse> courses = courseService.getAllCourses();
////        List<CourseDto> courseDtos = courses.stream()
////                .map(CourseMapper::toCourseDto)
////                .collect(Collectors.toList());
////
////        return ResponseEntity.ok(courseDtos);
////    }
////
////    // 특정 지역의 코스 조회 API
////    @Operation(summary = "특정 지역 코스 조회", description = "지역 코드를 통해 해당 지역의 패러글라이딩 코스를 조회합니다.")
////    @ApiResponses(value = {
////            @ApiResponse(responseCode = "200", description = "코스 조회 성공"),
////            @ApiResponse(responseCode = "404", description = "코스를 찾을 수 없음"),
////            @ApiResponse(responseCode = "500", description = "서버 오류")
////    })
////    @GetMapping("/courses/region/{region}")
////    public ResponseEntity<List<CourseDto>> getCoursesByRegion(@PathVariable Region region) {
////        List<TourCourse> courses = courseService.getCoursesByRegion(region);
////        if (courses.isEmpty()) {
////            return ResponseEntity.notFound().build();
////        }
////
////        List<CourseDto> courseDtos = courses.stream()
////                .map(CourseMapper::toCourseDto)
////                .collect(Collectors.toList());
////
////        return ResponseEntity.ok(courseDtos);
////    }
//
//
//}
