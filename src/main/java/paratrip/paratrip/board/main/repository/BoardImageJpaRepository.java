package paratrip.paratrip.board.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import paratrip.paratrip.board.main.entity.BoardEntity;
import paratrip.paratrip.board.main.entity.BoardImageEntity;

@Repository
public interface BoardImageJpaRepository extends JpaRepository<BoardImageEntity, Long> {
	List<BoardImageEntity> findAllByBoardEntity(BoardEntity boardEntity);

	void deleteByBoardEntity(BoardEntity boardEntity);
}
