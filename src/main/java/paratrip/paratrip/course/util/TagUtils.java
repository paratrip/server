package paratrip.paratrip.course.util;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import paratrip.paratrip.course.entity.TouristSpot;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TagUtils {

    private final WebClient.Builder webClientBuilder;

    private static final String GPT_API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = "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";

    // 미리 정의된 12개의 태그
    private static final List<String> COMMON_TAGS = List.of(
            "관광", "가족", "문화", "역사", "자연", "커플", "휴양", "체험", "친구", "힐링", "레저", "모험"
    );

    // 관광지에 대해 GPT를 이용해 태그 생성
    public List<String> generateTagsForTouristSpot(TouristSpot touristSpot) {
        String description = touristSpot.getRlteTatsNm() + " " + touristSpot.getBasicAddress();

        // GPT API에 설명을 보낸다
        String response = callGptApi(description);
        List<String> gptTags = parseGPTResponse(response);

        // GPT 응답에서 12개의 미리 정의된 태그 중 일치하는 3개만 선택
        List<String> selectedTags = gptTags.stream()
                .filter(COMMON_TAGS::contains)  // 12개의 공통 태그 중에서만 선택
                .limit(3)                       // 최대 3개만 선택
                .collect(Collectors.toList());

        return selectedTags;
    }

    // GPT API 호출
    public String callGptApi(String description) {
        try {
            Map<String, Object> messages = new HashMap<>();
            messages.put("role", "user");
            messages.put("content", "다음 설명에 기반한 3개의 태그를 생성하세요: " + description);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "gpt-4o-mini");
            requestBody.put("messages", Collections.singletonList(messages));
            requestBody.put("max_tokens", 100);
            requestBody.put("temperature", 0.7);

            WebClient webClient = webClientBuilder.build();

            String response = webClient.post()
                    .uri(GPT_API_URL)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + API_KEY)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(Mono.just(requestBody), Map.class)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            System.out.println("API Response: " + response);
            return response;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // GPT API 응답을 파싱하여 태그 리스트 생성
    private List<String> parseGPTResponse(String response) {
        if (response == null || response.isEmpty()) {
            throw new IllegalArgumentException("GPT 응답이 없습니다.");
        }

        return Arrays.stream(response.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
    }
}