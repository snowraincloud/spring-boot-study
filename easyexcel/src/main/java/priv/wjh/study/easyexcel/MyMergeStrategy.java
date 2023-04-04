package priv.wjh.study.easyexcel;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.WorkbookWriteHandler;
import com.alibaba.excel.write.handler.context.WorkbookWriteHandlerContext;
import com.alibaba.excel.write.merge.AbstractMergeStrategy;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

public class MyMergeStrategy implements WorkbookWriteHandler {
    @Override
    public void afterWorkbookDispose(WorkbookWriteHandlerContext context) {
        WorkbookWriteHandler.super.afterWorkbookDispose(context);
        Sheet sheet = context.getWriteContext()
                .writeSheetHolder()
                .getSheet();
        CellRangeAddress cellRangeAddress = new CellRangeAddress(2, 11, 1,
                                                                 6);
        sheet.addMergedRegion(cellRangeAddress);
    }

}
