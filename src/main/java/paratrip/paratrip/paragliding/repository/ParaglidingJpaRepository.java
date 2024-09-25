package paratrip.paratrip.paragliding.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import paratrip.paratrip.paragliding.entity.Paragliding;
import paratrip.paratrip.paragliding.entity.Region;

import java.util.List;

/**
 * packageName    : paratrip.paratrip.paragliding.repository
 * fileName       : ParaglidingRepository
 * author         : tlswl
 * date           : 2024-09-09
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-09-09        tlswl       최초 생성
 */
@Repository
public interface ParaglidingJpaRepository extends JpaRepository<Paragliding, Long> {
    List<Paragliding> findByRegion(Region region);
    List<Paragliding> findByRegionIn(List<Region> regions);
}
