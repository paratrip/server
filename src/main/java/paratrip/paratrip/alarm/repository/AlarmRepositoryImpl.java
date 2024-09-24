package paratrip.paratrip.alarm.repository;

import java.util.List;

import org.springframework.stereotype.Component;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import paratrip.paratrip.alarm.entity.AlarmEntity;
import paratrip.paratrip.alarm.entity.QAlarmEntity;
import paratrip.paratrip.board.main.entity.BoardEntity;
import paratrip.paratrip.member.entity.MemberEntity;

@Component
@RequiredArgsConstructor
public class AlarmRepositoryImpl implements AlarmRepository {
	private final AlarmJpaRepository alarmJpaRepository;
	private final JPAQueryFactory queryFactory;

	@Override
	public AlarmEntity saveAlarmEntity(AlarmEntity alarmEntity) {
		return alarmJpaRepository.save(alarmEntity);
	}

	@Override
	public List<AlarmEntity> findAllByMemberEntity(MemberEntity memberEntity) {
		QAlarmEntity qAlarmEntity = QAlarmEntity.alarmEntity;

		return queryFactory
			.selectFrom(qAlarmEntity)
			.where(
				qAlarmEntity.memberEntity.eq(memberEntity)
			)
			.fetch();
	}

	@Override
	public void deleteByBoardEntity(BoardEntity boardEntity) {
		alarmJpaRepository.deleteByBoardEntity(boardEntity);
	}
}
