package paratrip.paratrip.course.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import paratrip.paratrip.paragliding.entity.Paragliding;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TourCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paragliding_id")
    private Paragliding paragliding;

    @ManyToOne
    @JoinColumn(name = "tourist_spot1_id")
    private TouristSpot touristSpot1;

    @ManyToOne
    @JoinColumn(name = "tourist_spot2_id")
    private TouristSpot touristSpot2;

    private String region;  // 패러글라이딩의 지역 (주소 형태)

    // 이미지 URL 추가
    private String imageUrlParagliding;
    private String imageUrl1;
    private String imageUrl2;

    // 카테고리 추가
    private String category1;
    private String category2;

    // 관광지 세부 주소 추가
    private String spotAddress1;  // 관광지 1의 세부 주소
    private String spotAddress2;  // 관광지 2의 세부 주소
}
