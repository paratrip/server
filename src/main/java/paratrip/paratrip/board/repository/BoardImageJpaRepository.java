package paratrip.paratrip.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import paratrip.paratrip.board.entity.BoardEntity;
import paratrip.paratrip.board.entity.BoardImageEntity;

@Repository
public interface BoardImageJpaRepository extends JpaRepository<BoardImageEntity, Long> {
	List<BoardImageEntity> findAllByBoardEntity(BoardEntity boardEntity);
}
