package paratrip.paratrip.course.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import paratrip.paratrip.course.dto.CourseDto;
import paratrip.paratrip.course.entity.TourCourse;
import paratrip.paratrip.course.entity.TouristSpot;
import paratrip.paratrip.course.repository.CourseRepository;
import paratrip.paratrip.course.repository.TouristSpotRepository;
import paratrip.paratrip.paragliding.entity.Paragliding;
import paratrip.paratrip.paragliding.repository.ParaglidingJpaRepository;
import paratrip.paratrip.paragliding.repository.ParaglidingRepository;
import paratrip.paratrip.course.util.CourseUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final ParaglidingJpaRepository paraglidingRepository;
    private final TouristSpotRepository touristSpotRepository;

    @Transactional
    public void generateCourses() {
        // 패러글라이딩 장소와 관광지 정보 조회
        List<Paragliding> paraglidingList = paraglidingRepository.findAll();
        List<TouristSpot> touristSpots = touristSpotRepository.findAll();

        for (Paragliding paragliding : paraglidingList) {
            String paraglidingAddress = CourseUtil.convertParaglidingAddress(paragliding.getAddress());

            // 시/군 단위로 관광지 찾기
            List<TouristSpot> matchingSpots = touristSpots.stream()
                    .filter(spot -> CourseUtil.compareByCityOrDistrict(spot.getBasicAddress(), paraglidingAddress))
                    .collect(Collectors.toList());

            // 만일 시/군 단위에서 매칭되지 않을 경우, 도 단위로 비교
            if (matchingSpots.isEmpty()) {
                matchingSpots = touristSpots.stream()
                        .filter(spot -> CourseUtil.compareByProvince(spot.getBasicAddress(), paraglidingAddress))
                        .collect(Collectors.toList());
            }

            // 매칭된 관광지가 2개 이상일 때만 코스 생성
            if (matchingSpots.size() >= 2) {
                generateCourseForParagliding(paragliding, matchingSpots);
            } else {
                System.out.println("매칭되는 관광지가 부족하여 코스를 생성할 수 없습니다: " + paragliding.getName());
            }
        }
    }

    private void generateCourseForParagliding(Paragliding paragliding, List<TouristSpot> matchingSpots) {
        List<List<TouristSpot>> combinations = CourseUtil.generateCombinations(matchingSpots, 2);

        for (List<TouristSpot> combo : combinations) {
            TouristSpot spot1 = combo.get(0);
            TouristSpot spot2 = combo.get(1);

            if (spot1 != null && spot2 != null && isValidTouristSpot(spot1) && isValidTouristSpot(spot2)) {
                // 관광지 태그들을 합쳐서 코스에 저장
                List<String> combinedTags = new ArrayList<>(spot1.getTags());
                combinedTags.addAll(spot2.getTags());

                // 중복 제거 및 최대 12개 제한
                combinedTags = combinedTags.stream().distinct().limit(12).collect(Collectors.toList());

                TourCourse course = TourCourse.builder()
                        .paragliding(paragliding)
                        .touristSpot1(spot1)
                        .touristSpot2(spot2)
                        .region(paragliding.getAddress())
                        .imageUrlParagliding(paragliding.getImageUrl())
                        .imageUrl1(spot1.getImageUrl())
                        .imageUrl2(spot2.getImageUrl())
                        .category1(spot1.getCategory())
                        .category2(spot2.getCategory())
                        .spotAddress1(spot1.getBasicAddress())
                        .spotAddress2(spot2.getBasicAddress())
                        .tags(combinedTags)  // 태그 추가
                        .build();
                courseRepository.save(course);
            } else {
                System.out.println("유효하지 않은 관광지 데이터로 인해 코스를 생성할 수 없습니다.");
            }
        }
    }

    private boolean isValidTouristSpot(TouristSpot spot) {
        return spot.getBasicAddress() != null && !spot.getBasicAddress().isEmpty()
                && spot.getCategory() != null && !spot.getCategory().isEmpty()
                && spot.getImageUrl() != null && !spot.getImageUrl().isEmpty();
    }

    @Transactional(readOnly = true)
    public List<CourseDto> getCourses() {
        return courseRepository.findAll().stream()
                .map(course -> new CourseDto(
                        course.getParagliding().getName(),
                        course.getTouristSpot1() != null ? course.getTouristSpot1().getRlteTatsNm() : "N/A",
                        course.getTouristSpot2() != null ? course.getTouristSpot2().getRlteTatsNm() : "N/A",
                        course.getRegion(),
                        course.getImageUrlParagliding(),
                        course.getImageUrl1(),
                        course.getImageUrl2(),
                        course.getCategory1(),
                        course.getCategory2(),
                        course.getSpotAddress1(),
                        course.getSpotAddress2(),
                        course.getTags()  // 태그 추가
                ))
                .collect(Collectors.toList());
    }
}
