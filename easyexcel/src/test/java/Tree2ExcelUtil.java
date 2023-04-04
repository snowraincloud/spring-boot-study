import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * 树形结构数据导出excel工具
 * <p>
 * Created by lzy on 2021/2/24 14:09
 */
@SuppressWarnings("ALL")
public class Tree2ExcelUtil {
    private static int maxCol = 0;//标识最大列
    private static String lableName = "lable";
    private static String cateName = "lable2";
    private static String childrenName = "children";
    private static final String COL = "col";
    private static final String ROW = "row";
    private static final String ROW_OFT = "rowOft";
    private static final String ROW_SIZE = "rowSize";

    /**
     * 将传过来的json串根据父id将子级放到父级下面
     * @param arr 传过来的json串
     * @param id 编码
     * @param pid 父级编码
     * @param child 子级
     * @return
     */
    public static JSONArray listToTree(JSONArray arr, String id, String pid, String child){
        JSONArray r=new JSONArray();
        JSONObject hash=new JSONObject();
        for(int i=0;i<arr.size();i++){
            JSONObject json= (JSONObject) arr.get(i);
            hash.put(json.getString(id),json);
        }
        for(int j=0;j<arr.size();j++){
            JSONObject aVal= (JSONObject) arr.get(j);
            JSONObject hashVP =null;
            if(aVal.getString(pid)!=null){
                hashVP = (JSONObject)hash.get(aVal.getString(pid));
            }
            if(hashVP!=null){
                if(hashVP.get(child)!=null){
                    JSONArray ch= (JSONArray) hashVP.get(child);
                    ch.add(aVal);
                    hashVP.put(child,ch);
                }  else{
                    JSONArray ch=new JSONArray();
                    ch.add(aVal);
                    hashVP.put(child,ch);
                }
            }else{
                r.add(aVal);
            }
        }
        return r;
    }

    /**
     * 树形结构数据生成excel文件
     *
     * @param tree     树形数据
     * @param filePath 文件路径
     * @return
     */
    public static boolean tree2Excel(Map tree, String filePath) {
        return tree2Excel(tree, filePath,null, null, null);
    }

    /**
     * 树形结构数据生成excel文件
     *
     * @param tree         树形数据
     * @param filePath     文件路径
     * @param lableName    标签Key名称
     * @param childrenName 子节点Key名称
     * @return
     */
    public static boolean tree2Excel(Map tree, String filePath, String lableName,String cateName, String childrenName) {
        if (isBlank(filePath)) {
            System.err.println("文件名称不能为空");
            return false;
        }
        try {
            if (!isBlank(lableName)) Tree2ExcelUtil.lableName = lableName;
            if (!isBlank(lableName)) Tree2ExcelUtil.cateName = cateName;
            if (!isBlank(childrenName))Tree2ExcelUtil.childrenName = childrenName;

            //计算行列坐标
            coreAlgoCol(tree, 1);
            coreAlgoRow(tree);

            //创建excel文件
            File file = new File(filePath);//具体到路径
            boolean bfile = file.createNewFile();
            // 复制模板到新文件
            if (bfile) {
                //excel数据处理
                Workbook wk = null;
                //判断字符串是否以指定后缀结尾
                if (filePath.endsWith("xls")) wk = new HSSFWorkbook();
                if (filePath.endsWith("xlsx")) wk = new SXSSFWorkbook();
                if (wk == null)System.err.println("文件名称不正确");
                //创建一个sheet页
                Sheet sheet = wk.createSheet("Sheet1");
                int colSize = maxCol * 2 + 2;
                int rowSize = toInt(tree.get(ROW_SIZE), 1);
                for (int i = 0; i <= rowSize; i++) {
                    int cellnum = 1;
                    Row headRow = sheet.createRow(0);
                    Row row = sheet.createRow(i);
                    for (int j = 0; j <= colSize; j++) {

                        if(j>0 &&j%  2 == 0){
                            headRow.createCell(j-1).setCellValue("第"+cellnum+"层");
                            headRow.createCell(j).setCellValue("权限描述");
                            cellnum++;
                            row.createCell(j);

                        }
                    }
                }
                //配置单元格背景色
                CellStyle style1 = wk.createCellStyle();
                style1.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
                style1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                style1.setAlignment(HorizontalAlignment.LEFT);//
                style1.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
                style1.setWrapText(true);//自动换行
                style1.setBorderBottom(BorderStyle.THIN);
                style1.setBorderLeft(BorderStyle.THIN);
                style1.setBorderTop(BorderStyle.THIN);
                style1.setBorderRight(BorderStyle.THIN);

                CellStyle style2 = wk.createCellStyle();
                style2.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
                style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                style2.setAlignment(HorizontalAlignment.LEFT);//
                style2.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
                style2.setWrapText(true);//自动换行
                style2.setBorderBottom(BorderStyle.THIN);
                style2.setBorderLeft(BorderStyle.THIN);
                style2.setBorderTop(BorderStyle.THIN);
                style2.setBorderRight(BorderStyle.THIN);

                dealCell(sheet, tree, style1, style2);
                if (wk != null) {
                    FileOutputStream fos = null;
                    fos = new FileOutputStream(file);
                    wk.write(fos);
                    fos.flush();
                    closeStream(fos);
                    wk.close();
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * 根据计算好的坐标填充每一个单元格
     *
     * @param sheet  #
     * @param tree   数据
     * @param style1 单元格格式
     * @param style2 单元格格式
     */
    private static void dealCell(Sheet sheet, Map tree, CellStyle style1, CellStyle style2) {
        Row row = sheet.getRow(toInt(tree.get(ROW)));
        int oftCol = (toInt(tree.get(COL)) - 1) * 2 + 1;
        int oftCol2 = (toInt(tree.get(COL)) - 1) * 2 + 2;
        Cell cell = row.createCell(oftCol);
        cell.setCellStyle(style1);
        cell.setCellValue(String.valueOf(tree.get(lableName)));
        Cell cell2 = row.createCell(oftCol2);
        cell2.setCellStyle(style2);
        cell2.setCellValue(String.valueOf(tree.get(cateName)));

        sheet.setColumnWidth(oftCol, 256 * 25);
        sheet.setColumnWidth(oftCol2, 256 * 40);
        Object childrenObj = tree.get(childrenName);

        if (childrenObj != null) {
            List<Map> children = (List<Map>) childrenObj;
            if (children.size() > 0) {
                int size = children.size();
                int startRow=toInt(children.get(0).get(ROW));

                startRow=toInt(children.get(0).get(ROW));

                int endRow = toInt(children.get(size - 1).get(ROW));
                System.out.println(startRow+"  "+endRow);

                int col = oftCol + 1;
                for (; startRow <= endRow; startRow++) {
//                    if (endRow != startRow && col== col) {
//                        sheet.addMergedRegionUnsafe(new CellRangeAddress(startRow,endRow,col,col));
//                    }
                    sheet.getRow(startRow).getCell(col).setCellStyle(style2);
                }
                for (Map child : children) {
                    dealCell(sheet, child, style1, style2);
                }
            }
        }
    }

    /**
     * 主要算法，计算列的坐标，计算每个节点所占行
     * @param tree  数据
     * @param col   递增的列
     * @param trees 把高级别向下传递计算递增的行高
     */
    private static void coreAlgoCol(Map tree, int col, Map... trees) {
        tree.put(COL, col);
        Object childrenObj = tree.get(childrenName);
        if (childrenObj != null) {
            List<Map> children = (List<Map>) childrenObj;
            if (children.size() > 0) {
                int size = children.size() * 2 - 1;
                tree.put(ROW_SIZE, size);
                int len = trees != null ? trees.length + 1 : 1;
                Map[] arrData = new Map[len];

                if (trees != null && trees.length > 0) {
                    for (int i = 0; i < trees.length; i++) {
                        Map tree1 = trees[i];
                        tree1.put(ROW_SIZE, toInt(tree1.get(ROW_SIZE), 1) + size - 1);
                        arrData[i] = tree1;
                    }
                }
                arrData[len - 1] = tree;
                for (Map tree1 : children) {
                    int newCol = col + 1;
                    if (newCol > maxCol) {
                        maxCol = newCol;
                    }
                    coreAlgoCol(tree1, newCol, arrData);
                }
            }
        }
    }

    /**
     * 主要算法，计算行的坐标
     *
     * @param tree
     */
    private static void coreAlgoRow(Map tree) {
        if (toInt(tree.get(ROW)) == 0) {
            tree.put(ROW, Math.round(toInt(tree.get(ROW_SIZE), 1) / 2.0f));
        }
        Object childrenObj = tree.get(childrenName);
        if (childrenObj != null) {
            List<Map> children = (List<Map>) childrenObj;
            if (children.size() > 0) {
                int tempOft = toInt(tree.get(ROW_OFT));
                for (Map tree1 : children) {
                    int rowSize = toInt(tree1.get(ROW_SIZE), 1);
                    tree1.put(ROW_OFT, tempOft);
                    tree1.put(ROW, tempOft + Math.round(rowSize / 2.0f));
                    tempOft += rowSize ;//间隔行
                    coreAlgoRow(tree1);
                }
            }
        }
    }

    /**
     * 转int
     * @param val 传入值
     * @return
     */
    private static int toInt(Object val) {
        return toInt(val, 0);
    }
    private static int toInt(Object val, Integer defVal) {
        try {
            return Integer.parseInt(String.valueOf(val));
        } catch (NumberFormatException ignored) {
        }
        return defVal;
    }
    /**
     * 判断值是否为空
     * @param str 传过来的值
     * @return
     */
    private static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0;
    }
    /**
     * 关闭流
     *
     * @param closeables 不定长数组 流对象
     */
    public static void closeStream(Closeable... closeables) {
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
