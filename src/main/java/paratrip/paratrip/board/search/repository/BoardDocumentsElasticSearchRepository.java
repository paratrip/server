package paratrip.paratrip.board.search.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import paratrip.paratrip.board.search.entity.BoardDocuments;

@Repository
public interface BoardDocumentsElasticSearchRepository extends ElasticsearchRepository<BoardDocuments, String> {
	Page<BoardDocuments> findByTitleContains(String title, Pageable pageable);
}
