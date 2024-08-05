package paratrip.paratrip.board.main.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import paratrip.paratrip.board.main.entity.BoardEntity;
import paratrip.paratrip.member.entity.MemberEntity;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BoardMapper {
	@Mapping(target = "boardSeq", ignore = true)
	@Mapping(target = "title", source = "title")
	@Mapping(target = "content", source = "content")
	@Mapping(target = "location", source = "location")
	@Mapping(target = "hearts", source = "hearts")
	@Mapping(target = "creatorMemberEntity", source = "creatorMemberEntity")
	BoardEntity toBoardEntity(String title, String content, String location, Long hearts, MemberEntity creatorMemberEntity);
}
