package paratrip.paratrip.paragliding.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import paratrip.paratrip.paragliding.dto.request.LikeRequestDto;
import paratrip.paratrip.paragliding.dto.request.ParaglidingRequestDto;
import paratrip.paratrip.paragliding.dto.response.DetailResponseDto;
import paratrip.paratrip.paragliding.dto.response.ParaglidingResponseDto;
import paratrip.paratrip.paragliding.dto.response.RegionResponseDto;
import paratrip.paratrip.paragliding.service.ParaglidingService;

import java.util.List;

@Tag(name = "Paragliding API", description = "패러글라이딩 서비스 관련 API")
@RestController
@RequestMapping("/api/paragliding")
public class ParaglidingController {

    @Autowired
    private ParaglidingService paraglidingService;

    @Operation(summary = "패러글라이딩 리스트 조회", description = "선택한 지역과 액세스 토큰을 사용하여 패러글라이딩 리스트를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 패러글라이딩 리스트를 조회했습니다.",
                    content = @Content(schema = @Schema(implementation = ParaglidingResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "지역 선택 및 액세스 토큰", required = true,
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                      "region": "DY",
                      "accessToken": "yourAccessToken"
                    }
                    """)))
    @PostMapping("/list")
    public List<ParaglidingResponseDto> getParaglidingList(@RequestBody ParaglidingRequestDto request) {
        return paraglidingService.getParaglidingList(request);
    }

    @Operation(summary = "모든 패러글라이딩 조회", description = "전체 패러글라이딩 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 모든 패러글라이딩 정보를 조회했습니다.",
                    content = @Content(schema = @Schema(implementation = ParaglidingResponseDto.class)))
    })
    @GetMapping("/paragliding/all")
    public ResponseEntity<List<ParaglidingResponseDto>> getAllParagliding() {
        List<ParaglidingResponseDto> paraglidingList = paraglidingService.getAllParagliding();
        return ResponseEntity.ok(paraglidingList);
    }

    @Operation(summary = "모든 지역 조회", description = "모든 패러글라이딩 가능한 지역을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 지역 목록을 조회했습니다.",
                    content = @Content(schema = @Schema(implementation = RegionResponseDto.class)))
    })
    @GetMapping("/region")
    public ResponseEntity<List<RegionResponseDto>> getAllRegions() {
        List<RegionResponseDto> regions = paraglidingService.getAllRegions();
        return new ResponseEntity<>(regions, HttpStatus.OK);
    }

    @Operation(summary = "패러글라이딩 상세 조회", description = "패러글라이딩 장소의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 패러글라이딩 상세 정보를 조회했습니다.",
                    content = @Content(schema = @Schema(implementation = DetailResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "해당 ID의 패러글라이딩 정보를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/detail/{id}")
    public ResponseEntity<DetailResponseDto> getParaglidingDetails(
            @Parameter(description = "패러글라이딩 ID", example = "1") @PathVariable Long id,
            @RequestHeader("Authorization") String token) {
        DetailResponseDto details = paraglidingService.getParaglidingDetails(id);
        return ResponseEntity.ok(details);
    }

    @Operation(summary = "지역별 패러글라이딩 조회", description = "선택한 지역에 해당하는 패러글라이딩 리스트를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 지역별 패러글라이딩 리스트를 조회했습니다.",
                    content = @Content(schema = @Schema(implementation = ParaglidingResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/list/region/{regionCode}")
    public ResponseEntity<List<ParaglidingResponseDto>> getParaglidingByRegion(
            @Parameter(description = "조회할 지역의 코드", example = "DY") @PathVariable String regionCode) {
        List<ParaglidingResponseDto> paraglidingList = paraglidingService.getParaglidingByRegion(regionCode);
        return ResponseEntity.ok(paraglidingList);
    }

    @Operation(summary = "패러글라이딩 좋아요", description = "사용자가 패러글라이딩 장소에 좋아요를 추가합니다.")
    @PostMapping("/like")
    public ResponseEntity<String> likeParagliding(@RequestBody LikeRequestDto request) {
        paraglidingService.addLike(request);
        return ResponseEntity.ok("좋아요 성공");
    }

    @Operation(summary = "좋아요 순으로 패러글라이딩 리스트 조회", description = "좋아요(heart) 순으로 패러글라이딩 리스트를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 좋아요 순으로 패러글라이딩 리스트를 조회했습니다.",
                    content = @Content(schema = @Schema(implementation = ParaglidingResponseDto.class)))
    })
    @GetMapping("/list/sorted-by-likes")
    public ResponseEntity<List<ParaglidingResponseDto>> getParaglidingListSortedByLikes() {
        List<ParaglidingResponseDto> sortedParaglidingList = paraglidingService.getParaglidingListSortedByLikes();
        return ResponseEntity.ok(sortedParaglidingList);
    }
}
