package priv.wjh.study.poi;

import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Consumer;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
public class TestPoiRead {
    private static final Logger logger = LoggerFactory.getLogger(TestPoiRead.class);

    public static void main(String[] args) throws Exception {

        String[] keys = new String[]{"id"};

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Consumer<Row> convertConsumer = (row) -> {
            int colLen = row.getLastCellNum() > keys.length ? keys.length : row.getLastCellNum();
            Map<String, Object> map = new HashMap<>();
            for (int cellNum = 0; cellNum < colLen; cellNum++) {
                Cell cell = row.getCell(cellNum);
                if (cell == null) {
                    continue;
                }
                CellType valType = cell.getCellType();
                switch (valType) {
                    case STRING -> map.put(keys[cellNum], cell.getStringCellValue());
                    case BOOLEAN -> map.put(keys[cellNum], cell.getBooleanCellValue());
                    case NUMERIC -> {
                        if (DateUtil.isCellDateFormatted(cell)) {
                            // 用于转化为日期格式
                            Date d = cell.getDateCellValue();
                            DateFormat forMater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            map.put(keys[cellNum], forMater.format(d));
                        } else {
                            map.put(keys[cellNum], cell.getNumericCellValue());

                        }
                    }
                }
            }
            if (!map.isEmpty()) {
                list.add(map);
            }

        };
        ClassPathResource classPathResource = new ClassPathResource("指定用户导入数据量大.xlsx");
        long convertTime = System.currentTimeMillis();
        importExcel(classPathResource.getInputStream(), keys, "指定用户导入数据量大.xlsx", convertConsumer);
        logger.warn("转换解析耗时: [{}]", System.currentTimeMillis() - convertTime);

        long nothingTime = System.currentTimeMillis();
        importExcel(classPathResource.getInputStream(), keys, "指定用户导入数据量大.xlsx", (row) -> {

        });
        logger.warn("解析耗时: [{}]", System.currentTimeMillis() - nothingTime);
    }


    public static void importExcel(
            InputStream input, String[] keys, String fileName, Consumer<Row> consumer)
            throws Exception {
        Workbook wb = getWorkbook(input, fileName);
        Sheet sheet = wb.getSheetAt(0);
        for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (row == null) {
                continue;
            }
            consumer.accept(row);
        }
    }


    public static Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
        Workbook wb = WorkbookFactory.create(inStr);
        return wb;
    }
}
