package paratrip.paratrip.course.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TouristSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // private String touristSpotName; // <tAtsNm>
    private String basicAddress;    // <rlteBsicAdres>
    private String category;        // <rlteCtgryMclsNm>
    private String regionCode;      // 해당 지역 코드
    private String rlteTatsNm;      // rlteTatsNm
}