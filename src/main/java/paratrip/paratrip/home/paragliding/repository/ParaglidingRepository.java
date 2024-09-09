package paratrip.paratrip.home.paragliding.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import paratrip.paratrip.home.paragliding.entity.Paragliding;

/**
 * packageName    : paratrip.paratrip.home.paragliding.repository
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
}
