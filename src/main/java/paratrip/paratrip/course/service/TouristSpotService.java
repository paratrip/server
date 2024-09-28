package paratrip.paratrip.course.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;
import paratrip.paratrip.course.dto.TouristSpotResponseDto;
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

    private String decodedServiceKey = "NDLtSM572Ch47DMMGJM2Y+uJw0fLRP9A72MpqqlRqinBhr7pdMMS5y3vyXc83NpOUF9FD5sSFs3HLnTIZ1B0KQ==";

    // 애플리케이션 시작 시 관광지 데이터를 자동으로 불러와 저장
    @PostConstruct
    public void init() throws InterruptedException {
        // TouristSpot 테이블이 비어 있을 때만 데이터를 불러옴
        if (touristSpotRepository.count() == 0) {
            fetchAndSaveTouristSpots();
        } else {
            System.out.println("관광지 데이터가 이미 DB에 존재합니다. 데이터 가져오기 작업을 건너뜁니다.");
        }
    }

    // 모든 지역에 대한 관광지 데이터를 불러오고 저장
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

    // 특정 지역과 시군구에 대한 관광지 데이터를 API로 가져와 저장
    public void fetchAndSaveTouristData(String regionCode, String signguCode) {
        try {
            DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory();
            factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
            WebClient webClient = webClientBuilder.uriBuilderFactory(factory).build();

            URI url = UriComponentsBuilder.fromUriString("http://apis.data.go.kr/B551011/TarRlteTarService/areaBasedList")
                    .queryParam("serviceKey", URLEncoder.encode(decodedServiceKey, StandardCharsets.UTF_8))
                    .queryParam("numOfRows", 40)
                    .queryParam("pageNo", 1)
                    .queryParam("MobileOS", "ETC")
                    .queryParam("MobileApp", "myapp")
                    .queryParam("baseYm", "202408")
                    .queryParam("areaCd", regionCode)
                    .queryParam("signguCd", signguCode)
                    .queryParam("_type", "json")
                    .build(true)
                    .toUri();

            String response = webClient.get().uri(url).retrieve().bodyToMono(String.class).block();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode itemsNode = rootNode.path("response").path("body").path("items").path("item");

            if (itemsNode.isArray()) {
                for (JsonNode itemNode : itemsNode) {
                    String basicAddress = itemNode.path("rlteBsicAdres").asText();
                    String middleCategory = itemNode.path("rlteCtgryMclsNm").asText(); // 카테고리 확인
                    String tAtsNm = itemNode.path("rlteTatsNm").asText();

                    // 카테고리가 '음식'일 경우 제외
                    if (middleCategory.equalsIgnoreCase("음식")) {
                        continue;
                    }

                    // 이미지 검색 API를 통해 이미지 URL 가져오기
                    String imageUrl = fetchImageUrl(tAtsNm);

                    // 이미지가 존재할 경우에만 TouristSpot 데이터 저장
                    if (imageUrl != null && !imageUrl.isEmpty()) {
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

    // 특정 키워드에 대해 이미지 검색 API 호출
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

            String response = webClient.get().uri(url).retrieve().bodyToMono(String.class).block();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode itemsNode = rootNode.path("response").path("body").path("items").path("item");

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

    public void mapImagesToTouristSpots() {
        List<TouristSpot> spots = touristSpotRepository.findAll();
        for (TouristSpot spot : spots) {
            String imageUrl = fetchImageUrl(spot.getRlteTatsNm());
            if (imageUrl != null) {
                spot.setImageUrl(imageUrl);
                touristSpotRepository.save(spot);
            }
        }
        System.out.println("모든 관광지에 이미지가 매핑되었습니다.");
    }

    public List<TouristSpotResponseDto> getRecommendedTouristSpots() {
        List<TouristSpot> touristSpots = touristSpotRepository.findAll();

        // TouristSpot 엔티티를 TouristSpotResponseDto로 변환하여 반환
        return touristSpots.stream()
                .map(spot -> new TouristSpotResponseDto(
                        spot.getCategory(),
                        spot.getRegionCode(),
                        spot.getRegionCode(),  // 예시: 실제 지역명을 사용하거나, 필요한 필드 사용
                        spot.getRlteTatsNm(),
                        spot.getBasicAddress(),
                        spot.getImageUrl(),
                        spot.getTag()
                ))
                .collect(Collectors.toList());
    }

    /**
     * 이미지가 없는 관광지를 삭제하는 메서드.
     */
    @Transactional
    public void deleteSpotsWithoutImages() {
        List<TouristSpot> spotsWithoutImages = touristSpotRepository.findAll()
                .stream()
                .filter(spot -> spot.getImageUrl() == null || spot.getImageUrl().isEmpty())  // 이미지가 없는 관광지 필터링
                .collect(Collectors.toList());

        if (!spotsWithoutImages.isEmpty()) {
            touristSpotRepository.deleteAll(spotsWithoutImages);  // 이미지가 없는 관광지 삭제
            System.out.println(spotsWithoutImages.size() + "개의 이미지가 없는 관광지가 삭제되었습니다.");
        } else {
            System.out.println("삭제할 이미지가 없는 관광지가 없습니다.");
        }
    }

    /**
     * 관광지에 이미지 매핑 후 이미지가 없는 관광지 삭제.
     */
    @Transactional
    public void mapImagesAndCleanUp() {
        mapImagesToTouristSpots();  // 1. 이미지 매핑 작업 수행
        deleteSpotsWithoutImages();  // 2. 이미지가 없는 관광지 삭제
    }
}
