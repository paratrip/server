package paratrip.paratrip.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import paratrip.paratrip.board.entity.BoardEntity;

@Repository
public interface BoardJpaRepository extends JpaRepository<BoardEntity, Long> {

}
