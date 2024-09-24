package paratrip.paratrip.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import paratrip.paratrip.course.entity.TourCourse;

public interface CourseRepository extends JpaRepository<TourCourse, Long> {
}
