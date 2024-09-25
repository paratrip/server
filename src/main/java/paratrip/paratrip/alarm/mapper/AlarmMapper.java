package paratrip.paratrip.alarm.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import paratrip.paratrip.alarm.entity.AlarmEntity;
import paratrip.paratrip.alarm.utils.Type;
import paratrip.paratrip.board.main.entity.BoardEntity;
import paratrip.paratrip.member.entity.MemberEntity;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AlarmMapper {
	@Mapping(target = "alarmSeq", ignore = true)
	@Mapping(target = "boardEntity", source = "boardEntity")
	@Mapping(target = "memberEntity", source = "memberEntity")
	@Mapping(target = "type", source = "type")
	AlarmEntity toAlarmEntity(BoardEntity boardEntity, MemberEntity memberEntity, Type type);
}
