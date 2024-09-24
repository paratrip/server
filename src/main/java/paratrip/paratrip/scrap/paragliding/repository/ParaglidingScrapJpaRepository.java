package paratrip.paratrip.scrap.paragliding.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import paratrip.paratrip.scrap.paragliding.entity.ParaglidingScrapEntity;

@Repository
public interface ParaglidingScrapJpaRepository extends JpaRepository<ParaglidingScrapEntity, Long> {

}
