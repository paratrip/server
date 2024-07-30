package paratrip.paratrip.member.mapper;

import static paratrip.paratrip.member.dto.request.MemberRequestDto.*;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import paratrip.paratrip.member.entity.MemberEntity;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {
	@Mapping(target = "memberSeq", ignore = true)
	@Mapping(target = "encodedPassword", source = "encodedPassword")
	MemberEntity toMemberEntity(JoinMemberRequestDto request, String encodedPassword);
}
