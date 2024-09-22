package paratrip.paratrip.course.controller;

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

    @PostMapping("/generate-courses")
    public String generateCourses() {
        courseService.generateCourses();
        return "Courses generated successfully.";
    }

    @GetMapping("/courses")
    public List<CourseDto> getCourses() {
        return courseService.getCourses();
    }
}
