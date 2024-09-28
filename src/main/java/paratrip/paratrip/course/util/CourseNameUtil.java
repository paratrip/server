package paratrip.paratrip.course.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CourseNameUtil {

    private final WebClient.Builder webClientBuilder;

    private static final String GPT_API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = "sk-d8oS68Brm2CDz7-3DAhjPGJzTR9WkQFhKjWD8R_KLDT3BlbkFJ0TLQ8rKFuG4Z09-nXH8QgiaEB3zOJuQmZAzL04xJcA";

    /**
     * GPT-4 API를 사용하여 코스 이름 생성
     * @param paraglidingName 패러글라이딩 이름
     * @param spotNames 관광지 이름 리스트
     * @param categories 관광지 카테고리 리스트
     * @return 생성된 코스 이름
     */
    public String generateCourseName(String paraglidingName, List<String> spotNames, List<String> categories) {
        // GPT에게 코스 이름을 생성하도록 요청 (코스 이름 생성을 위한 입력 텍스트 구성)
        String content = "다음 패러글라이딩 이름과 관광지 이름, 카테고리를 바탕으로 흥미로운 코스 이름을 20글자수 이내로 생성하세요: " +
                "패러글라이딩 이름: " + paraglidingName + ", 관광지 이름: " + String.join(", ", spotNames) +
                ", 카테고리: " + String.join(", ", categories);

        // GPT-4 API 호출
        String response = callGptApi(content);

        // 응답이 null이 아니면 이름으로 사용, 그렇지 않으면 기본 이름 반환
        return response != null ? parseCourseNameFromResponse(response) : "기본 코스 이름";
    }

    /**
     * GPT API 호출 메서드
     * @param description 요청할 설명 텍스트
     * @return GPT 응답 문자열
     */
    private String callGptApi(String description) {
        try {
            Map<String, Object> messages = new HashMap<>();
            messages.put("role", "user");
            messages.put("content", description);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "gpt-4");
            requestBody.put("messages", java.util.Collections.singletonList(messages));
            requestBody.put("max_tokens", 100);
            requestBody.put("temperature", 0.7);

            WebClient webClient = webClientBuilder.build();

            // GPT-4 API에 POST 요청 보내기
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

    /**
     * GPT API 응답을 파싱하여 코스 이름 추출
     * @param response GPT 응답 문자열 (JSON 형식)
     * @return 코스 이름
     */
    private String parseCourseNameFromResponse(String response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response);

            // 응답의 `choices[0].message.content` 값 추출
            String courseName = root.path("choices").get(0).path("message").path("content").asText();

            return courseName != null && !courseName.isEmpty() ? courseName.trim() : "기본 코스 이름";
        } catch (Exception e) {
            e.printStackTrace();
            return "기본 코스 이름";
        }
    }
}
