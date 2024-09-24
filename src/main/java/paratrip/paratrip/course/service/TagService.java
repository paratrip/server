//package paratrip.paratrip.course.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.reactive.function.client.WebClient;
//import paratrip.paratrip.course.entity.TouristSpot;
//import paratrip.paratrip.course.repository.TouristSpotRepository;
//import reactor.core.publisher.Mono;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class TagService {
//
//    private final WebClient.Builder webClientBuilder;
//    private final TouristSpotRepository touristSpotRepository;
//
//    private static final String GPT_API_URL = "https://api.openai.com/v1/chat/completions";
//    private static final String API_KEY = "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";
//
//    private static final List<String> COMMON_TAGS = List.of("관광", "가족", "문화", "역사", "자연", "커플", "휴양", "체험", "친구", "힐링");
//    private static final Set<String> allTags = new HashSet<>();
//
//    // 관광지 설명을 10개씩 처리하는 메서드
//    public List<String> generateTagsForTouristSpotsBatch(List<TouristSpot> touristSpotsBatch) {
//        List<String> descriptions = touristSpotsBatch.stream()
//                .map(touristSpot -> touristSpot.getRlteTatsNm() + " " + touristSpot.getBasicAddress())
//                .collect(Collectors.toList());
//
//        // GPT API에 관광지 10개의 설명을 한꺼번에 보낸다.
//        String response = callGptApi(descriptions);
//        List<String> tags = parseGPTResponse(response);
//
//        // 공통 태그 추가
//        tags.addAll(COMMON_TAGS);
//        tags = tags.stream().distinct().limit(3).collect(Collectors.toList());
//
//        for (String tag : tags) {
//            if (allTags.size() < 12) {
//                allTags.add(tag);
//            }
//        }
//
//        if (allTags.size() > 12) {
//            throw new RuntimeException("총 태그 수가 12개를 넘었습니다.");
//        }
//
//        return tags;
//    }
//
//    // API 요청 간격을 늘리기 위한 지연과 함께 API 호출
//    private String callGptApi(List<String> descriptions) {
//        Map<String, Object> messages = new HashMap<>();
//        String combinedDescriptions = String.join(", ", descriptions);
//        messages.put("role", "user");
//        messages.put("content", String.format("주어진 관광지 설명 '%s'을 바탕으로 3개의 관련 태그를 쉼표로 구분하여 생성하세요.", combinedDescriptions));
//
//        Map<String, Object> requestBody = new HashMap<>();
//        requestBody.put("model", "gpt-4o-mini");
//        requestBody.put("messages", messages);
//        requestBody.put("max_tokens", 100);
//        requestBody.put("temperature", 0.7);
//
//        WebClient webClient = webClientBuilder.build();
//
//        try {
//            // 요청 간 간격을 10초로 설정 (필요시 더 늘릴 수 있음)
//            Thread.sleep(10000);
//
//            return webClient.post()
//                    .uri(GPT_API_URL)
//                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + API_KEY)
//                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                    .body(Mono.just(requestBody), Map.class)
//                    .retrieve()
//                    .bodyToMono(String.class)
//                    .block();
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//            throw new RuntimeException("Thread was interrupted", e);
//        }
//    }
//
//    private List<String> parseGPTResponse(String response) {
//        return Arrays.stream(response.split(","))
//                .map(String::trim)
//                .collect(Collectors.toList());
//    }
//
//    // 10개씩 관광지를 처리하여 태그를 생성 및 저장하는 메서드
//    @Transactional
//    public void generateAndSaveTagsForAllSpots() {
//        List<TouristSpot> touristSpots = touristSpotRepository.findAll();
//
//        for (int i = 0; i < touristSpots.size(); i += 10) {
//            List<TouristSpot> batch = touristSpots.subList(i, Math.min(i + 10, touristSpots.size()));
//
//            // 10개의 관광지에 대한 태그 생성
//            List<String> tags = generateTagsForTouristSpotsBatch(batch);
//
//            // 각각의 관광지에 태그를 저장
//            for (TouristSpot touristSpot : batch) {
//                touristSpot.setTags(tags);
//                touristSpotRepository.save(touristSpot);
//
//                System.out.println("Tourist Spot: " + touristSpot.getRlteTatsNm() + " Tags: " + tags);
//            }
//        }
//    }
//}
