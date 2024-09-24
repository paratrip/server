package paratrip.paratrip.alarm.repository;

import java.util.List;

import paratrip.paratrip.alarm.entity.AlarmEntity;
import paratrip.paratrip.board.main.entity.BoardEntity;
import paratrip.paratrip.member.entity.MemberEntity;

public interface AlarmRepository {
	AlarmEntity saveAlarmEntity(AlarmEntity alarmEntity);

	List<AlarmEntity> findAllByMemberEntity(MemberEntity memberEntity);

	void deleteByBoardEntity(BoardEntity boardEntity);
}
