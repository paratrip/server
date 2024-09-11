package paratrip.paratrip.paragliding.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import paratrip.paratrip.paragliding.entity.Paragliding;
import paratrip.paratrip.paragliding.entity.Region;
import paratrip.paratrip.paragliding.repository.ParaglidingRepository;
import paratrip.paratrip.paragliding.util.ExcelUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * packageName    : paratrip.paratrip.paragliding.service
 * fileName       : ExcelService
 * author         : tlswl
 * date           : 2024-09-09
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-09-09        tlswl       최초 생성
 */
@Service
public class ExcelService {
    @Autowired
    private ParaglidingRepository paraglidingRepository;

    @PostConstruct
    public void init() {
        if (isDataAlreadyPresent()) {
            System.out.println("데이터가 이미 존재합니다. 삽입을 중단합니다.");
            return;  // 데이터가 존재하면 삽입 중단
        }

        try {
            // resources 폴더의 파일 불러오기
            String filePath = "paratrip.xls"; // 파일 이름
            InputStream fileInputStream = getClass().getClassLoader().getResourceAsStream(filePath);

            if (fileInputStream != null) {
                saveParaglidingDataFromExcel(fileInputStream, filePath);
            } else {
                System.out.println("엑셀 파일을 찾을 수 없습니다.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void saveParaglidingDataFromExcel(InputStream fileInputStream, String filePath) throws IOException {
        List<List<Object>> excelData = ExcelUtils.readExcelFromStream(fileInputStream, filePath);
        List<Paragliding> paraglidingList = new ArrayList<>();

        for (List<Object> row : excelData) {
            String name = (String) row.get(0);
            String tellNumber = (String) row.get(1);
            String address = (String) row.get(2);
            long latitude = convertToLong(row.get(3));
            long longitude = convertToLong(row.get(4));
            String description = (String) row.get(5);
            String closedDays = (String) row.get(6);
            String pageUrl = (String) row.get(7);
            String openingHour = (String) row.get(8);
            Double cost = (Double) row.get(9);
            boolean parkingLot = convertToBoolean(row.get(10));
            boolean stroller = convertToBoolean(row.get(11));
            boolean creditCard = convertToBoolean(row.get(12));
            // 원을 기준으로 나누고 불필요한 공백이나 줄바꿈을 제거
            List<String> tickets = Arrays.stream(((String) row.get(13)).split("원\\s*"))
                    .map(String::trim) // 공백과 줄바꿈 제거
                    .filter(ticket -> !ticket.isEmpty()) // 빈 문자열 제외
                    .map(ticket -> ticket + "원") // 다시 '원'을 붙임
                    .collect(Collectors.toList());
            String regionStr=(String)row.get(14);
            Region region=Region.valueOf(regionStr.toUpperCase());
            String imageUrl=(String)row.get(15);

            // 엑셀 데이터에 없는 heart 필드 추가 처리
            int heart = 0; // 기본값으로 설정

            // Paragliding 엔티티 생성
            Paragliding paragliding = Paragliding.builder()
                    .name(name)
                    .tellNumber(tellNumber)
                    .address(address)
                    .pageUrl(pageUrl)
                    .openingHour(openingHour)
                    .cost(cost)
                    .closedDays(closedDays)
                    .parkingLot(parkingLot)
                    .stroller(stroller)
                    .creditCard(creditCard)
                    .description(description)
                    .heart(heart) // heart 필드는 기본값
                    .latitude(latitude)
                    .longitude(longitude)
                    .tickets(tickets)
                    .region(region) // Region 값 설정
                    .imageUrl(imageUrl)
                    .build();

            paraglidingList.add(paragliding);
        }

        // DB에 저장
        paraglidingRepository.saveAll(paraglidingList);
    }
    private long convertToLong(Object cellValue) {
        if (cellValue == null) {
            return 0L; // 빈 값이면 0
        }
        if (cellValue instanceof Double) {
            return ((Double) cellValue).longValue();
        } else if (cellValue instanceof String) {
            try {
                return Long.parseLong((String) cellValue);
            } catch (NumberFormatException e) {
                System.out.println("잘못된 숫자 형식: " + cellValue);
                return 0L; // 잘못된 숫자 형식인 경우 0
            }
        } else {
            return 0L; // 기본값 0
        }
    }

    // Boolean 변환 메서드: "true", "false" 또는 1/0 값에 따라 변환
    private boolean convertToBoolean(Object cellValue) {
        if (cellValue == null) {
            return false; // 기본값 false
        }
        if (cellValue instanceof Boolean) {
            return (Boolean) cellValue;
        } else if (cellValue instanceof String) {
            String value = ((String) cellValue).trim(); // 공백 제거 후 처리
            if (value.equals("가능")) {
                return true; // 가능은 true로 변환
            } else if (value.equals("불가능")) {
                return false; // 불가능은 false로 변환
            } else {
                return Boolean.parseBoolean(value); // 다른 문자열은 기본 Boolean 처리
            }
        } else if (cellValue instanceof Double) {
            return ((Double) cellValue) == 1.0; // 1.0이면 true, 아니면 false
        } else {
            return false; // 기본값 false
        }
    }

    private boolean isDataAlreadyPresent() {
        return paraglidingRepository.count() > 0; // 데이터가 존재하는지 확인
    }
}
