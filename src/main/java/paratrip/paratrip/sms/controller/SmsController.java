package paratrip.paratrip.sms.controller;

import static paratrip.paratrip.sms.dto.UserDto.*;
import static paratrip.paratrip.sms.vo.SmsSendRequestVo.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import paratrip.paratrip.core.base.BaseResponse;
import paratrip.paratrip.sms.service.SmsService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sms-certification")
@Tag(name = "핸드폰 인증 API", description = "담당자(박종훈)")
public class SmsController {
	private final SmsService userService;

	@PostMapping("/send")
	public ResponseEntity<?> sendSms(@RequestBody SendSmsRequest request) {
		userService.sendSms(request);
		return ResponseEntity.ok().body(BaseResponse.ofSuccess(HttpStatus.OK.value(), "SUCCESS"));
	}

	@PostMapping("/confirm")
	public ResponseEntity<?> SmsVerification(@RequestBody SmsCertificationRequest requestDto) {
		userService.verifySms(requestDto);
		return ResponseEntity.ok().body(BaseResponse.ofSuccess(HttpStatus.OK.value(), "SUCCESS"));
	}
}
