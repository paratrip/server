package paratrip.paratrip.hearts.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import paratrip.paratrip.hearts.entity.BoardHeartEntity;
import paratrip.paratrip.board.main.entity.BoardEntity;
import paratrip.paratrip.member.entity.MemberEntity;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BoardHeartMapper {
	@Mapping(target = "boardHeartSeq", ignore = true)
	@Mapping(target = "boardEntity", source = "boardEntity")
	@Mapping(target = "memberEntity", source = "memberEntity")
	BoardHeartEntity toBoardHeartEntity(BoardEntity boardEntity, MemberEntity memberEntity);
}
