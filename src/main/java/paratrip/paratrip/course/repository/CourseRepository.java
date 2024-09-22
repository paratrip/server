package paratrip.paratrip.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import paratrip.paratrip.course.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
