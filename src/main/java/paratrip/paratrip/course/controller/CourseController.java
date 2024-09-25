package paratrip.paratrip.course.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import paratrip.paratrip.course.dto.CourseResponseDto;
import paratrip.paratrip.course.service.CourseService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "관광코스 태그 조회 API", description = "지역 및 태그 기반 관광 코스 조회 API")
public class CourseController {

    private final CourseService courseService;

    @Operation(summary = "관광 코스 조회", description = "선택한 지역 또는 태그에 기반하여 필터링된 관광 코스를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 관광 코스 목록을 조회했습니다.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CourseResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/api/courses")
    public ResponseEntity<List<CourseResponseDto>> getCourses(
            @Parameter(description = "필터링할 지역 코드 (예: PC, BR 등)", example = "PC")
            @RequestParam(required = false) String region,

            @Parameter(description = "필터링할 태그 목록 (쉼표로 구분된 태그들)", example = "자연,역사")
            @RequestParam(required = false) List<String> tags) {

        List<CourseResponseDto> courses = courseService.getCoursesWithFilters(region, tags);
        return ResponseEntity.ok(courses);
    }
}
