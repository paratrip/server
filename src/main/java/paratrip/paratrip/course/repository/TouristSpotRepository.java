package paratrip.paratrip.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import paratrip.paratrip.course.entity.TouristSpot;

import java.util.List;

public interface TouristSpotRepository extends JpaRepository<TouristSpot, Long> {
    boolean existsByRlteTatsNm(String rlteTatsNm);
    List<TouristSpot> findByRegionCode(String regionCode);

}