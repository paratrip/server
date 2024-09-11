package paratrip.paratrip.paragliding.dto.response;

import java.util.List;

/**
 * packageName    : paratrip.paratrip.paragliding.dto.response
 * fileName       : ParaglidingDetailResponseDTO
 * author         : tlswl
 * date           : 2024-09-09
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-09-09        tlswl       최초 생성
 */
public record DetailResponseDto(
        String name,
        String description,
        String imageUrl,
        List<String> tickets,
        String cost,
        String pageUrl,
        boolean parkingLot,
        boolean stroller,
        boolean creditCard,
        String closedDays,
        String openingHour,
        String tellNumber,
        double latitude,
        double longitude,
        String address
) {}