package paratrip.paratrip.alarm.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import paratrip.paratrip.alarm.entity.AlarmEntity;
import paratrip.paratrip.alarm.repository.AlarmRepository;
import paratrip.paratrip.alarm.service.dto.response.AlarmResponseDto;
import paratrip.paratrip.member.entity.MemberEntity;
import paratrip.paratrip.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class AlarmService {
	private final MemberRepository memberRepository;
	private final AlarmRepository alarmRepository;

	@Transactional(readOnly = true)
	public List<AlarmResponseDto.GetAlarmResponseDto> getAlarm(Long memberSeq) {
		/*
		 1. Member 유효성 검사
		*/
		MemberEntity memberEntity = memberRepository.findByMemberSeq(memberSeq);

		List<AlarmEntity> alarmEntities = alarmRepository.findAllByOwnerEntity(memberEntity);

		return alarmEntities.stream()
			.map(alarmEntity -> new AlarmResponseDto.GetAlarmResponseDto(
				alarmEntity.getType(),
				alarmEntity.getMemberEntity().getUserId(),
				alarmEntity.getBoardEntity().getTitle()
			))
			.collect(Collectors.toList());
	}
}
