package paratrip.paratrip.alarm.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import paratrip.paratrip.alarm.service.AlarmService;
import paratrip.paratrip.alarm.service.dto.response.AlarmResponseDto;
import paratrip.paratrip.core.base.BaseResponse;

@RestController(value = "/alarm")
@RequiredArgsConstructor
public class AlarmController {
	private final AlarmService alarmService;

	@GetMapping()
	public ResponseEntity<BaseResponse<List<AlarmResponseDto.GetAlarmResponseDto>>> getAlarm(
		@RequestParam("memberSeq") Long memberSeq
	) {
		List<AlarmResponseDto.GetAlarmResponseDto> response = alarmService.getAlarm(memberSeq);

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(HttpStatus.OK.value(), response));
	}
}
