package paratrip.paratrip.course.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import paratrip.paratrip.course.dto.CourseResponseDto;
import paratrip.paratrip.course.service.CourseService;
import paratrip.paratrip.course.service.TouristSpotService;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@Tag(name = "관광코스 관련 API")
public class CourseController {

    private final CourseService courseService;
    private final TouristSpotService touristSpotService;

    // 코스 생성 API
    @PostMapping("/generate")
    public ResponseEntity<String> generateCourses() {
        courseService.generateCourses();  // 코스 생성
        return ResponseEntity.ok("코스가 성공적으로 생성되었습니다.");
    }

    // 모든 관광 코스 조회
    @GetMapping("/list")
    public ResponseEntity<List<CourseResponseDto>> getAllCourses() {
        List<CourseResponseDto> courseList = courseService.getAllCourses();
        return ResponseEntity.ok(courseList);
    }

    @GetMapping("/list/{courseId}")
    public ResponseEntity<List<CourseResponseDto>> getDetailCourses(){
        List<CourseResponseDto> courseDetail = courseService.getAllCourses();
        return ResponseEntity.ok(courseDetail);
    }
}
