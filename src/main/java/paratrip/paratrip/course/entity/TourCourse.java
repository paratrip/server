package paratrip.paratrip.course.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import paratrip.paratrip.paragliding.entity.Paragliding;
import paratrip.paratrip.paragliding.entity.Region;

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

    @Column(name = "region")
    @Enumerated(EnumType.STRING)  // Region enum을 문자열로 저장
    private Region region;

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
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "course_tags", joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "tag")
    private List<String> tags;  // 관광지 및 패러글라이딩의 태그
}
