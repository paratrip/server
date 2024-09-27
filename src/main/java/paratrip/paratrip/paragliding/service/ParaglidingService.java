package paratrip.paratrip.paragliding.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import paratrip.paratrip.paragliding.dto.request.LikeRequestDto;
import paratrip.paratrip.paragliding.dto.request.ParaglidingRequestDto;
import paratrip.paratrip.paragliding.dto.response.DetailResponseDto;
import paratrip.paratrip.paragliding.dto.response.ParaglidingResponseDto;
import paratrip.paratrip.paragliding.dto.response.RegionResponseDto;
import paratrip.paratrip.paragliding.entity.Paragliding;
import paratrip.paratrip.paragliding.entity.Region;
import paratrip.paratrip.paragliding.repository.ParaglidingJpaRepository;
import paratrip.paratrip.paragliding.repository.ParaglidingRepository;
import paratrip.paratrip.paragliding.util.ParaglidingUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * packageName    : paratrip.paratrip.paragliding.service
 * fileName       : ParaglidingService
 * author         : tlswl
 * date           : 2024-09-09
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-09-09        tlswl       최초 생성
 */
@Service
public class ParaglidingService {

    @Autowired
    private ParaglidingJpaRepository paraglidingRepository;

    @Autowired
    private ParaglidingUtils paraglidingUtils;

    public List<ParaglidingResponseDto> getParaglidingList(ParaglidingRequestDto request) {
        List<Region> regions = request.regions();
        List<Paragliding> paraglidingList;

        if (regions != null && !regions.isEmpty()) {
            // 여러 개의 지역을 조건으로 조회 (여기서 findByRegionIn은 여러 지역을 조회하는 메서드여야 함)
            paraglidingList = paraglidingRepository.findByRegionIn(regions);
        } else {
            // 지역 선택이 없으면 모든 패러글라이딩 정보를 조회
            paraglidingList = paraglidingRepository.findAll();
        }

        // 좋아요(heart) 수로 내림차순 정렬
        paraglidingList.sort((p1, p2) -> Integer.compare(p2.getHeart(), p1.getHeart()));

        // DTO로 변환 후 반환
        return paraglidingUtils.toResponseDtoList(paraglidingList);
    }

    public List<ParaglidingResponseDto> getAllParagliding() {
        List<Paragliding> paraglidingList = paraglidingRepository.findAll();
        return paraglidingUtils.toResponseDtoList(paraglidingList);
    }

    public List<RegionResponseDto> getAllRegions(){
        return Stream.of(Region.values())
                .map(region -> new RegionResponseDto(region.name()))
                .collect(Collectors.toList());
    }

    public DetailResponseDto getParaglidingDetails(Long id) {
        Paragliding paragliding = paraglidingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("정보가 없습니다"));

        return paraglidingUtils.convertToDetailResponseDto(paragliding);
    }

    // 지역별 패러글라이딩 조회 메서드 수정
    public List<ParaglidingResponseDto> getParaglidingByRegion(String regionCode) {
        List<Paragliding> paraglidingList;

        if (regionCode == null || regionCode.trim().isEmpty()) {
            // 지역 코드가 주어지지 않았을 때, 전체 패러글라이딩 조회
            paraglidingList = paraglidingRepository.findAll();
        } else {
            // 지역 코드에 해당하는 패러글라이딩 정보 조회
            paraglidingList = paraglidingRepository.findByRegion(Region.valueOf(regionCode));
        }

        // 좋아요(heart) 수로 내림차순 정렬
        paraglidingList.sort((p1, p2) -> Integer.compare(p2.getHeart(), p1.getHeart()));

        // DTO로 변환 후 반환
        return paraglidingUtils.toResponseDtoList(paraglidingList);
    }

    // 좋아요 순으로 정렬된 패러글라이딩 리스트 조회
    public List<ParaglidingResponseDto> getParaglidingListSortedByLikes() {
        List<Paragliding> paraglidingList = paraglidingRepository.findAll();
        paraglidingList.sort((p1, p2) -> Integer.compare(p2.getHeart(), p1.getHeart()));
        return paraglidingUtils.toResponseDtoList(paraglidingList);
    }

    // 좋아요 추가 메서드
    public void addLike(LikeRequestDto request) {
        Long paraglidingId = request.paraglidingId();
        Long userId = request.userId();

        Paragliding paragliding = paraglidingRepository.findById(paraglidingId)
                .orElseThrow(() -> new RuntimeException("Paragliding spot not found"));

        if (paragliding.getLikedUsers().contains(userId)) {
            throw new RuntimeException("User already liked this spot");
        }

        paragliding.setHeart(paragliding.getHeart() + 1);
        paragliding.getLikedUsers().add(userId);
        paraglidingRepository.save(paragliding);
    }
}