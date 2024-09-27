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

    private String tag;  // 태그를 하나만 저장하기 위한 필드 추가

}
