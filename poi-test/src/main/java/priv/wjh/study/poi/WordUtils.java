package priv.wjh.study.poi;

import com.microsoft.schemas.office.office.CTLock;
import com.microsoft.schemas.vml.*;
import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STTrueFalse;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.stream.Stream;

/**
 * word 工具处理类
 * @author houyinke
 * @date 2022/7/19 15:57
 */
public class WordUtils {

    static Logger logger = LoggerFactory.getLogger(WordUtils.class);

    /**
     * 水印字体字体
     */
    private static final String fontName = "PingFang SC";

    /**
     * 一个字平均长度，单位pt，用于：计算文本占用的长度（文本总个数*单字长度）
     */
    private static final int widthPerWord = 10;

    /**
     * word文字水印(调用poi封装的createWatermark方法)
     *
     * @param doc     XWPFDocument对象
     * @param markStr 水印文字
     */
    public static void setWordWaterMark(XWPFDocument doc, String markStr, String fontColor) {
        XWPFParagraph paragraph = doc.createParagraph();
        XWPFHeaderFooterPolicy headerFooterPolicy = doc.getHeaderFooterPolicy();
        if (headerFooterPolicy == null) {
            headerFooterPolicy = doc.createHeaderFooterPolicy();
        }
        // create default Watermark - fill color black and not rotated
        headerFooterPolicy.createWatermark(markStr);
        // get the default header
        // Note: createWatermark also sets FIRST and EVEN headers
        // but this code does not updating those other headers
        XWPFHeader header = headerFooterPolicy.getHeader(XWPFHeaderFooterPolicy.DEFAULT);
        paragraph = header.getParagraphArray(0);
//            // get com.microsoft.schemas.vml.CTShape where fill color and rotation is set
        paragraph.getCTP().newCursor();
        org.apache.xmlbeans.XmlObject[] xmlobjects = paragraph.getCTP().getRArray(0).getPictArray(0).selectChildren(
                new javax.xml.namespace.QName("urn:schemas-microsoft-com:vml", "shape"));
        if (xmlobjects.length > 0) {
            CTShape ctshape = (CTShape) xmlobjects[0];
            ctshape.setFillcolor(fontColor);
            ctshape.setStyle(ctshape.getStyle() + ";rotation:315");
        }
    }

    /**
     * 将指定的字符串重复repeats次.
     *
     * @param pattern 字符串
     * @param repeats 重复次数
     * @return 生成的字符串
     */
    private static String repeatString(String pattern, int repeats) {
        StringBuilder buffer = new StringBuilder(pattern.length() * repeats);
        Stream.generate(() -> pattern).limit(repeats).forEach(buffer::append);
        return new String(buffer);
    }

    /**
     * 为文档添加水印
     * 实现参考了{@link XWPFHeaderFooterPolicy#(String, int)}
     *
     * @param doc        需要被处理的docx文档对象
     * @param customText 水印文本
     * @param type       类型：1.平铺；2.单个
     */
    private static void waterMarkDocXDocument(XWPFDocument doc, String customText, String styleTop, int type, String fontColor, String fontSize, String rotation) {
        XWPFHeader header = doc.createHeader(HeaderFooterType.DEFAULT); // 如果之前已经创建过 DEFAULT 的Header，将会复用之
        int size = header.getParagraphs().size();
        if (size == 0) {
            header.createParagraph();
        }

        CTP ctp = header.getParagraphArray(0).getCTP();
        byte[] rsidr = doc.getDocument().getBody().getPArray(0).getRsidR();
        byte[] rsidrdefault = doc.getDocument().getBody().getPArray(0).getRsidRDefault();
        ctp.setRsidP(rsidr);
        ctp.setRsidRDefault(rsidrdefault);
        CTPPr ppr = ctp.addNewPPr();
        ppr.addNewPStyle().setVal("Header");

        // 开始加水印
        CTR ctr = ctp.addNewR();
        CTRPr ctrpr = ctr.addNewRPr();
        ctrpr.addNewNoProof();
        CTGroup group = CTGroup.Factory.newInstance();
        CTShapetype shapetype = group.addNewShapetype();
        CTTextPath shapeTypeTextPath = shapetype.addNewTextpath();
        shapeTypeTextPath.setOn(STTrueFalse.T);
        shapeTypeTextPath.setFitshape(STTrueFalse.T);
        CTLock lock = shapetype.addNewLock();
        lock.setExt(STExt.VIEW);
        CTShape shape = group.addNewShape();
        shape.setId("PowerPlusWaterMarkObject");
        shape.setSpid("_x0000_s102");
        shape.setType("#_x0000_t136");

        // 平铺或单个
        if (type != 2) {
            // 设置形状样式（旋转，位置，相对路径等参数）
            shape.setStyle(getShapeStyle(customText, styleTop, rotation));
        } else {
            // 设置形状样式（旋转，位置，相对路径等参数）
            shape.setStyle(getShapeStyle());
        }

        shape.setFillcolor(fontColor);
        // 字体设置为实心
        shape.setStroked(STTrueFalse.FALSE);
        // 绘制文本的路径
        CTTextPath shapeTextPath = shape.addNewTextpath();
        // 设置文本字体与大小
        shapeTextPath.setStyle("font-family:" + fontName + ";font-size:" + fontSize);
        shapeTextPath.setString(customText);
        CTPicture pict = ctr.addNewPict();
        pict.set(group);
    }

    /**
     * 以艺术字方式加上水印(平铺)
     *
     * @param docx       XWPFDocument对象
     * @param customText 水印文字
     */
    public static void makeFullWaterMarkByWordArt(XWPFDocument docx, String customText, String fontColor, String fontSize, String styleRotation) {
        // 水印文字之间使用8个空格分隔
        customText = customText + repeatString(" ", 16);
        // 一行水印重复水印文字次数
        customText = repeatString(customText, 10);
        // 与顶部的间距
        String styleTop = "0pt";

        if (docx == null) {
            return;
        }

        // 遍历文档，添加水印
        for (int lineIndex = -10; lineIndex < 20; lineIndex++) {
            styleTop = 200 * lineIndex + "pt";
            waterMarkDocXDocument(docx, customText, styleTop, 1, fontColor, fontSize, styleRotation);
        }
    }

    /**
     * 以艺术字方式加上水印(单个)
     *
     * @param docx       XWPFDocument对象
     * @param customText 水印文字
     */
    public static void makeWaterMarkByWordArt(XWPFDocument docx, String customText, String fontColor, String fontSize, String rotation) {
        // 与顶部的间距
        String styleTop = "0pt";
        // 判断文档是否为空
        if (docx == null) {
            return;
        }

        // 添加水印
        waterMarkDocXDocument(docx, customText, styleTop, 2, fontColor, fontSize, rotation);
    }

    /**
     * 构建Shape的样式参数
     *
     * @param customText 水印文本
     * @return
     */
    private static String getShapeStyle(String customText, String styleTop, String styleRotation) {
        StringBuilder sb = new StringBuilder();
        // 文本path绘制的定位方式
        sb.append("position: ").append("absolute");
        // 计算文本占用的长度（文本总个数*单字长度）
        sb.append(";width: ").append(customText.length() * widthPerWord).append("pt");
        // 字体高度
        sb.append(";height: ").append("20pt");
        sb.append(";z-index: ").append("-251654144");
        sb.append(";mso-wrap-edited: ").append("f");
        sb.append(";margin-top: ").append(styleTop);
        sb.append(";mso-position-horizontal-relative: ").append("margin");
        sb.append(";mso-position-vertical-relative: ").append("margin");
        sb.append(";mso-position-vertical: ").append("left");
        sb.append(";mso-position-horizontal: ").append("center");
        sb.append(";rotation: ").append(styleRotation);
        return sb.toString();
    }

    /**
     * 构建Shape的样式参数
     *
     * @return
     */
    private static String getShapeStyle() {
        StringBuilder sb = new StringBuilder();
        // 文本path绘制的定位方式
        sb.append("position: ").append("absolute");
        sb.append(";left: ").append("opt");
        // 计算文本占用的长度（文本总个数*单字长度）
        sb.append(";width: ").append("500pt");
        // 字体高度
        sb.append(";height: ").append("150pt");
        sb.append(";z-index: ").append("-251654144");
        sb.append(";mso-wrap-edited: ").append("f");
        sb.append(";margin-left: ").append("-50pt");
        sb.append(";margin-top: ").append("270pt");
        sb.append(";mso-position-horizontal-relative: ").append("margin");
        sb.append(";mso-position-vertical-relative: ").append("margin");
        sb.append(";mso-width-relative: ").append("page");
        sb.append(";mso-height-relative: ").append("page");
        sb.append(";rotation: ").append("-2949120f");
        return sb.toString();
    }

    /**
     * word 字数统计
     *
     * @param xwpfDocument doc文档
     * @return
     */
    public static int count(XWPFDocument xwpfDocument) {
        List<XWPFParagraph> paragraphs = xwpfDocument.getParagraphs();
        // 行数,字数
        int rowCount = 1, count = 0;

        try {
            // 循环读取字数
            for (XWPFParagraph xwpfParagraph : paragraphs) {
                int linLength = 0;
                String lineStr = "";
                List<XWPFRun> xwpfRuns = xwpfParagraph.getRuns();
                for (XWPFRun xwpfRun : xwpfRuns) {
                    linLength += xwpfRun.toString().trim().length();
                    lineStr += xwpfRun.toString();
                    count += xwpfRun.toString().trim().length();
                }

                rowCount++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }

    /**
     * 加载docx格式的word文档
     *
     * @param inputStream
     * @return
     */
    private static XWPFDocument loadDocXDocument(InputStream inputStream) {
        XWPFDocument doc;
        try {
            doc = new XWPFDocument(inputStream);
        } catch (Exception e) {
            throw new RuntimeException("文档加载失败！！");
        }
        return doc;
    }

    public static void main(String[] args) throws IOException {
        XWPFDocument xwpfDocument = null;
        try {
            //输入的docx文档
            InputStream in = new FileInputStream(new File("./ssm开发文档.docx"));
            xwpfDocument = WordUtils.loadDocXDocument(in);

            // 统计字数
            int count = WordUtils.count(xwpfDocument);
            System.out.println("word字数：" + count);

            // 添加水印
            WordUtils.makeFullWaterMarkByWordArt(xwpfDocument, "中电金信", "#888888", "0.7pt", "-30");

            // 写出加水印后的文档
            String filePath = "./1ssm开发文档.docx";
            OutputStream os = new FileOutputStream(filePath);
            xwpfDocument.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (xwpfDocument != null) {
                xwpfDocument.close();
            }
        }
    }
}
