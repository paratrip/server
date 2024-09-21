package paratrip.paratrip.course.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;
import paratrip.paratrip.course.entity.TouristSpot;
import paratrip.paratrip.course.repository.TouristSpotRepository;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Service
@RequiredArgsConstructor
public class TouristSpotService {

    private final TouristSpotRepository touristSpotRepository;
    private final WebClient.Builder webClientBuilder;

    // 인코딩 처리된 서비스 키 생성 메소드
    public String encodeServiceKey(String serviceKey) {
        try {
            return URLEncoder.encode(serviceKey, "UTF-8").replace("+", "%2B");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Error encoding service key", e);
        }
    }

    String decodedServiceKey = "MlY68M1MmMMegGoGX5sZVrtEuniuCGrjlz93nJdrGeCc+j1rRAISWcEOW7+Nf8uvYkG+OhNkvsUfi4yo3+bO3g==";

    @PostConstruct
    public void init() {
        // 9개의 지역 코드와 시군구 코드를 미리 설정
        String[][] regionSignguPairs = {
                {"41", "41461"}, // 용인
                {"51", "51760"}, // 평창
                {"44", "44180"}, // 보령
                {"43", "43800"}, // 단양
                {"46", "46720"}, // 곡성
                {"51", "51750"}, // 영월
                {"52", "52130"}, // 군산
                {"48", "48890"}, // 합천
                {"46", "46130"}  // 여수
        };

        // 각 지역과 시군구에 대해 데이터를 가져와 저장
        for (String[] pair : regionSignguPairs) {
            String regionCode = pair[0];
            String signguCode = pair[1];
            fetchAndSaveTouristData(regionCode, signguCode);
        }
    }

    public void fetchAndSaveTouristData(String regionCode, String signguCode) {
        try {
            // 서비스 키 인코딩
            String encodedServiceKey = encodeServiceKey(decodedServiceKey);

            // URI를 먼저 빌드하고, 콘솔에 출력
            String uri = UriComponentsBuilder.fromHttpUrl("http://apis.data.go.kr/B551011/TarRlteTarService/areaBasedList")
                    .queryParam("serviceKey", encodedServiceKey)
                    .queryParam("numOfRows", 30)
                    .queryParam("pageNo", 1)
                    .queryParam("MobileOS", "ETC")
                    .queryParam("MobileApp", "myapp")
                    .queryParam("baseYm", "202408")
                    .queryParam("areaCd", regionCode)
                    .queryParam("signguCd", signguCode)
                    .queryParam("_type", "json")
                    .build()
                    .toUriString();

            // 생성된 URL을 출력
            System.out.println("Generated URL: " + uri);

            // WebClient로 요청을 보내기 전에 URL을 확인한 후 요청
            WebClient webClient = webClientBuilder.build();
            String response = webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            // 응답 데이터 확인
            System.out.println("API Response: " + response);

            if (response.contains("SERVICE ERROR")) {
                System.err.println("API 서비스 오류 발생: " + response);
                return;
            }

            // JSON 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode itemsNode = rootNode.path("response").path("body").path("items").path("item");

            // 데이터가 배열일 경우 각 관광지 데이터를 순회하며 저장
            if (itemsNode.isArray()) {
                for (JsonNode itemNode : itemsNode) {
                    String basicAddress = itemNode.path("rlteBsicAdres").asText();
                    String middleCategory = itemNode.path("rlteCtgryMclsNm").asText();
                    String touristSpotName = itemNode.path("tAtsNm").asText();

                    // TouristSpot 엔티티 생성 및 저장
                    if (!touristSpotRepository.existsByTouristSpotName(touristSpotName)) {
                        TouristSpot touristSpot = TouristSpot.builder()
                                .basicAddress(basicAddress)
                                .category(middleCategory)
                                .touristSpotName(touristSpotName)
                                .regionCode(regionCode)
                                .build();

                        // DB에 저장
                        touristSpotRepository.save(touristSpot);
                        System.out.println("Saved Tourist Spot: " + touristSpotName);
                    } else {
                        System.out.println("Tourist Spot already exists: " + touristSpotName);
                    }
                }
            } else {
                System.err.println("No items found in the API response.");
            }
        } catch (WebClientResponseException e) {
            System.err.println("Error response from API: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}