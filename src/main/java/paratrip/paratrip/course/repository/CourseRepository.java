package paratrip.paratrip.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import paratrip.paratrip.course.entity.TourCourse;
import paratrip.paratrip.paragliding.entity.Region;

import java.util.List;

public interface CourseRepository extends JpaRepository<TourCourse, Long> {
    List<TourCourse> findByRegion(Region region);

}
