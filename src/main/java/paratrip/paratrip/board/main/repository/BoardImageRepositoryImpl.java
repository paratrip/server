package paratrip.paratrip.board.main.repository;

import java.util.List;
import java.util.stream.Collectors;

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

	@Override
	public List<String> extractImageURLsByBoardEntity(BoardEntity boardEntity) {
		List<BoardImageEntity> boardImageEntities = boardImageJpaRepository.findAllByBoardEntity(boardEntity);

		return boardImageEntities.stream()
			.map(BoardImageEntity::getImageURL)
			.collect(Collectors.toList());
	}

	@Override
	public void deleteByBoardEntity(BoardEntity boardEntity) {
		boardImageJpaRepository.deleteByBoardEntity(boardEntity);
	}
}
