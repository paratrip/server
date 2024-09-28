package paratrip.paratrip.course.util;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import paratrip.paratrip.course.entity.TouristSpot;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TagUtils {

    private final WebClient.Builder webClientBuilder;

    private static final String GPT_API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = "sk-d8oS68Brm2CDz7-3DAhjPGJzTR9WkQFhKjWD8R_KLDT3BlbkFJ0TLQ8rKFuG4Z09-nXH8QgiaEB3zOJuQmZAzL04xJcA";

    // 미리 정의된 12개의 태그
    private static final String[] COMMON_TAGS = {
            "이색", "가족", "문화", "역사", "자연", "커플", "휴양", "체험", "친구", "힐링", "레저", "모험"
    };

    /**
     * 단일 태그 생성 메서드
     * @param touristSpot 관광지 정보
     * @return 하나의 태그 (String)
     */
    public String generateTagForTouristSpot(TouristSpot touristSpot) {  // 메서드가 이 클래스에 추가되어야 합니다.
        String description = touristSpot.getRlteTatsNm() + " " + touristSpot.getBasicAddress();

        // GPT API에 설명을 보낸다
        String response = callGptApi(description);

        // GPT 응답을 파싱하여 단일 태그를 반환
        if (response != null) {
            return parseSingleTagFromResponse(response);
        }
        return "기본";  // 태그를 생성하지 못할 경우 기본 태그 반환
    }

    public String callGptApi(String description) {
        try {
            Map<String, Object> messages = new HashMap<>();
            messages.put("role", "user");

            // GPT에게 12개의 태그 중에서만 선택하도록 요청
            String tagOptions = String.join(", ", COMMON_TAGS); // 12개의 태그를 문자열로 변환
            messages.put("content", "다음 설명에 기반하여 '" + tagOptions + "' 중에서 가장 적합한 태그 하나를 선택하세요: " + description);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "gpt-4");
            requestBody.put("messages", java.util.Collections.singletonList(messages));
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

    // GPT API 응답을 파싱하여 단일 태그를 반환
    private String parseSingleTagFromResponse(String response) {
        if (response == null || response.isEmpty()) {
            throw new IllegalArgumentException("GPT 응답이 없습니다.");
        }

        // 응답 문자열에서 가장 적합한 단일 태그를 추출 (단일 태그를 반환하는 로직)
        for (String tag : COMMON_TAGS) {
            if (response.contains(tag)) {
                return tag;  // 응답에 포함된 첫 번째 태그 반환
            }
        }
        return "기타";  // 일치하는 태그가 없을 경우 기본 태그로 "기타" 반환
    }
}
