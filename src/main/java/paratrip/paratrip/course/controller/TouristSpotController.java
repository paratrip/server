package paratrip.paratrip.course.controller;

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

    @GetMapping("/fetch")
    public String fetchTouristData(@PathVariable("region") String region, @PathVariable("signgu") String signgu) {
        touristSpotService.fetchAndSaveTouristData(region, signgu);
        return "Tourist spots data fetching initiated.";
    }
}