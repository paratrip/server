package paratrip.paratrip.board.heart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import paratrip.paratrip.board.heart.entity.BoardHeartEntity;

@Repository
public interface BoardHeartJpaRepository extends JpaRepository<BoardHeartEntity, Long> {

}
