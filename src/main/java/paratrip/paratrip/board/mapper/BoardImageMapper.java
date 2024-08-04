package paratrip.paratrip.board.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import paratrip.paratrip.board.entity.BoardEntity;
import paratrip.paratrip.board.entity.BoardImageEntity;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BoardImageMapper {
	@Mapping(target = "boardImageSeq", ignore = true)
	@Mapping(target = "boardEntity", source = "boardEntity")
	@Mapping(target = "imageURL", source = "imageURL")
	BoardImageEntity toBoardImageEntity(BoardEntity boardEntity, String imageURL);
}
