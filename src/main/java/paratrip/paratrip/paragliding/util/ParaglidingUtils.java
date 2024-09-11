package paratrip.paratrip.paragliding.util;

import org.springframework.stereotype.Component;
import paratrip.paratrip.paragliding.dto.response.DetailResponseDto;
import paratrip.paratrip.paragliding.dto.response.ParaglidingResponseDto;
import paratrip.paratrip.paragliding.entity.Paragliding;

import java.util.List;
import java.util.stream.Collectors;

/**
 * packageName    : paratrip.paratrip.paragliding.util
 * fileName       : ParaglidingUtils
 * author         : tlswl
 * date           : 2024-09-09
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-09-09        tlswl       최초 생성
 */
@Component
public class ParaglidingUtils {

    public List<ParaglidingResponseDto> toResponseDtoList(List<Paragliding> paraglidingList){
        return paraglidingList.stream()
                .map(paragliding -> new ParaglidingResponseDto(
                        paragliding.getParaglidingSeq(),
                        paragliding.getName(),
                        paragliding.getHeart(),
                        paragliding.getRegion().name(),
                        paragliding.getCost(),
                        paragliding.getImageUrl()
                ))
                .collect(Collectors.toList());
    }

    public DetailResponseDto convertToDetailResponseDto(Paragliding paragliding) {
        return new DetailResponseDto(
                paragliding.getName(),
                paragliding.getDescription(),
                paragliding.getImageUrl(),
                paragliding.getTickets(),
                String.valueOf(paragliding.getCost()),
                paragliding.getPageUrl(),
                paragliding.isParkingLot(),
                paragliding.isStroller(),
                paragliding.isCreditCard(),
                paragliding.getClosedDays(),
                paragliding.getOpeningHour(),
                paragliding.getTellNumber(),
                paragliding.getLatitude(),
                paragliding.getLongitude(),
                paragliding.getAddress()
        );
    }
}
