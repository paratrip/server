package paratrip.paratrip.scrap.paragliding.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import paratrip.paratrip.member.entity.MemberEntity;
import paratrip.paratrip.paragliding.entity.Paragliding;
import paratrip.paratrip.scrap.paragliding.entity.ParaglidingScrapEntity;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ParaglidingScrapMapper {
	@Mapping(target = "paraglidingScrapSeq", ignore = true)
	@Mapping(target = "memberEntity", source = "memberEntity")
	@Mapping(target = "paraglidingEntity", source = "paraglidingEntity")
	ParaglidingScrapEntity toParaglidingScrapEntity(MemberEntity memberEntity, Paragliding paraglidingEntity);
}
