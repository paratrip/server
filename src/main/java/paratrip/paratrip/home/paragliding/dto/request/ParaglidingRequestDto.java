package paratrip.paratrip.home.paragliding.dto.request;

import paratrip.paratrip.home.paragliding.entity.Region;

/**
 * packageName    : paratrip.paratrip.home.paragliding.dto.request
 * fileName       : ParaglidingListDto
 * author         : tlswl
 * date           : 2024-09-09
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-09-09        tlswl       최초 생성
 */
public record ParaglidingRequestDto(Region region, String accessToken) {
}
