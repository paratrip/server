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
import paratrip.paratrip.paragliding.repository.ParaglidingRepository;
import paratrip.paratrip.course.util.CourseUtil;

import java.util.List;
import java.util.stream.Collectors;

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

        for (Paragliding paragliding : paraglidingList) {
            // 패러글라이딩의 실제 주소를 가져옴 (Enum이 아닌)
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
        // 관광지 조합을 생성
        List<List<TouristSpot>> combinations = CourseUtil.generateCombinations(matchingSpots, 2);

        for (List<TouristSpot> combo : combinations) {
            TouristSpot spot1 = combo.get(0);
            TouristSpot spot2 = combo.get(1);

            // TouristSpot 데이터가 null이 아닌지 확인
            if (spot1 != null && spot2 != null && isValidTouristSpot(spot1) && isValidTouristSpot(spot2)) {
                // 관광지의 세부 주소도 함께 저장
                TourCourse course = TourCourse.builder()
                        .paragliding(paragliding)
                        .touristSpot1(spot1)
                        .touristSpot2(spot2)
                        .region(paragliding.getAddress()) // 패러글라이딩의 실제 주소
                        .imageUrlParagliding(paragliding.getImageUrl())
                        .imageUrl1(spot1.getImageUrl())
                        .imageUrl2(spot2.getImageUrl())
                        .category1(spot1.getCategory())
                        .category2(spot2.getCategory())
                        .spotAddress1(spot1.getBasicAddress()) // 관광지 1의 주소
                        .spotAddress2(spot2.getBasicAddress()) // 관광지 2의 주소
                        .build();
                courseRepository.save(course);
            } else {
                System.out.println("유효하지 않은 관광지 데이터로 인해 코스를 생성할 수 없습니다.");
            }
        }
    }

    // TouristSpot 필수 데이터가 유효한지 확인
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
                        course.getSpotAddress1(), // 관광지 1의 세부 주소
                        course.getSpotAddress2()  // 관광지 2의 세부 주소
                ))
                .collect(Collectors.toList());
    }
}
