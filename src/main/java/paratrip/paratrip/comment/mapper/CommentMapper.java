package paratrip.paratrip.comment.mapper;

import static paratrip.paratrip.member.service.dto.request.MemberRequestDto.*;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import paratrip.paratrip.board.main.entity.BoardEntity;
import paratrip.paratrip.comment.entity.CommentEntity;
import paratrip.paratrip.member.entity.MemberEntity;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {
	@Mapping(target = "commentSeq", ignore = true)
	@Mapping(target = "boardEntity", source = "boardEntity")
	@Mapping(target = "memberEntity", source = "memberEntity")
	@Mapping(target = "comment", source = "comment")
	CommentEntity toCommentEntity(BoardEntity boardEntity, MemberEntity memberEntity, String comment);
}
