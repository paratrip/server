package paratrip.paratrip.board.search.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import paratrip.paratrip.board.search.entity.BoardDocuments;

public interface BoardDocumentsRepository {
	BoardDocuments saveBoardDocuments(BoardDocuments boardDocuments);

	Page<BoardDocuments> findByTitleContains(String title, Pageable pageable);
}
