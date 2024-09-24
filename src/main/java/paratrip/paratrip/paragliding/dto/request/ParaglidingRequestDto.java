package paratrip.paratrip.paragliding.dto.request;

import paratrip.paratrip.paragliding.entity.Region;

import java.util.List;

/**
 * packageName    : paratrip.paratrip.paragliding.dto.request
 * fileName       : ParaglidingListDto
 * author         : tlswl
 * date           : 2024-09-09
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-09-09        tlswl       최초 생성
 */
public record ParaglidingRequestDto(List<Region> regions, String accessToken) {
}
