package paratrip.paratrip.course.util;

import org.springframework.stereotype.Component;

@Component
public class ApiUrlBuilderUtil {

    public String buildApiUrl(String areaCd, String signguCd) {
        return "http://apis.data.go.kr/B551011/TarRlteTarService/areaBasedList?"
                + "serviceKey=mEdC8VRqGDHydjEpUvj3Qf%2BbrU0clNSMRJI2Fqce8aUOUZ6N1JXxQTLj9yMP6KxtM7EH1nL2IBb%2BMPiFYpwJWQ%3D%3D"
                + "&numOfRows=20"
                + "&pageNo=1"
                + "&MobileOS=ETC"
                + "&MobileApp=AppTest"
                + "&baseYm=202408"
                + "&areaCd=" + areaCd // 지역 코드
                + "&signguCd=" + signguCd // 시군구 코드
                + "&_type=json";
    }
}