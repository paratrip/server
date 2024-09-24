package paratrip.paratrip.course.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import paratrip.paratrip.course.entity.TouristSpot;
import paratrip.paratrip.course.repository.TouristSpotRepository;
import paratrip.paratrip.course.util.TagUtils;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TouristSpotRepository touristSpotRepository;
    private final TagUtils tagUtils;

    // 모든 관광지에 대해 태그를 생성하고 저장하는 메서드
    @Transactional
    public void generateAndSaveTagsForAllSpots() {
        List<TouristSpot> allTouristSpots = touristSpotRepository.findAll();

        if (allTouristSpots.isEmpty()) {
            System.out.println("No tourist spots found.");
            return;
        }

        for (TouristSpot spot : allTouristSpots) {
            List<String> generatedTags = tagUtils.generateTagsForTouristSpot(spot);
            spot.setTags(generatedTags);
            touristSpotRepository.save(spot);
        }

        System.out.println("Tags generated and saved for all tourist spots.");
    }
}
