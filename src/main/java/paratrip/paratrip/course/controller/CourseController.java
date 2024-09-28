package paratrip.paratrip.course.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import paratrip.paratrip.paragliding.entity.Region;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@Tag(name = "관광코스 관련 API")
public class CourseController {

    private final CourseService courseService;

    // 코스 생성 API
    @PostMapping("/generate")
    public ResponseEntity<String> generateCourses() {
        courseService.generateCourses();  // 코스 생성
        return ResponseEntity.ok("코스가 성공적으로 생성되었습니다.");
    }

    @Operation(
            summary = "코스 조회 (필터링 포함)",
            description = "모든 관광 코스 리스트를 조회하고, 선택한 지역과 태그로 필터링된 코스 리스트를 조회할 수 있습니다. 지역 및 태그를 여러 개 선택할 수 있습니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "성공적으로 코스를 조회했습니다.",
                    content = @Content(schema = @Schema(implementation = CourseResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "조회할 코스를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    @GetMapping("/list")
    public ResponseEntity<List<CourseResponseDto>> getAllCourses(
            @Parameter(description = "조회할 지역의 이름 또는 코드 (여러 개 선택 가능)") @RequestParam(required = false) List<Region> region,
            @Parameter(description = "조회할 태그 이름 (여러 개 선택 가능)") @RequestParam(required = false) List<String> tag
    ) {
        // 여러 필터링 조건을 서비스에 전달하여 필터링된 코스를 조회
        List<CourseResponseDto> courseList = courseService.getAllCourses(region, tag);
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
