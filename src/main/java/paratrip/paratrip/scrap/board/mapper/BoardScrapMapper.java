package paratrip.paratrip.scrap.board.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import paratrip.paratrip.board.main.entity.BoardEntity;
import paratrip.paratrip.scrap.board.entity.BoardScrapEntity;
import paratrip.paratrip.member.entity.MemberEntity;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BoardScrapMapper {
	@Mapping(target = "boardScrapSeq", ignore = true)
	@Mapping(target = "memberEntity", source = "memberEntity")
	@Mapping(target = "boardEntity", source = "boardEntity")
	BoardScrapEntity toBoardScrapEntity(MemberEntity memberEntity, BoardEntity boardEntity);
}
