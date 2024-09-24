package paratrip.paratrip.course.entity;

public enum SignguCode {
    YI("41461"), // 용인
    PC("51760"), // 평창
    BR("44180"), // 보령
    DY("43800"), // 단양
    GOKS("46720"), // 곡성
    YY("51750"), // 영월
    GUNS("52130"), // 군산
    HC("48890"), // 합천
    YS("46130"); // 여수

    private final String code;

    SignguCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}