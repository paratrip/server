package paratrip.paratrip.member.controller;

import static paratrip.paratrip.member.vo.request.MemberRequestVo.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import paratrip.paratrip.core.base.BaseResponse;
import paratrip.paratrip.core.exception.GlobalExceptionHandler;
import paratrip.paratrip.member.service.MemberService;
import paratrip.paratrip.member.validates.VerifyEmailMemberValidator;
import paratrip.paratrip.member.validates.VerifyPasswordMemberValidator;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
	private final VerifyEmailMemberValidator verifyEmailMemberValidator;
	private final VerifyPasswordMemberValidator verifyPasswordMemberValidator;

	private final MemberService memberService;

	@PostMapping(value = "verify-email", name = "이메일 인증 (중복 확인 + 이메일 형식 검사)")
	@Operation(summary = "이메일 인증 (중복 확인 + 이메일 형식 검사) API", description = "이메일 인증 (중복 확인 + 이메일 형식 검사) API")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "요청에 성공하였습니다.",
			useReturnTypeSchema = true),
		@ApiResponse(
			responseCode = "S500",
			description = "500 SERVER_ERROR (나도 몰라 ..)",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "B001",
			description = "400 Invalid DTO Parameter errors / 요청 값 형식 요류",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "EDC001",
			description = "409 EMAIL_DUPLICATION_CONFLICT_EXCEPTION / 이메일 중복 요류",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
	})
	public ResponseEntity<BaseResponse> addHideFeed(
		@Valid
		@RequestBody VerifyEmailMemberRequest request
	) {
		// 유효성 검사
		verifyEmailMemberValidator.validate(request);

		// VO -> DTO
		memberService.verifyMemberEmail(request.toVerifyEmailMemberRequestDto());

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(HttpStatus.OK.value(), "SUCCESS"));
	}

	@PostMapping(value = "verify-password", name = "비밀번호 유효성 검사 (형식: 영문 + 숫자 + 특수문자 포함 8자리)")
	@Operation(summary = "비밀번호 유효성 검사 (형식: 영문 + 숫자 + 특수문자 포함 8자리) API", description = "비밀번호 유효성 검사 (형식: 영문 + 숫자 + 특수문자 포함 8자리) API")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "요청에 성공하였습니다.",
			useReturnTypeSchema = true),
		@ApiResponse(
			responseCode = "S500",
			description = "500 SERVER_ERROR (나도 몰라 ..)",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "B001",
			description = "400 Invalid DTO Parameter errors / 요청 값 형식 요류",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
	})
	public ResponseEntity<BaseResponse> addHideFeed(
		@Valid
		@RequestBody VerifyPasswordMemberRequest request
	) {
		// 유효성 검사
		verifyPasswordMemberValidator.validate(request);

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(HttpStatus.OK.value(), "SUCCESS"));
	}
}
