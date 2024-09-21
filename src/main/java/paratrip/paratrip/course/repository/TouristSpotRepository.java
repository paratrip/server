package paratrip.paratrip.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import paratrip.paratrip.course.entity.TouristSpot;

public interface TouristSpotRepository extends JpaRepository<TouristSpot, Long> {
    boolean existsByTouristSpotName(String touristSpotName);
}