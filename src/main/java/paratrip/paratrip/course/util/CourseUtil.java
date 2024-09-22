package paratrip.paratrip.course.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseUtil {

    // Paragliding 주소를 짧은 형태로 변환하는 매핑
    private static final Map<String, String> paraglidingRegionMapping = new HashMap<>() {{
        put("경기도", "경기");
        put("강원특별자치도", "강원");
        put("충청남도", "충남");
        put("충청북도", "충북");
        put("전라남도", "전남");
        put("전북특별자치도", "전북");
        put("경상남도", "경남");
    }};

    // Paragliding 주소를 변환하는 메서드
    public static String convertRegionName(String address) {
        for (Map.Entry<String, String> entry : paraglidingRegionMapping.entrySet()) {
            if (address.contains(entry.getKey())) {
                return address.replace(entry.getKey(), entry.getValue());
            }
        }
        return address; // 변환되지 않으면 원래 주소 반환
    }

    // 주소 비교 로직
    public static boolean compareAddress(String address1, String address2) {
        return address1.contains(address2) || address2.contains(address1);
    }

    // 모든 경우의 수를 계산하는 유틸리티 메서드
    public static <T> List<List<T>> generateCombinations(List<T> list, int r) {
        List<List<T>> result = new ArrayList<>();
        combine(new Object[r], list.toArray(), r, 0, 0, result);
        return result;
    }

    private static <T> void combine(Object[] temp, Object[] data, int r, int index, int start, List<List<T>> result) {
        if (index == r) {
            List<T> combination = new ArrayList<>();
            for (Object obj : temp) {
                combination.add((T) obj);
            }
            result.add(combination);
            return;
        }
        for (int i = start; i < data.length; i++) {
            temp[index] = data[i];
            combine(temp, data, r, index + 1, i + 1, result);
        }
    }
}
