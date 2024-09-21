package paratrip.paratrip.course.entity;

public enum RegionCode {
    YI("41"), // 용인 (경기도)
    PC("51"), // 평창 (강원도)
    BR("44"), // 보령 (충청남도)
    DY("43"), // 단양 (충청북도)
    GOKS("46"), // 곡성 (전라남도)
    YY("51"), // 영월 (강원도)
    GUNS("52"), // 군산 (전라북도)
    HC("48"), // 합천 (경상남도)
    YS("46"); // 여수 (전라남도)

    private final String code;

    RegionCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}