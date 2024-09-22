package paratrip.paratrip.alarm.service.dto.response;

import paratrip.paratrip.alarm.utils.Type;

public class AlarmResponseDto {
	public record GetAlarmResponseDto(
		Type type,
		String userId,
		String title
	) {

	}
}
