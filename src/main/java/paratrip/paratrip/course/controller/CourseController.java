package paratrip.paratrip.course.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import paratrip.paratrip.course.dto.CourseDto;
import paratrip.paratrip.course.service.CourseService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @Operation(summary = "코스 생성", description = "새로운 코스를 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "코스 생성 완료"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @PostMapping("/generate-courses")
    public String generateCourses() {
        courseService.generateCourses();
        return "코스가 성공적으로 생성되었습니다.";
    }

    @Operation(summary = "코스 목록 조회", description = "생성된 모든 코스 목록을 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "코스 목록 조회 성공"),
            @ApiResponse(responseCode = "404", description = "코스를 찾을 수 없음")
    })
    @GetMapping("/courses")
    public List<CourseDto> getCourses() {
        return courseService.getCourses();
    }
}
