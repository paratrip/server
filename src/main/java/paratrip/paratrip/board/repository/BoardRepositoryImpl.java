package paratrip.paratrip.board.repository;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import paratrip.paratrip.board.entity.BoardEntity;

@Component
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepository {
	private final BoardJpaRepository boardJpaRepository;

	@Override
	public BoardEntity saveBoardEntity(BoardEntity boardEntity) {
		return boardJpaRepository.save(boardEntity);
	}
}
