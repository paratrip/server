package paratrip.paratrip.course.dto;

import java.util.List;

public record TouristSpotResponseDto(
        String category,           // 카테고리
        String regionCode,         // 지역 코드
        String regionName,         // 지역 이름 (추가)

        String rlteTatsNm,         // 관광지 이름
        String basicAddress,       // 기본 주소
        String imageUrl,           // 이미지 URL
        String tags          // 태그 리스트
) {}
