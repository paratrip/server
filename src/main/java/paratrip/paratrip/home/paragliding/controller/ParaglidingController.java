package paratrip.paratrip.home.paragliding.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import paratrip.paratrip.home.paragliding.dto.request.ParaglidingRequestDto;
import paratrip.paratrip.home.paragliding.dto.response.ParaglidingResponseDto;
import paratrip.paratrip.home.paragliding.dto.response.RegionResponseDto;
import paratrip.paratrip.home.paragliding.service.ParaglidingService;

import java.util.List;

/**
 * packageName    : paratrip.paratrip.home.paragliding.controller
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
}
