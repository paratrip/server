package paratrip.paratrip.kakao.controller;

import static paratrip.paratrip.member.service.dto.response.MemberResponseDto.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import paratrip.paratrip.core.base.BaseResponse;
import paratrip.paratrip.kakao.domain.KakaoDomain;
import paratrip.paratrip.kakao.service.KakaoService;
import paratrip.paratrip.member.service.dto.response.MemberResponseDto;

@RestController
@RequiredArgsConstructor
@Tag(name = "소셜 로그인 API", description = "담당자(박종훈)")
public class KakaoController {
	private final KakaoService kakaoService;

	@GetMapping("/login/oauth2/callback/kakao")
	public ResponseEntity<BaseResponse<LoginMemberResponseDto>> kakaoLogin(
		@RequestParam("code") String code
	) throws Exception {
		LoginMemberResponseDto response = kakaoService.loginKakaoMember(code);

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(HttpStatus.OK.value(), response));
	}
}
