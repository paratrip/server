package paratrip.paratrip.course.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import paratrip.paratrip.paragliding.entity.Paragliding;
import paratrip.paratrip.paragliding.entity.Region;

import java.util.ArrayList;
import java.util.List;

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

    // 이미지 URL 추가
    private String imageUrlParagliding;
    private String imageUrl1;
    private String imageUrl2;

    // 카테고리 추가
    private String category1;
    private String category2;

    // 관광지 세부 주소 추가
    private String spotAddress1;
    private String spotAddress2;

    // 태그 추가
    private String tags;

    // 관광지의 rlteTatsNm 값 추가
    private String rlteTatsNm1;  // touristSpot1의 rlteTatsNm 값
    private String rlteTatsNm2;  // touristSpot2의 rlteTatsNm 값

    @Column(name = "region")
    @Enumerated(EnumType.STRING)  // enum 타입으로 저장
    private Region region;

    // 코스 이름 필드 추가
    @Column(length = 500,columnDefinition = "LONGTEXT")  // 기존 VARCHAR(255)에서 VARCHAR(500)으로 확장
    private String name;  // 코스 이름 필드 추가
}
