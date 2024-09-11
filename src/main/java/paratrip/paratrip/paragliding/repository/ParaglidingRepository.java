package paratrip.paratrip.paragliding.repository;

import org.springframework.data.jpa.repository.JpaRepository;
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
public interface ParaglidingRepository extends JpaRepository<Paragliding, Long> {
    List<Paragliding> findByRegion(Region region);
}
