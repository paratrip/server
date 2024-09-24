package paratrip.paratrip.course.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TouristSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String basicAddress;
    private String category;
    private String regionCode;
    private String rlteTatsNm;
    private String rlteCtgrySclsNm;
    private String imageUrl;

    // 태그 추가
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "tourist_spot_tags", joinColumns = @JoinColumn(name = "tourist_spot_id"))
    @Column(name = "tag")
    private List<String> tags;  // 관광지의 태그
}
