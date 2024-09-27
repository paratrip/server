package paratrip.paratrip.course.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import paratrip.paratrip.course.dto.TouristSpotResponseDto;
import paratrip.paratrip.course.entity.TouristSpot;
import paratrip.paratrip.course.service.TouristSpotService;

import java.util.List;

@RestController
@RequestMapping("/api/tourist-spots")
@RequiredArgsConstructor
@Tag(name = "관광지 데이터 관리 API", description = "관광지 데이터 가져오기, 이미지 매핑 API, 백엔드에서 사용")
public class TouristSpotController {

    private final TouristSpotService touristSpotService;

    @Operation(summary = "관광지 데이터 가져오기", description = "지역과 시군구에 따라 관광지 데이터를 가져옵니다.")
    @Parameters({
            @Parameter(name = "region", description = "지역 코드", example = "11"),
            @Parameter(name = "signgu", description = "시군구 코드", example = "112233")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "관광지 데이터 가져오기 작업이 시작되었습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 지역 또는 시군구 입력"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @PostMapping("/fetch/{region}/{signgu}")
    public ResponseEntity<String> fetchTouristData(@PathVariable("region") String region, @PathVariable("signgu") String signgu) {
        touristSpotService.fetchAndSaveTouristData(region, signgu);
        return ResponseEntity.ok("관광지 데이터 가져오기가 시작되었습니다.");
    }

    /**
     * 이미지 매핑 및 이미지 없는 관광지 삭제 API.
     */
    @Operation(summary = "이미지 매핑 및 이미지 없는 관광지 삭제", description = "관광지의 이름을 바탕으로 이미지를 검색하고 매핑한 후, 이미지가 없는 관광지를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 이미지가 매핑되고, 이미지가 없는 관광지가 삭제되었습니다."),
            @ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.")
    })
    @PostMapping("/map-images-and-cleanup")
    public ResponseEntity<String> mapImagesAndCleanUp() {
        try {
            touristSpotService.mapImagesAndCleanUp();
            return ResponseEntity.ok("이미지가 관광지에 매핑되었으며, 이미지가 없는 관광지가 삭제되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("이미지 매핑 및 정리 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    @Operation(summary = "추천 관광지 리스트 조회", description = "추천 여행지의 리스트를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 추천 여행지 리스트를 조회했습니다.")
    })
    @GetMapping("/recommended")
    public ResponseEntity<List<TouristSpotResponseDto>> getRecommendedTouristSpots() {
        List<TouristSpotResponseDto> recommendedTouristSpots = touristSpotService.getRecommendedTouristSpots();
        return ResponseEntity.ok(recommendedTouristSpots);
    }
}
