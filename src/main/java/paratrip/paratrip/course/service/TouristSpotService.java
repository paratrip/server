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
import paratrip.paratrip.course.dto.TouristSpotResponseDto;
import paratrip.paratrip.course.entity.RegionCode;
import paratrip.paratrip.course.entity.TouristSpot;
import paratrip.paratrip.course.repository.TouristSpotRepository;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TouristSpotService {

    private final TouristSpotRepository touristSpotRepository;
    private final WebClient.Builder webClientBuilder;
    private final CourseService courseService;  // CourseService 의존성 주입

    private String decodedServiceKey = "0PDIwA52myrKA7RqVYA5cWJjNY58QPdUDUbL+YIfRKljfui3fmtvA5TBoTkUAybLnTgcI3I6SZVEgHdNPGljuw==";

    // 애플리케이션 시작 시 자동으로 데이터를 처리하고 코스 생성
    @PostConstruct
    public void init() throws InterruptedException {
        // DB에 데이터가 있는지 확인
        if (touristSpotRepository.count() == 0) {  // TouristSpot 테이블이 비어있을 때만 실행
            fetchAndSaveTouristSpots();
            courseService.generateCourses();  // 데이터 저장 후 코스 생성
        } else {
            System.out.println("Tourist spots already exist in the database. Skipping data fetch.");
        }
    }

    // 관광지 데이터를 여러 지역에서 불러오고 저장하는 메서드
    public void fetchAndSaveTouristSpots() throws InterruptedException {
        String[][] regionSignguPairs = {
                {"51", "51760"}, // 평창
                {"44", "44180"}, // 보령
                {"43", "43800"}, // 단양
                {"46", "46720"}, // 곡성
                {"51", "51750"}, // 영월
                {"52", "52130"}, // 군산
                {"48", "48890"}, // 합천
                {"46", "46130"}  // 여수
        };

        // 각 지역 데이터를 불러오고 저장
        for (String[] pair : regionSignguPairs) {
            String regionCode = pair[0];
            String signguCode = pair[1];
            fetchAndSaveTouristData(regionCode, signguCode);
            Thread.sleep(1000); // 각 요청 간 1초 대기
        }
    }

    // 특정 지역에 대한 관광지 데이터를 API로 가져와 저장하는 메서드
    public void fetchAndSaveTouristData(String regionCode, String signguCode) {
        try {
            // URI 빌더의 인코딩 설정
            DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory();
            factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
            WebClient webClient = webClientBuilder.uriBuilderFactory(factory).build();

            // API URL 생성
            URI url = UriComponentsBuilder.fromUriString("http://apis.data.go.kr/B551011/TarRlteTarService/areaBasedList")
                    .queryParam("serviceKey", URLEncoder.encode(decodedServiceKey, StandardCharsets.UTF_8))
                    .queryParam("numOfRows", 50)
                    .queryParam("pageNo", 1)
                    .queryParam("MobileOS", "ETC")
                    .queryParam("MobileApp", "myapp")
                    .queryParam("baseYm", "202408")
                    .queryParam("areaCd", regionCode)
                    .queryParam("signguCd", signguCode)
                    .queryParam("_type", "json")
                    .build(true)
                    .toUri();

            // API 호출 및 응답 받기
            String response = webClient.get().uri(url).retrieve().bodyToMono(String.class).block();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode itemsNode = rootNode.path("response").path("body").path("items").path("item");

            // 데이터를 저장하기 위한 로직
            if (itemsNode.isArray()) {
                for (JsonNode itemNode : itemsNode) {
                    String basicAddress = itemNode.path("rlteBsicAdres").asText();
                    String middleCategory = itemNode.path("rlteCtgryMclsNm").asText();
                    String tAtsNm = itemNode.path("rlteTatsNm").asText();

                    // 이미지 검색 API 호출하여 이미지 URL 가져오기
                    String imageUrl = fetchImageUrl(tAtsNm);

                    // 이미지 URL이 있으면 TouristSpot 저장
                    if (imageUrl != null) {
                        TouristSpot touristSpot = TouristSpot.builder()
                                .basicAddress(basicAddress)
                                .category(middleCategory)
                                .rlteTatsNm(tAtsNm)
                                .regionCode(regionCode)
                                .imageUrl(imageUrl)
                                .build();

                        // 데이터 중복 체크 후 저장
                        if (!touristSpotRepository.existsByRlteTatsNm(tAtsNm)) {
                            touristSpotRepository.save(touristSpot);
                        }
                    }
                }
            }
        } catch (WebClientResponseException e) {
            System.err.println("API 호출 에러: " + e.getStatusCode() + " - " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 이미지 검색 API를 호출하는 메서드
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

            // 이미지 검색 API 호출
            String response = webClient.get().uri(url).retrieve().bodyToMono(String.class).block();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode itemsNode = rootNode.path("response").path("body").path("items").path("item");

            // 이미지 URL 추출
            if (itemsNode.isArray() && itemsNode.size() > 0) {
                return itemsNode.get(0).path("galWebImageUrl").asText();
            } else {
                return null; // 이미지가 없으면 null 반환
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 관광지 리스트 조회
    // 관광지 리스트 조회
    public List<TouristSpotResponseDto> getRecommendedTouristSpots() {
        List<TouristSpot> touristSpots = touristSpotRepository.findAll();

        // TouristSpot 엔티티를 record로 변환하여 반환
        return touristSpots.stream()
                .map(spot -> new TouristSpotResponseDto(
                        spot.getCategory(),
                        spot.getRegionCode(),
                        getRegionNameFromCode(spot.getRegionCode()), // 지역 이름 변환
                        spot.getRlteTatsNm(),
                        spot.getBasicAddress(),
                        spot.getImageUrl(),
                        spot.getTags()
                ))
                .collect(Collectors.toList());
    }

    // 지역 코드를 지역 이름으로 변환하는 메서드
    private String getRegionNameFromCode(String regionCode) {
        Optional<RegionCode> region = findByCode(regionCode);
        return region.map(Enum::name).orElse("Unknown Region");  // 지역 코드를 지역 이름으로 변환
    }

    // 지역 코드로부터 RegionCode 열거형을 찾는 메서드
    private Optional<RegionCode> findByCode(String code) {
        for (RegionCode region : RegionCode.values()) {
            if (region.getCode().equals(code)) {
                return Optional.of(region);
            }
        }
        return Optional.empty();  // 코드가 없을 경우
    }
}
