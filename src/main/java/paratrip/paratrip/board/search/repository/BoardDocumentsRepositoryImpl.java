package paratrip.paratrip.board.search.repository;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import paratrip.paratrip.board.search.entity.BoardDocuments;

@Component
@RequiredArgsConstructor
public class BoardDocumentsRepositoryImpl implements BoardDocumentsRepository {
	private final BoardDocumentsElasticSearchRepository boardDocumentsElasticSearchRepository;

	@Override
	public BoardDocuments saveBoardDocuments(BoardDocuments boardDocuments) {
		return boardDocumentsElasticSearchRepository.save(boardDocuments);
	}
}
