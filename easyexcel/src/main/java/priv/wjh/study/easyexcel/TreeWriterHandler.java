package priv.wjh.study.easyexcel;

import com.alibaba.excel.write.handler.WorkbookWriteHandler;
import com.alibaba.excel.write.handler.context.WorkbookWriteHandlerContext;
import lombok.Data;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;

@Data
public class TreeWriterHandler implements WorkbookWriteHandler {
    private final List<Item> tree;
    private CellStyle cellStyle;

    @Override
    public void afterWorkbookDispose(WorkbookWriteHandlerContext context) {
        WorkbookWriteHandler.super.afterWorkbookDispose(context);
        Sheet sheet = context.getWriteContext()
                .writeSheetHolder()
                .getSheet();

        // 单元格样式
        Workbook workbook = context.getWriteWorkbookHolder()
                .getWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();
        // 水平对齐方式
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        // 垂直对齐方式
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setWrapText(true);
        this.cellStyle = cellStyle;
        // 填充标题
        fillTitle(sheet, ExcelUtils.countLayers(tree));
        // 填充数据
        doWriteData(sheet, tree, 1, 0);
    }

    /**
     * 填充标题行
     */
    public void fillTitle(Sheet sheet, int layers){
        int row = 0;
        int col = 0;
        for (int i = 1; i <= layers; i++) {
            sheet.setColumnWidth(col, 256 * 20);
            setCellValue(sheet, row, col++, "第" + i + "层");
            sheet.setColumnWidth(col, 256 * 35);
            setCellValue(sheet, row, col++, "权限描述");
        }
    }

    /**
     * 递归填充数据
     */
    public int doWriteData(Sheet sheet, List<Item> tree, int row, int col) {
        int lastRow = row;
        for (Item item : tree) {
            // 填充数据
            setCellValue(sheet, row, col , item.getItemName());
            setCellValue(sheet, row, col + 1, item.getCateName());
            // 填充下级数据
            lastRow = doWriteData(sheet, item.getChildren(), row, col + 2);

            if (lastRow - row >= 1){
                // 进行单元个合并
                for (int i = 0; i < 2; i++) {
                    CellRangeAddress cellRangeAddress = new CellRangeAddress(row, lastRow, col + i,
                                                                             col + i);
                    sheet.addMergedRegion(cellRangeAddress);
                }
            }
            row = lastRow + 1;
        }
        return lastRow;
    }

    /**
     * 设置数据
     */
    public void setCellValue(Sheet sheet, int rowIndex, int colIndex, String val) {
        // 按长度进行分割
        StringBuilder sb = new StringBuilder(val);
        for (int i = 30; i < sb.length(); i+=30) {
            sb.insert(i, "\n");
        }
        Cell cell = getCell(sheet, rowIndex, colIndex);
        // 设置到单元格中
        cell.setCellValue(sb.toString());
        // 设置单元格样式
        cell.setCellStyle(this.cellStyle);
    }


    public static Cell getCell(Sheet sheet, int rowIndex, int colIndex) {
        Row row = sheet.getRow(rowIndex);
        if (row == null) {
            row = sheet.createRow(rowIndex);
        }
        Cell cell = row.getCell(colIndex);
        if (cell == null) {
            cell = row.createCell(colIndex);
        }
        return cell;
    }
}
