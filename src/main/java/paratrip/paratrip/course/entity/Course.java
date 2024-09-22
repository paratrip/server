package paratrip.paratrip.course.entity;

import jakarta.persistence.*;
import lombok.*;
import paratrip.paratrip.paragliding.entity.Paragliding;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paragliding_id")
    private Paragliding paragliding;

    @ManyToOne
    @JoinColumn(name = "tourist_spot_1_id")
    private TouristSpot touristSpot1;

    @ManyToOne
    @JoinColumn(name = "tourist_spot_2_id")
    private TouristSpot touristSpot2;

    @Column(name = "region")
    private String region;  // 코스가 속한 지역

    private String imageUrl1;  // 관광지 1 이미지 URL
    private String imageUrl2;  // 관광지 2 이미지 URL
    private String imageUrlParagliding;  // 패러글라이딩 이미지 URL

    private String category1;  // 관광지 1 카테고리
    private String category2;  // 관광지 2 카테고리
}
