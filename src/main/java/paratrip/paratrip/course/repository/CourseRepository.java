package paratrip.paratrip.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import paratrip.paratrip.course.entity.TourCourse;
import paratrip.paratrip.paragliding.entity.Region;

import java.util.List;

public interface CourseRepository extends JpaRepository<TourCourse, Long> {
    List<TourCourse> findByRegion(Region region);
    // 지역으로 코스를 필터링하는 쿼리
    List<TourCourse> findByRegionContaining(String region);

    // 태그로 코스를 필터링하는 쿼리 (태그 중 하나라도 포함되면 조회)
    List<TourCourse> findByTagsIn(List<String> tags);

}
