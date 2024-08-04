package paratrip.paratrip.board.scrap.repository;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import paratrip.paratrip.board.scrap.entity.BoardScrapEntity;

@Component
@RequiredArgsConstructor
public class BoardScrapRepositoryImpl implements BoardScrapRepository {
	private final BoardScrapJpaRepository boardScrapJpaRepository;

	@Override
	public BoardScrapEntity saveBoardScrapEntity(BoardScrapEntity boardScrapEntity) {
		return boardScrapJpaRepository.save(boardScrapEntity);
	}
}
