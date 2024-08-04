package paratrip.paratrip.board.scrap.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import paratrip.paratrip.board.main.entity.BoardEntity;
import paratrip.paratrip.board.scrap.entity.BoardScrapEntity;
import paratrip.paratrip.member.entity.MemberEntity;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BoardScrapMapper {
	@Mapping(target = "boardScrapSeq", ignore = true)
	@Mapping(target = "memberEntity", source = "memberEntity")
	@Mapping(target = "boardEntity", source = "boardEntity")
	BoardScrapEntity toBoardScrapEntity(MemberEntity memberEntity, BoardEntity boardEntity);
}
