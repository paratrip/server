package paratrip.paratrip.board.search.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import paratrip.paratrip.board.search.entity.BoardDocuments;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BoardDocumentsMapper {
	@Mapping(target = "boardDocumentsSeq", ignore = true)
	@Mapping(target = "boardSeq", source = "boardSeq")
	@Mapping(target = "title", source = "title")
	BoardDocuments toBoardDocuments(Long boardSeq, String title);
}
