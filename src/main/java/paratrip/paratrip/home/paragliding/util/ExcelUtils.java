package paratrip.paratrip.home.paragliding.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * packageName    : paratrip.paratrip.home.paragliding.util
 * fileName       : ExcelUtils
 * author         : tlswl
 * date           : 2024-09-09
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-09-09        tlswl       최초 생성
 */
public class ExcelUtils {

    public static Workbook getWorkbook(InputStream inputStream, String filePath) throws IOException {
        if (filePath.endsWith(".xlsx")) {
            return new XSSFWorkbook(inputStream); // .xlsx 파일 처리
        } else if (filePath.endsWith(".xls")) {
            return new HSSFWorkbook(inputStream); // .xls 파일 처리
        } else {
            throw new IllegalArgumentException("Invalid file format. The file must be an Excel file.");
        }
    }

    public static List<List<Object>> readExcelFromStream(InputStream fileInputStream, String filePath) throws IOException {
        Workbook workbook = getWorkbook(fileInputStream, filePath);
        Sheet sheet = workbook.getSheetAt(2);

        List<List<Object>> rowDataList=new ArrayList<>();

        for(int i=0;i<=sheet.getLastRowNum();i++){
            Row row= sheet.getRow(i);
            List<Object> rowData = new ArrayList<>();

            for(int j=0;j<row.getLastCellNum();j++){
                Cell cell=row.getCell(j);

                if(cell!=null){
                    switch (cell.getCellType()){
                        case STRING:
                            rowData.add(cell.getStringCellValue());
                            break;
                        case BOOLEAN:
                            rowData.add(cell.getBooleanCellValue());
                            break;
                        case NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell)) {
                                rowData.add(cell.getDateCellValue());
                            } else {
                                rowData.add(cell.getNumericCellValue());
                            }
                            break;
                        default:
                            rowData.add(null); // 기본값 처리
                    }
                }
            }
            rowDataList.add(rowData);
        }
        workbook.close();
        fileInputStream.close();

        // 콘솔에 읽어온 데이터를 출력
        for (List<Object> row : rowDataList) {
            System.out.println(row); // 각 행의 데이터를 출력
        }

        return rowDataList;
    }
}
