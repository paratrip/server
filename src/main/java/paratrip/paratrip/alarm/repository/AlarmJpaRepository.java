package paratrip.paratrip.alarm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import paratrip.paratrip.alarm.entity.AlarmEntity;
import paratrip.paratrip.board.main.entity.BoardEntity;

@Repository
public interface AlarmJpaRepository extends JpaRepository<AlarmEntity, Long> {
	void deleteByBoardEntity(BoardEntity boardEntity);
}
