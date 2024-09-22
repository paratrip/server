package paratrip.paratrip.course.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import paratrip.paratrip.course.dto.CourseDto;
import paratrip.paratrip.course.entity.Course;
import paratrip.paratrip.course.entity.TouristSpot;
import paratrip.paratrip.course.repository.CourseRepository;
import paratrip.paratrip.course.repository.TouristSpotRepository;
import paratrip.paratrip.paragliding.entity.Paragliding;
import paratrip.paratrip.paragliding.repository.ParaglidingRepository;
import paratrip.paratrip.course.util.CourseUtil;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final ParaglidingRepository paraglidingRepository;
    private final TouristSpotRepository touristSpotRepository;

    @Transactional
    public void generateCourses() {
        // 패러글라이딩 장소와 관광지 정보 조회
        List<Paragliding> paraglidingList = paraglidingRepository.findAll();
        List<TouristSpot> touristSpots = touristSpotRepository.findAll();

        // 패러글라이딩마다 관광지와 조합하여 코스 생성
        for (Paragliding paragliding : paraglidingList) {
            String paraglidingAddress = CourseUtil.convertRegionName(paragliding.getAddress()); // 패러글라이딩 주소 변환

            // 같은 지역에 있는 관광지 필터링
            List<TouristSpot> matchingSpots = touristSpots.stream()
                    .filter(spot -> CourseUtil.compareAddress(spot.getBasicAddress(), paraglidingAddress))
                    .toList();

            System.out.println("Matching tourist spots for " + paragliding.getName() + ": " + matchingSpots.size());

            // 관광지 2개와 패러글라이딩을 조합하여 코스 생성 (모든 경우의 수 조합)
            List<List<TouristSpot>> combinations = CourseUtil.generateCombinations(matchingSpots, 2);
            for (List<TouristSpot> combo : combinations) {
                TouristSpot spot1 = combo.get(0);
                TouristSpot spot2 = combo.get(1);

                // 코스 생성 후 저장
                Course course = Course.builder()
                        .paragliding(paragliding)
                        .touristSpot1(spot1)
                        .touristSpot2(spot2)
                        .region(paraglidingAddress)
                        .imageUrlParagliding(paragliding.getImageUrl())
                        .imageUrl1(spot1.getImageUrl())
                        .imageUrl2(spot2.getImageUrl())
                        .category1(spot1.getCategory())
                        .category2(spot2.getCategory())
                        .build();
                courseRepository.save(course);
            }
        }
    }

    @Transactional(readOnly = true)
    public List<CourseDto> getCourses() {
        // 생성된 모든 코스를 DTO로 변환하여 반환
        return courseRepository.findAll().stream()
                .map(course -> new CourseDto(
                        course.getParagliding().getName(),
                        course.getTouristSpot1().getRlteTatsNm(),
                        course.getTouristSpot2().getRlteTatsNm(),
                        course.getRegion(),
                        course.getImageUrlParagliding(),
                        course.getImageUrl1(),
                        course.getImageUrl2(),
                        course.getCategory1(),
                        course.getCategory2()
                ))
                .toList();
    }
}
