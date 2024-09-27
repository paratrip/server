package paratrip.paratrip.course.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(
            summary = "전체 코스 조회",
            description = "모든 관광 코스 리스트를 조회하고 각 코스에 대한 세부 정보를 반환합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "성공적으로 모든 코스를 조회했습니다.",
                    content = @Content(schema = @Schema(implementation = CourseResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "조회할 코스를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    @GetMapping("/list")
    public ResponseEntity<List<CourseResponseDto>> getAllCourses() {
        List<CourseResponseDto> courseList = courseService.getAllCourses();
        return ResponseEntity.ok(courseList);
    }

    @Operation(summary = "코스 상세 조회", description = "courseId로 특정 코스의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 코스 상세 정보를 조회했습니다."),
            @ApiResponse(responseCode = "404", description = "해당 courseId에 해당하는 코스를 찾을 수 없습니다.")
    })
    @GetMapping("/{courseId}")
    public ResponseEntity<CourseResponseDto> getCourseDetail(
            @PathVariable("courseId") Long courseId) {
        CourseResponseDto courseDetail = courseService.getCourseDetail(courseId);
        return ResponseEntity.ok(courseDetail);
    }
}
