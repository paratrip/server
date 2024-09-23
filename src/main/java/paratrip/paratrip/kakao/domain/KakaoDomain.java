package paratrip.paratrip.kakao.domain;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoDomain {

	@Value("${kakao.client.id}")
	private String clientId;

	@Value("${kakao.redirect.uri}")
	private String redirectUri;

	public String getAccessToken(String code) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", clientId);
		params.add("redirect_uri", redirectUri);
		params.add("code", code);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

		ResponseEntity<String> response = restTemplate.exchange(
			"https://kauth.kakao.com/oauth/token",
			HttpMethod.POST,
			request,
			String.class
		);

		try {
			// Jackson ObjectMapper를 사용하여 JSON 응답 파싱
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree(response.getBody());

			// access_token 값 추출
			String accessToken = rootNode.path("access_token").asText();

			return accessToken;  // 액세스 토큰 반환
		} catch (Exception e) {
			e.printStackTrace();
			return null;  // 에러가 발생하면 null 반환
		}
	}

	public JsonNode getUserProfile(String accessToken) throws IOException {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.set("Authorization", "Bearer " + accessToken);

		HttpEntity<String> request = new HttpEntity<>("parameters", headers);

		ResponseEntity<String> response = restTemplate.exchange(
			"https://kapi.kakao.com/v2/user/me",
			HttpMethod.GET,
			request,
			String.class
		);

		// 응답 JSON 문자열을 JsonNode 객체로 변환합니다.
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readTree(response.getBody());
	}

	public JsonNode getUserFromCode(String code) throws Exception {
		RestTemplate restTemplate = new RestTemplate();

		// 액세스 토큰 요청
		ResponseEntity<String> response = restTemplate.postForEntity(
			"https://kauth.kakao.com/oauth/token?grant_type=authorization_code&client_id="
				+ clientId + "&redirect_uri="
				+ redirectUri + "&code=" + code,
			null,
			String.class
		);

		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode accessTokenNode = objectMapper.readTree(response.getBody());

		// 사용자 프로필 정보 요청
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessTokenNode.get("access_token").asText());

		HttpEntity<String> request = new HttpEntity<>(headers);
		ResponseEntity<String> userInfoResponse = restTemplate.exchange(
			"https://kapi.kakao.com/v2/user/me",
			HttpMethod.GET,
			request,
			String.class
		);

		return objectMapper.readTree(userInfoResponse.getBody());
	}

	public String getUserId(JsonNode rootNode) {
		return rootNode.path("id").asText();
	}

	public String getNickName(JsonNode rootNode) {
		return rootNode.path("kakao_account")
			.path("profile")
			.path("nickname")
			.asText();
	}

	public String getProfileImage(JsonNode rootNode) {
		return rootNode.path("kakao_account")
			.path("profile")
			.path("profile_image_url")
			.asText();
	}

	// email 값을 추출하는 메서드
	public String getEmail(JsonNode rootNode) {
		return rootNode.path("kakao_account")
			.path("email")
			.asText();
	}

	public ResponseEntity<String> logout(String accessToken) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);

		HttpEntity<String> request = new HttpEntity<>(headers);

		return restTemplate.exchange(
			"https://kapi.kakao.com/v1/user/logout",
			HttpMethod.POST,
			request,
			String.class
		);
	}
}