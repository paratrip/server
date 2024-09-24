package paratrip.paratrip.course.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import paratrip.paratrip.course.service.TouristSpotService;

@RestController
@RequestMapping("/api/tourist-spots")
@RequiredArgsConstructor
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
    @GetMapping("/fetch/{region}/{signgu}")
    public String fetchTouristData(@PathVariable("region") String region, @PathVariable("signgu") String signgu) {
        touristSpotService.fetchAndSaveTouristData(region, signgu);
        return "관광지 데이터 가져오기가 시작되었습니다.";
    }
}
