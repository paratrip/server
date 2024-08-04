package paratrip.paratrip.board.main.repository;

import java.util.List;

import paratrip.paratrip.board.main.entity.BoardEntity;
import paratrip.paratrip.board.main.entity.BoardImageEntity;

public interface BoardImageRepository {
	BoardImageEntity saveBoardImageEntity(BoardImageEntity boardImageEntity);
	List<BoardImageEntity> findAllByBoardEntity(BoardEntity boardEntity);
	void deleteAll(List<BoardImageEntity> boardImageEntities);
}
