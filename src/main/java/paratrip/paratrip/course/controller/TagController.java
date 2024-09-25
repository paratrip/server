package paratrip.paratrip.course.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import paratrip.paratrip.course.entity.TouristSpot;
import paratrip.paratrip.course.service.TagService;

import java.util.List;
import java.util.Optional;

/**
 * packageName    : paratrip.paratrip.course.controller
 * fileName       : TagController
 * author         : tlswl
 * date           : 2024-09-22
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-09-22        tlswl       최초 생성
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "Tag  관련 API", description = "gpt 오류가 많아 생성을 눌러야 태그가 만들어지게 임의로 해놨습니다")
public class TagController {

    private final TagService tagService;

    // 모든 관광지에 대해 태그를 생성하는 API
    @PostMapping("/api/tags/generate")
    public ResponseEntity<String> generateTagsForAllSpots() {
        tagService.generateAndSaveTagsForAllSpots();
        return ResponseEntity.ok("Tags generated and saved for all tourist spots.");
    }

    @GetMapping("/api/tags")
    public ResponseEntity<List<TouristSpot>> getAllTags() {
        List<TouristSpot> touristSpotsWithTags = tagService.getAllTouristSpotsWithTags();
        return ResponseEntity.ok(touristSpotsWithTags);
    }

    // 특정 관광지의 태그를 tourist_spot_id로 조회하는 API
    @GetMapping("/api/tags/{touristSpotId}")
    public ResponseEntity<TouristSpot> getTagsForTouristSpot(@PathVariable("touristSpotId") Long touristSpotId) {
        Optional<TouristSpot> touristSpotWithTags = tagService.getTagsForTouristSpotById(touristSpotId);

        // 관광지가 존재하지 않으면 404 응답
        if (touristSpotWithTags.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(touristSpotWithTags.get());
    }
}
