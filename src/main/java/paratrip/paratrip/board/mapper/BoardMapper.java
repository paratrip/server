package paratrip.paratrip.board.mapper;

import static paratrip.paratrip.member.service.dto.request.MemberRequestDto.*;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import paratrip.paratrip.board.entity.BoardEntity;
import paratrip.paratrip.member.entity.MemberEntity;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BoardMapper {
	@Mapping(target = "boardSeq", ignore = true)
	@Mapping(target = "title", source = "title")
	@Mapping(target = "content", source = "content")
	@Mapping(target = "location", source = "location")
	@Mapping(target = "memberEntity", source = "memberEntity")
	BoardEntity toBoardEntity(String title, String content, String location, MemberEntity memberEntity);
}
