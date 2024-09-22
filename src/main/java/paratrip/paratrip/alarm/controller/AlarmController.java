package paratrip.paratrip.alarm.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import paratrip.paratrip.alarm.service.AlarmService;
import paratrip.paratrip.alarm.service.dto.response.AlarmResponseDto;
import paratrip.paratrip.core.base.BaseResponse;

@RestController
@RequestMapping("/alarm")
@RequiredArgsConstructor
@Tag(name = "알람 API")
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
