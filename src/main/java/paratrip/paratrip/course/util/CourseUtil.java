package paratrip.paratrip.course.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseUtil {

    private static final Map<String, String> paraglidingRegionMapping = new HashMap<>() {{
        put("경기도", "경기");
        put("강원특별자치도", "강원");
        put("충청남도", "충남");
        put("충청북도", "충북");
        put("전라남도", "전남");
        put("전북특별자치도", "전북");
        put("경상남도", "경남");
    }};

    public static String convertParaglidingAddress(String address) {
        for (Map.Entry<String, String> entry : paraglidingRegionMapping.entrySet()) {
            if (address.contains(entry.getKey())) {
                return address.replace(entry.getKey(), entry.getValue());
            }
        }
        return address;
    }

    // 시/군 비교 로직
    public static boolean compareByCityOrDistrict(String address1, String address2) {
        return address1.contains(address2) || address2.contains(address1);
    }

    // 도 단위 비교 로직
    public static boolean compareByProvince(String address1, String address2) {
        String province1 = extractProvince(address1);
        String province2 = extractProvince(address2);
        return province1.equals(province2);
    }

    private static String extractProvince(String address) {
        for (String province : paraglidingRegionMapping.values()) {
            if (address.contains(province)) {
                return province;
            }
        }
        return "";
    }

    public static <T> List<List<T>> generateCombinations(List<T> list, int r) {
        // 모든 경우의 수 조합을 생성하는 로직
        List<List<T>> result = new ArrayList<>();
        combine(new Object[r], list.toArray(), r, 0, 0, result);
        return result;
    }

    private static <T> void combine(Object[] temp, Object[] data, int r, int index, int start, List<List<T>> result) {
        if (index == r) {
            result.add(List.of((T[]) temp.clone()));
            return;
        }
        for (int i = start; i < data.length; i++) {
            temp[index] = data[i];
            combine(temp, data, r, index + 1, i + 1, result);
        }
    }
}
