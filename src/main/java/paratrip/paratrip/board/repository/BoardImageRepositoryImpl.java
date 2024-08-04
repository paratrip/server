package paratrip.paratrip.board.repository;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import paratrip.paratrip.board.entity.BoardImageEntity;

@Component
@RequiredArgsConstructor
public class BoardImageRepositoryImpl implements BoardImageRepository {
	private final BoardImageJpaRepository boardImageJpaRepository;

	@Override
	public BoardImageEntity saveBoardImageEntity(BoardImageEntity boardImageEntity) {
		return boardImageJpaRepository.save(boardImageEntity);
	}
}
