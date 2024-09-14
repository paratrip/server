package paratrip.paratrip.paragliding.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import paratrip.paratrip.paragliding.dto.request.ParaglidingRequestDto;
import paratrip.paratrip.paragliding.dto.response.DetailResponseDto;
import paratrip.paratrip.paragliding.dto.response.ParaglidingResponseDto;
import paratrip.paratrip.paragliding.dto.response.RegionResponseDto;
import paratrip.paratrip.paragliding.service.ParaglidingService;

import java.util.List;

/**
 * packageName    : paratrip.paratrip.paragliding.controller
 * fileName       : ParaglidingController
 * author         : tlswl
 * date           : 2024-09-09
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-09-09        tlswl       최초 생성
 */
@RestController
@RequestMapping("/api/paragliding")
public class ParaglidingController {

    @Autowired
    private ParaglidingService paraglidingService;

    @PostMapping("/list")
    public List<ParaglidingResponseDto> ggetParaglidingList(@RequestBody ParaglidingRequestDto request) {
        return paraglidingService.getParaglidingList(request);
    }

    @GetMapping("/paragliding/all")
    public ResponseEntity<List<ParaglidingResponseDto>> getAllParagliding() {
        List<ParaglidingResponseDto> paraglidingList = paraglidingService.getAllParagliding();
        return ResponseEntity.ok(paraglidingList);
    }

    @GetMapping("/region")
    public ResponseEntity<List<RegionResponseDto>> getAllRegions() {
        List<RegionResponseDto> regions = paraglidingService.getAllRegions();
        return new ResponseEntity<>(regions, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<DetailResponseDto> getParaglidingDetails(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        DetailResponseDto details = paraglidingService.getParaglidingDetails(id);
        return ResponseEntity.ok(details);
    }
}