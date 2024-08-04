package paratrip.paratrip.board.repository;

import java.util.List;

import paratrip.paratrip.board.entity.BoardEntity;
import paratrip.paratrip.board.entity.BoardImageEntity;

public interface BoardImageRepository {
	BoardImageEntity saveBoardImageEntity(BoardImageEntity boardImageEntity);
	List<BoardImageEntity> findAllByBoardEntity(BoardEntity boardEntity);
	void deleteAll(List<BoardImageEntity> boardImageEntities);
}
