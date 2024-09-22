package paratrip.paratrip.course.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;
import paratrip.paratrip.course.entity.TouristSpot;
import paratrip.paratrip.course.repository.TouristSpotRepository;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class TouristSpotService {

    private final TouristSpotRepository touristSpotRepository;
    private final WebClient.Builder webClientBuilder;

    String decodedServiceKey = "GqNKkxUkL86uGd2y/xTIoabmqZwi0BQqyPUWRaafwi3pfYSDud9IvxKnBNI1gpFafvC05XZ0H4sCwGEyH2//YA==";

//    @PostConstruct
//    public void init() throws InterruptedException {
//        // 8개의 지역 코드와 시군구 코드를 설정
//        String[][] regionSignguPairs = {
//                {"51", "51760"}, // 평창
//                {"44", "44180"}, // 보령
//                {"43", "43800"}, // 단양
//                {"46", "46720"}, // 곡성
//                {"51", "51750"}, // 영월
//                {"52", "52130"}, // 군산
//                {"48", "48890"}, // 합천
//                {"46", "46130"}  // 여수
//        };
//
//        // 각 지역과 시군구에 대해 데이터를 가져와 저장
//        for (String[] pair : regionSignguPairs) {
//            String regionCode = pair[0];
//            String signguCode = pair[1];
//            fetchAndSaveTouristData(regionCode, signguCode);
//            Thread.sleep(10);
//
//        }
//    }

    public void fetchAndSaveTouristData(String regionCode, String signguCode) {
        try {
            // URI 빌더의 인코딩 모드 설정
            DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory();
            factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

            // WebClientBuilder에 적용
            WebClient webClient = webClientBuilder.uriBuilderFactory(factory).build();

            // 관광 정보 API 호출
            URI url = UriComponentsBuilder.fromUriString("http://apis.data.go.kr/B551011/TarRlteTarService/areaBasedList")
                    .queryParam("serviceKey", URLEncoder.encode(decodedServiceKey, StandardCharsets.UTF_8))
                    .queryParam("numOfRows", 100)
                    .queryParam("pageNo", 1)
                    .queryParam("MobileOS", "ETC")
                    .queryParam("MobileApp", "myapp")
                    .queryParam("baseYm", "202408")
                    .queryParam("areaCd", regionCode)
                    .queryParam("signguCd", signguCode)
                    .queryParam("_type", "json")
                    .build(true)
                    .toUri();

            // 생성된 URL을 출력
            System.out.println("Generated URL: " + url);

            String response = webClient.get().uri(url).retrieve().bodyToMono(String.class).block();

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
                    String tAtsNm = itemNode.path("rlteTatsNm").asText();
                    String rlteCtgrySclsNm = itemNode.path("rlteCtgrySclsNm").asText();

                    // TouristSpot 엔티티 생성 및 저장 전에 이미지 검색 API 호출
                    String imageUrl = fetchImageUrl(tAtsNm);

                    // 이미지가 있을 경우에만 TouristSpot 저장
                    if (imageUrl != null) {
                        TouristSpot touristSpot = TouristSpot.builder()
                                .basicAddress(basicAddress)
                                .category(middleCategory)
                                .rlteTatsNm(tAtsNm)
                                .rlteCtgrySclsNm(rlteCtgrySclsNm)
                                .regionCode(regionCode)
                                .imageUrl(imageUrl)
                                .build();

                        // DB에 저장
                        if (!touristSpotRepository.existsByRlteTatsNm(tAtsNm)) {
                            touristSpotRepository.save(touristSpot);
                            System.out.println("Saved Tourist Spot: " + tAtsNm);
                        } else {
                            System.out.println("Tourist Spot already exists: " + tAtsNm);
                        }
                    } else {
                        System.out.println("No image found for Tourist Spot: " + tAtsNm);
                    }
                }
            } else {
                System.err.println("No items found in the API response.");
            }
        } catch (WebClientResponseException e) {
            System.err.println("Error response from API: " + e.getStatusCode() + " - " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 이미지 검색 API 호출 메서드
    public String fetchImageUrl(String keyword) {
        try {
            WebClient webClient = webClientBuilder.build();

            URI url = UriComponentsBuilder.fromUriString("http://apis.data.go.kr/B551011/PhotoGalleryService1/gallerySearchList1")
                    .queryParam("serviceKey", URLEncoder.encode(decodedServiceKey, StandardCharsets.UTF_8))
                    .queryParam("numOfRows", 1)
                    .queryParam("pageNo", 1)
                    .queryParam("MobileOS", "ETC")
                    .queryParam("MobileApp", "myapp")
                    .queryParam("arrange", "A")
                    .queryParam("keyword", URLEncoder.encode(keyword, StandardCharsets.UTF_8))
                    .queryParam("_type", "json")
                    .build(true)
                    .toUri();

            // 이미지 검색 요청
            String response = webClient.get().uri(url).retrieve().bodyToMono(String.class).block();

            // JSON 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode itemsNode = rootNode.path("response").path("body").path("items").path("item");

            // 첫 번째 이미지 URL을 반환 (있다면)
            if (itemsNode.isArray() && itemsNode.size() > 0) {
                return itemsNode.get(0).path("galWebImageUrl").asText();
            } else {
                return null;  // 이미지가 없을 경우
            }
        } catch (WebClientResponseException e) {
            System.err.println("Error response from Image API: " + e.getStatusCode() + " - " + e.getMessage());
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
