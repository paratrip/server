package paratrip.paratrip.board.search.entity;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
@Document(indexName = "board_documents")
@Mapping(mappingPath = "static/elastic-mapping.json")
@Setting(settingPath = "static/elastic-token.json")
public class BoardDocuments {
	@Id
	@org.springframework.data.annotation.Id
	@Field(name = "board_documents_seq", type = FieldType.Keyword)
	private String boardDocumentsSeq;

	@Field(type = FieldType.Long)
	private Long boardSeq;

	@Field(type = FieldType.Text)
	private String title;
}
