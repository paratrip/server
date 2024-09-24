package paratrip.paratrip.paragliding.service;

import com.amazonaws.services.s3.model.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import paratrip.paratrip.paragliding.dto.request.ParaglidingRequestDto;
import paratrip.paratrip.paragliding.dto.response.DetailResponseDto;
import paratrip.paratrip.paragliding.dto.response.ParaglidingResponseDto;
import paratrip.paratrip.paragliding.dto.response.RegionResponseDto;
import paratrip.paratrip.paragliding.entity.Paragliding;
import paratrip.paratrip.paragliding.repository.ParaglidingJpaRepository;
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
        List<Paragliding> paraglidingList;
        if (request.region() != null) {
            paraglidingList = paraglidingRepository.findByRegion(request.region());
        } else {
            paraglidingList = paraglidingRepository.findAll();
        }
        paraglidingList.sort((p1, p2) -> Integer.compare(p2.getHeart(), p1.getHeart()));
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
}