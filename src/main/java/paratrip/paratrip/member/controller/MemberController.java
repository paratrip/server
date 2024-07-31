package paratrip.paratrip.member.controller;

import static paratrip.paratrip.member.dto.response.MemberResponseDto.*;
import static paratrip.paratrip.member.vo.request.MemberRequestVo.*;
import static paratrip.paratrip.member.vo.response.MemberResponseVo.*;

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
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import paratrip.paratrip.core.base.BaseResponse;
import paratrip.paratrip.core.exception.GlobalExceptionHandler;
import paratrip.paratrip.member.service.MemberService;
import paratrip.paratrip.member.validates.FindMemberEmailValidator;
import paratrip.paratrip.member.validates.JoinMemberValidator;
import paratrip.paratrip.member.validates.LoginMemberValidator;
import paratrip.paratrip.member.validates.LogoutMemberValidator;
import paratrip.paratrip.member.validates.ResetMemberPasswordValidator;
import paratrip.paratrip.member.validates.VerifyEmailMemberValidator;
import paratrip.paratrip.member.validates.VerifyPasswordMemberValidator;
import paratrip.paratrip.member.validates.VerifyPhoneNumberMemberValidator;
import paratrip.paratrip.member.validates.VerifyUserIdMemberValidator;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Tag(name = "회원 API", description = "담당자(박종훈)")
public class MemberController {
	private final VerifyEmailMemberValidator verifyEmailMemberValidator;
	private final VerifyPasswordMemberValidator verifyPasswordMemberValidator;
	private final VerifyUserIdMemberValidator verifyUserIdMemberValidator;
	private final VerifyPhoneNumberMemberValidator verifyPhoneNumberMemberValidator;
	private final JoinMemberValidator joinMemberValidator;
	private final LoginMemberValidator loginMemberValidator;
	private final LogoutMemberValidator logoutMemberValidator;
	private final FindMemberEmailValidator findMemberEmailValidator;
	private final ResetMemberPasswordValidator resetMemberPasswordValidator;

	private final MemberService memberService;

	@PostMapping(value = "verify-email", name = "이메일 유효성 검사 (중복 확인 + 이메일 형식 검사)")
	@Operation(summary = "이메일 유효성 검사 (중복 확인 + 이메일 형식 검사) API", description = "이메일 유효성 검사 (중복 확인 + 이메일 형식 검사) API")
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
	public ResponseEntity<BaseResponse> verifyEmail(
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
	public ResponseEntity<BaseResponse> verifyPassword(
		@Valid
		@RequestBody VerifyPasswordMemberRequest request
	) {
		// 유효성 검사
		verifyPasswordMemberValidator.validate(request);

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(HttpStatus.OK.value(), "SUCCESS"));
	}

	@PostMapping(value = "verify-userId", name = "아이디 중복 유효성 검사")
	@Operation(summary = "아이디 중복 유효성 검사 API", description = "아이디 중복 유효성 검사 API")
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
			responseCode = "UIDC002",
			description = "USER_ID_DUPLICATION_CONFLICT_EXCEPTION / userId 중복 요류",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
	})
	public ResponseEntity<BaseResponse> verifyUserId(
		@Valid
		@RequestBody VerifyUserIdMemberRequest request
	) {
		// 유효성 검사
		verifyUserIdMemberValidator.validate(request);

		memberService.verifyMemberUserId(request.toVerifyUserIdMemberRequestDto());

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(HttpStatus.OK.value(), "SUCCESS"));
	}

	@PostMapping(value = "verify-phone", name = "핸드폰 번호 유효성 + 중복 검사(010-XXXX-XXXX 형식)")
	@Operation(summary = "핸드폰 번호 유효성 검사 API", description = "핸드폰 번호 유효성 검사 API")
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
			responseCode = "PNDC003",
			description = "409 PHONE_NUMBER_DUPLICATION_CONFLICT_EXCEPTION / 전화번호 중복 요류",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class)))
	})
	public ResponseEntity<BaseResponse> verifyPhoneNumber(
		@Valid
		@RequestBody VerifyPhoneNumberMemberRequest request
	) {
		// 유효성 검사
		verifyPhoneNumberMemberValidator.validate(request);

		memberService.verifyMemberPhoneNumber(request.toVerifyPhoneNumberMemberRequestDto());

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(HttpStatus.OK.value(), "SUCCESS"));
	}

	@PostMapping(value = "join", name = "회원 가입 (이전 Verify URL 전부 통과 이후 회원 가입 URL 요청 (EMAIL, USERID 중복 처리 똑같이 진행.))")
	@Operation(summary = "회원 가입 요청 API", description = "회원 가입 요청 API (Gender의 경우 MALE, FEMAIL로 구분됩니다.)")
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
			responseCode = "UIDC002",
			description = "USER_ID_DUPLICATION_CONFLICT_EXCEPTION / userId 중복 요류",
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
	public ResponseEntity<BaseResponse> joinMember(
		@Valid
		@RequestBody JoinMemberRequest request
	) {
		// 유효성 검사
		joinMemberValidator.validate(request);

		memberService.joinMember(request.toJoinMemberRequestDto());

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(HttpStatus.OK.value(), "SUCCESS"));
	}

	@PostMapping(value = "login", name = "로그인")
	@Operation(summary = "로그인 API", description = "로그인 (핸드폰 형식은 010-XXXX-XXXX로 들어와야합니다.)")
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
			responseCode = "ENF001",
			description = "404 EMAIL_NOT_FOUND_EXCEPTION / 존재하지 않는 이메일 요류",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "PB002",
			description = "400 PASSWORD_BAD_REQUEST_EXCEPTION / 비밀번호 일치 요류",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
	})
	public ResponseEntity<BaseResponse<LoginMemberResponse>> loginMember(
		@Valid
		@RequestBody LoginMemberRequest request
	) {
		// 유효성 검사
		loginMemberValidator.validate(request);

		// VO -> DTO
		LoginMemberResponseDto loginMemberResponseDto = memberService.loginMember(request.toLoginMemberRequestDto());

		// DTO -> VO
		LoginMemberResponse response = new LoginMemberResponse(
			loginMemberResponseDto.memberSeq(),
			loginMemberResponseDto.accessToken(),
			loginMemberResponseDto.refreshToken()
		);

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(HttpStatus.OK.value(), response));
	}

	@PostMapping(value = "logout", name = "로그아웃")
	@Operation(summary = "로그아웃 API", description = "로그아웃")
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
			responseCode = "MSB003",
			description = "400 MEMBER_SEQ_BAD_REQUEST_EXCEPTION / Member Seq 요류",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
	})
	public ResponseEntity<BaseResponse> logoutMember(
		@Valid
		@RequestBody LogoutMemberRequest request
	) {
		// 유효성 검사
		logoutMemberValidator.validate(request);

		memberService.logoutMember(request.toLogoutMemberRequestDto());

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(HttpStatus.OK.value(), "SUCCESS"));
	}

	@PostMapping(value = "/find-email", name = "이메일 찾기")
	@Operation(summary = "이메일 찾기 API", description = "이메일 찾기 (API 활용 전 SMS API를 통해 핸드폰 인증 완료 후 이메일 반환 필요)")
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
			responseCode = "PNNF002",
			description = "404 PHONE_NUMBER_NOT_FOUND_EXCEPTION / 전화번호 존재 하지 않는 요류",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
	})
	public ResponseEntity<BaseResponse<FindMemberEmailResponse>> findMemberEmail(
		@Valid
		@RequestBody FindMemberEmailRequest request
	) {
		// 유효성 검사
		findMemberEmailValidator.validate(request);

		FindMemberEmailResponseDto findMemberEmailResponseDto
			= memberService.findMemberEmail(request.toFindMemberEmailRequestDto());

		// DTO -> VO
		FindMemberEmailResponse response = new FindMemberEmailResponse(findMemberEmailResponseDto.email());

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(HttpStatus.OK.value(), response));
	}

	@PostMapping(value = "/reset-password", name = "비밀번호 재설정")
	@Operation(summary = "비밀번호 재설정 API", description = "비밀번호 재설정 (API 활용 전 SMS API를 통해 핸드폰 인증 완료 후 비밀번호 재설정 필요)")
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
			responseCode = "PNNF002",
			description = "404 PHONE_NUMBER_NOT_FOUND_EXCEPTION / 전화번호 존재 하지 않는 요류",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
	})
	public ResponseEntity<BaseResponse> resetMemberPassword(
		@Valid
		@RequestBody ResetMemberPasswordRequest request
	) {
		// 유효성 검사
		resetMemberPasswordValidator.validate(request);

		memberService.resetMemberPassword(request.toResetMemberPasswordRequestDto());

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(HttpStatus.OK.value(), "SUCCESS"));
	}
}
