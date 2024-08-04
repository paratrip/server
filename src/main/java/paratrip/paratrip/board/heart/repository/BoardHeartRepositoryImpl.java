package paratrip.paratrip.board.heart.repository;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import paratrip.paratrip.board.heart.entity.BoardHeartEntity;

@Component
@RequiredArgsConstructor
public class BoardHeartRepositoryImpl implements BoardHeartRepository {
	private final BoardHeartJpaRepository boardHeartJpaRepository;

	@Override
	public BoardHeartEntity saveBoardHeartEntity(BoardHeartEntity boardHeartEntity) {
		return boardHeartJpaRepository.save(boardHeartEntity);
	}
}
