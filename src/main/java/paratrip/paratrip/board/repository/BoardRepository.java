package paratrip.paratrip.board.repository;

import paratrip.paratrip.board.entity.BoardEntity;

public interface BoardRepository {
	BoardEntity saveBoardEntity(BoardEntity boardEntity);
}
