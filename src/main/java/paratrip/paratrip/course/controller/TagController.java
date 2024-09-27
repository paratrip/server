package paratrip.paratrip.course.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import paratrip.paratrip.course.entity.TouristSpot;
import paratrip.paratrip.course.service.TagService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Tag(name = "Tag 생성 및 관리 API", description = "데이터베이스 생성할 때 백엔드에서 사용하는 API")
@RequestMapping("/api/tags")
public class TagController {

    private final TagService tagService;

    @Operation(summary = "모든 관광지에 대해 태그 생성", description = "모든 관광지에 대해 1개의 태그만 부여하고 저장합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "모든 관광지에 대해 태그가 생성되고 저장되었습니다."),
            @ApiResponse(responseCode = "500", description = "서버 오류가 발생했습니다.")
    })
    @PostMapping("/generate")
    public ResponseEntity<String> generateTagsForAllSpots() {
        tagService.generateAndSaveSingleTagForAllSpots();
        return ResponseEntity.ok("모든 관광지에 대해 1개의 태그가 생성되고 저장되었습니다.");
    }

    @Operation(summary = "관광지별 태그 조회", description = "특정 관광지의 태그를 조회합니다.")
    @GetMapping("/{touristSpotId}")
    public ResponseEntity<TouristSpot> getTagsForTouristSpot(@PathVariable("touristSpotId") Long touristSpotId) {
        Optional<TouristSpot> touristSpotWithTags = tagService.getTagsForTouristSpotById(touristSpotId);
        return touristSpotWithTags.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "모든 관광지와 태그 조회", description = "모든 관광지와 태그를 조회합니다.")
    @GetMapping
    public ResponseEntity<List<TouristSpot>> getAllTags() {
        List<TouristSpot> touristSpotsWithTags = tagService.getAllTouristSpotsWithTags();
        return ResponseEntity.ok(touristSpotsWithTags);
    }
}
