package paratrip.paratrip.paragliding.dto.response;

/**
 * packageName    : paratrip.paratrip.paragliding.dto.response
 * fileName       : ParaglidingListResponseDto
 * author         : tlswl
 * date           : 2024-09-09
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-09-09        tlswl       최초 생성
 */
public record ParaglidingResponseDto(
        Long id,
        String name,
        int heart,
        String region,
        Double cost,
        String imageUrl
) { }

