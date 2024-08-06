package paratrip.paratrip.board.search.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import paratrip.paratrip.board.search.entity.BoardDocuments;

@Repository
public interface BoardDocumentsElasticSearchRepository extends ElasticsearchRepository<BoardDocuments, String> {

}
