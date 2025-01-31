package paratrip.paratrip.course.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import paratrip.paratrip.course.dto.CourseResponseDto;
import paratrip.paratrip.course.entity.TouristSpot;
import paratrip.paratrip.course.entity.TourCourse;
import paratrip.paratrip.course.repository.CourseRepository;
import paratrip.paratrip.course.repository.TouristSpotRepository;
import paratrip.paratrip.course.util.CourseNameUtil;
import paratrip.paratrip.paragliding.entity.Paragliding;
import paratrip.paratrip.paragliding.entity.Region;
import paratrip.paratrip.paragliding.repository.ParaglidingJpaRepository;
import paratrip.paratrip.course.util.CourseUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final ParaglidingJpaRepository paraglidingRepository;
    private final TouristSpotRepository touristSpotRepository;
    private final CourseNameUtil courseNameUtil;

    // 관광지와 패러글라이딩 장소를 매칭하여 코스 생성
    @Transactional
    public void generateCourses() {
        List<Paragliding> paraglidingList = paraglidingRepository.findAll();
        List<TouristSpot> touristSpots = touristSpotRepository.findAll();

        for (Paragliding paragliding : paraglidingList) {
            String paraglidingAddress = CourseUtil.convertParaglidingAddress(paragliding.getAddress());

            List<TouristSpot> matchingSpots = touristSpots.stream()
                    .filter(spot -> CourseUtil.compareByCityOrDistrict(spot.getBasicAddress(), paraglidingAddress))
                    .collect(Collectors.toList());

            if (matchingSpots.isEmpty()) {
                matchingSpots = touristSpots.stream()
                        .filter(spot -> CourseUtil.compareByProvince(spot.getBasicAddress(), paraglidingAddress))
                        .collect(Collectors.toList());
            }

            if (matchingSpots.size() >= 2) {
                generateCourseForParagliding(paragliding, matchingSpots);
            }
        }
    }

    private void generateCourseForParagliding(Paragliding paragliding, List<TouristSpot> matchingSpots) {
        List<List<TouristSpot>> combinations = CourseUtil.generateCombinations(matchingSpots, 2);

        for (List<TouristSpot> combo : combinations) {
            TouristSpot spot1 = combo.get(0);
            TouristSpot spot2 = combo.get(1);

            if (spot1 != null && spot2 != null && isValidTouristSpot(spot1) && isValidTouristSpot(spot2)) {
                String combinedTags = String.join(", ", spot1.getTag(), spot2.getTag());
                String paraglidingName = paragliding.getName(); // 패러글라이딩 이름
                List<String> spotNames = List.of(spot1.getRlteTatsNm(), spot2.getRlteTatsNm()); // 관광지 이름 리스트
                List<String> categories = List.of(spot1.getCategory(), spot2.getCategory());   // 관광지 카테고리 리스트

                String courseName = courseNameUtil.generateCourseName(paraglidingName, spotNames, categories);

                // 코스 생성 및 저장
                TourCourse course = TourCourse.builder()
                        .paragliding(paragliding)
                        .touristSpot1(spot1)
                        .touristSpot2(spot2)
                        .region(paragliding.getRegion())    // 패러글라이딩의 region 값을 Region enum으로 직접 저장
                        .imageUrlParagliding(paragliding.getImageUrl())          // 패러글라이딩 이미지 URL
                        .imageUrl1(spot1.getImageUrl())                          // 관광지 1 이미지 URL
                        .imageUrl2(spot2.getImageUrl())                          // 관광지 2 이미지 URL
                        .category1(spot1.getCategory())                          // 관광지 1 카테고리
                        .category2(spot2.getCategory())                          // 관광지 2 카테고리
                        .spotAddress1(spot1.getBasicAddress())                   // 관광지 1 주소
                        .spotAddress2(spot2.getBasicAddress())                   // 관광지 2 주소
                        .tags(combinedTags)                                      // 병합된 태그 리스트
                        .rlteTatsNm1(spot1.getRlteTatsNm())                      // 관광지 1의 rlteTatsNm 값
                        .rlteTatsNm2(spot2.getRlteTatsNm())                      // 관광지 2의 rlteTatsNm 값
                        .name(courseName)
                        .build();

                courseRepository.save(course);
            }
        }
    }


    private boolean isValidTouristSpot(TouristSpot spot) {
        return spot.getBasicAddress() != null && !spot.getBasicAddress().isEmpty()
                && spot.getCategory() != null && !spot.getCategory().isEmpty()
                && spot.getImageUrl() != null && !spot.getImageUrl().isEmpty();
    }

    // 코스 리스트 조회 메서드 추가
    // 필터링된 코스 리스트 조회 메서드 (여러 region 및 tag 값을 지원)
    @Transactional(readOnly = true)
    public List<CourseResponseDto> getAllCourses(List<Region> regions, List<String> tags) {
        List<TourCourse> courses;

        // 여러 필터링 조건에 따라 코스 조회
        if (regions != null && !regions.isEmpty() && tags != null && !tags.isEmpty()) {
            courses = courseRepository.findByRegionInAndTagsIn(regions, tags);
        } else if (regions != null && !regions.isEmpty()) {
            courses = courseRepository.findByRegionIn(regions);
        } else if (tags != null && !tags.isEmpty()) {
            courses = courseRepository.findByTagsIn(tags);
        } else {
            courses = courseRepository.findAll();
        }

        // 조회된 코스를 DTO로 변환하여 반환
        return courses.stream().map(course -> new CourseResponseDto(
                course.getId(),
                course.getParagliding().getName(),                  // 패러글라이딩 이름
                course.getTouristSpot1().getRlteTatsNm(),            // 관광지 1 이름
                course.getTouristSpot2().getRlteTatsNm(),
                course.getRegion(),                            // 패러글라이딩의 Region 값 (enum 타입)
                course.getTouristSpot1().getTag(),                   // 관광지 1 태그
                course.getTouristSpot2().getTag(),                   // 관광지 2 태그
                course.getImageUrlParagliding(),                     // 패러글라이딩 이미지 URL
                course.getImageUrl1(),                               // 관광지 1 이미지 URL
                course.getImageUrl2(),                               // 관광지 2 이미지 URL
                course.getRlteTatsNm1(),                             // 관광지 1의 rlteTatsNm
                course.getRlteTatsNm2(),
                course.getName()
        )).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CourseResponseDto getCourseDetail(Long courseId) {
        // courseId로 TourCourse 엔티티를 조회하고 없으면 예외 발생
        Optional<TourCourse> courseOptional = courseRepository.findById(courseId);

        if (courseOptional.isEmpty()) {
            throw new IllegalArgumentException("해당 ID의 코스를 찾을 수 없습니다: " + courseId);
        }

        // 조회된 코스를 DTO로 변환하여 반환
        TourCourse course = courseOptional.get();
        return new CourseResponseDto(
                course.getId(),
                course.getParagliding().getName(),            // 패러글라이딩 이름
                course.getTouristSpot1().getRlteTatsNm(),      // 관광지 1 이름
                course.getTouristSpot2().getRlteTatsNm(),      // 관광지 2 이름
                course.getRegion(),                          // 패러글라이딩 지역 (Region enum 타입)
                course.getTouristSpot1().getTag(),             // 관광지 1 태그
                course.getTouristSpot2().getTag(),             // 관광지 2 태그
                course.getImageUrlParagliding(),               // 패러글라이딩 이미지 URL
                course.getImageUrl1(),                         // 관광지 1 이미지 URL
                course.getImageUrl2(),                         // 관광지 2 이미지 URL
                course.getRlteTatsNm1(),                       // 관광지 1의 rlteTatsNm
                course.getRlteTatsNm2()    ,
                course.getName()// 관광지 2의 rlteTatsNm
        );
    }

}
