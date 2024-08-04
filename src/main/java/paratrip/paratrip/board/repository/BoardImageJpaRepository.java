package paratrip.paratrip.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import paratrip.paratrip.board.entity.BoardImageEntity;

@Repository
public interface BoardImageJpaRepository extends JpaRepository<BoardImageEntity, Long> {

}
