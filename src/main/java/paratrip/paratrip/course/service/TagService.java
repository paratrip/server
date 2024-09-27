package paratrip.paratrip.course.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import paratrip.paratrip.course.entity.TouristSpot;
import paratrip.paratrip.course.repository.TouristSpotRepository;
import paratrip.paratrip.course.util.TagUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TouristSpotRepository touristSpotRepository;
    private final TagUtils tagUtils;

    // 모든 관광지에 대해 1개의 태그를 생성하고 저장하는 메서드
    @Transactional
    public void generateAndSaveSingleTagForAllSpots() {
        List<TouristSpot> allTouristSpots = touristSpotRepository.findAll();

        if (allTouristSpots.isEmpty()) {
            System.out.println("관광지가 없습니다. 태그를 생성할 수 없습니다.");
            return;
        }

        for (TouristSpot spot : allTouristSpots) {
            String generatedTag = tagUtils.generateTagForTouristSpot(spot);  // 단일 태그 생성 메서드 호출

            if (generatedTag != null && !generatedTag.isEmpty()) {
                spot.setTag(generatedTag);  // 단일 태그를 관광지에 저장
                touristSpotRepository.save(spot);
            }
        }

        System.out.println("모든 관광지에 대해 하나의 태그가 생성되고 저장되었습니다.");
    }

    public List<TouristSpot> getAllTouristSpotsWithTags() {
        return touristSpotRepository.findAll(); // 또는 태그가 있는 관광지 필터링 로직을 추가
    }

    public Optional<TouristSpot> getTagsForTouristSpotById(Long touristSpotId) {
        return touristSpotRepository.findById(touristSpotId);
    }
}
