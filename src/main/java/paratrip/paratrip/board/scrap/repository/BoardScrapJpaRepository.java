package paratrip.paratrip.board.scrap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import paratrip.paratrip.board.scrap.entity.BoardScrapEntity;

@Repository
public interface BoardScrapJpaRepository extends JpaRepository<BoardScrapEntity, Long> {

}
