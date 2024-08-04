package paratrip.paratrip.board.main.repository;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import paratrip.paratrip.board.main.entity.BoardEntity;
import paratrip.paratrip.board.main.entity.BoardImageEntity;

@Component
@RequiredArgsConstructor
public class BoardImageRepositoryImpl implements BoardImageRepository {
	private final BoardImageJpaRepository boardImageJpaRepository;

	@Override
	public BoardImageEntity saveBoardImageEntity(BoardImageEntity boardImageEntity) {
		return boardImageJpaRepository.save(boardImageEntity);
	}

	@Override
	public List<BoardImageEntity> findAllByBoardEntity(BoardEntity boardEntity) {
		return boardImageJpaRepository.findAllByBoardEntity(boardEntity);
	}

	@Override
	public void deleteAll(List<BoardImageEntity> boardImageEntities) {
		boardImageJpaRepository.deleteAll(boardImageEntities);
	}
}
