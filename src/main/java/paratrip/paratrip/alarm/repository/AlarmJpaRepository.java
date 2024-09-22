package paratrip.paratrip.alarm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import paratrip.paratrip.alarm.entity.AlarmEntity;

@Repository
public interface AlarmJpaRepository extends JpaRepository<AlarmEntity, Long> {

}
