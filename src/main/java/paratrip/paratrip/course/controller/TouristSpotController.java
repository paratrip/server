package paratrip.paratrip.course.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import paratrip.paratrip.course.dto.TouristSpotResponseDto;
import paratrip.paratrip.course.service.TouristSpotService;

import java.util.List;

@RestController
@RequestMapping("/api/tourist-spots")
@RequiredArgsConstructor
@Tag(name = "관광코스 조회 API", description = "지역별로 선택하는걸 추가해야합니다. 데이터형만 맞춰놓으시면 그거에 맞게 수정하겠습니다")
public class TouristSpotController {

    private final TouristSpotService touristSpotService;

    @Operation(summary = "관광지 데이터 가져오기 -> 백엔드 API입니다", description = "지역과 시군구에 따라 관광지 데이터를 가져옵니다.")
    @Parameters({
            @Parameter(name = "region", description = "지역 코드", example = "11"),
            @Parameter(name = "signgu", description = "시군구 코드", example = "112233")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "관광지 데이터 가져오기 작업이 시작되었습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 지역 또는 시군구 입력"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @GetMapping("/fetch/{region}/{signgu}")
    public String fetchTouristData(@PathVariable("region") String region, @PathVariable("signgu") String signgu) {
        touristSpotService.fetchAndSaveTouristData(region, signgu);
        return "관광지 데이터 가져오기가 시작되었습니다.";
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
