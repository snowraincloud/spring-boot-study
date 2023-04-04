import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;

import java.util.Map;


//@RequestMapping("timing")
public class AdvanceShipmentController {

    public static String exportFolders(StringBuilder jsonStr){
        System.out.println(jsonStr);
        JSONArray arr= JSON.parseArray(jsonStr.toString());
        JSONArray res= Tree2ExcelUtil.listToTree(arr, "ItemCode", "ParentId", "children");
        //去掉最外层[]
        String replaceAll=trimstart(String.valueOf(res),"[");
        replaceAll=trimend(replaceAll,"]");

        Map tree = JSON.parseObject(replaceAll, Map.class);
       Boolean result = Tree2ExcelUtil.tree2Excel(tree, "E:\\" + System.currentTimeMillis() + ".xlsx", "ItemName", "CateName", "children");
        System.out.println(result);
        String returns ="false";
        if (result)returns="true";
        return returns;
    }


    public static void main(String[] args) {
        String jsonStr = "[{\"ItemCode\":\"972717\",\"ItemName\":\"5、后勤部\",\"ParentId\":\"0\",\"CateName\":\"管理、查看、编辑:后勤部-文档管理、王晴邈、黄晶、文控中心文档管理、文档安全审计\"},{\"ItemCode\":\"1247423\",\"ItemName\":\"03-客餐及下午茶数据\",\"ParentId\":\"972717\",\"CateName\":\"编辑、下载、管理:黄江平、朱光菊、梁桂芳、王鹏伟、钟良德、后勤部-文档管理、王晴邈、黄晶、文控中心文档管理、文档安全审计\"},{\"ItemCode\":\"1688818\",\"ItemName\":\"03食材验收结果通知单\",\"ParentId\":\"1247423\",\"CateName\":\"编辑、下载、管理:黄江平、朱光菊、梁桂芳、王鹏伟、钟良德、后勤部-文档管理、王晴邈、黄晶、文控中心文档管理、文档安全审计\"},{\"ItemCode\":\"1762357\",\"ItemName\":\"2022年食材验收结果通知单\",\"ParentId\":\"1688818\",\"CateName\":\"编辑、下载、管理:黄江平、朱光菊、梁桂芳、王鹏伟、钟良德、后勤部-文档管理、王晴邈、黄晶、文控中心文档管理、文档安全审计\"},{\"ItemCode\":\"2071943\",\"ItemName\":\"2023年食材验收结果通知单\",\"ParentId\":\"1688818\",\"CateName\":\"编辑、下载、管理:黄江平、朱光菊、梁桂芳、王鹏伟、钟良德、后勤部-文档管理、王晴邈、黄晶、文控中心文档管理、文档安全审计\"},{\"ItemCode\":\"1247429\",\"ItemName\":\"01-客餐就餐接待明细\",\"ParentId\":\"1247423\",\"CateName\":\"编辑、下载、管理:黄江平、朱光菊、梁桂芳、王鹏伟、钟良德、后勤部-文档管理、王晴邈、黄晶、文控中心文档管理、文档安全审计\"},{\"ItemCode\":\"1247433\",\"ItemName\":\"02-下午茶人数更新\",\"ParentId\":\"1247423\",\"CateName\":\"编辑、下载、管理:黄江平、朱光菊、梁桂芳、王鹏伟、钟良德、后勤部-文档管理、王晴邈、黄晶、文控中心文档管理、文档安全审计\"},{\"ItemCode\":\"1247422\",\"ItemName\":\"02-班车相关数据\",\"ParentId\":\"972717\",\"CateName\":\"编辑、下载、管理:黄江平、张倩、黄家欣、后勤部-文档管理、王晴邈、黄晶、文控中心文档管理、文档安全审计\"},{\"ItemCode\":\"1247427\",\"ItemName\":\"02-班车乘坐明细\",\"ParentId\":\"1247422\",\"CateName\":\"编辑、下载、管理:黄江平、张倩、黄家欣、后勤部-文档管理、王晴邈、黄晶、文控中心文档管理、文档安全审计\"},{\"ItemCode\":\"189655\",\"ItemName\":\"2020年\",\"ParentId\":\"1247427\",\"CateName\":\"编辑、下载、管理:黄江平、张倩、黄家欣、后勤部-文档管理、黄晶、文控中心文档管理、文档安全审计\"},{\"ItemCode\":\"2125987\",\"ItemName\":\"2023年\",\"ParentId\":\"1247427\",\"CateName\":\"编辑、下载、管理:黄江平、张倩、黄家欣、后勤部-文档管理、黄晶、文控中心文档管理、文档安全审计\"},{\"ItemCode\":\"426368\",\"ItemName\":\"2022年\",\"ParentId\":\"1247427\",\"CateName\":\"编辑、下载、管理:黄江平、张倩、黄家欣、后勤部-文档管理、黄晶、文控中心文档管理、文档安全审计\"},{\"ItemCode\":\"189656\",\"ItemName\":\"2019年\",\"ParentId\":\"1247427\",\"CateName\":\"编辑、下载、管理:黄江平、张倩、黄家欣、后勤部-文档管理、黄晶、文控中心文档管理、文档安全审计\"},{\"ItemCode\":\"189654\",\"ItemName\":\"2021年\",\"ParentId\":\"1247427\",\"CateName\":\"编辑、下载、管理:黄江平、张倩、黄家欣、后勤部-文档管理、黄晶、文控中心文档管理、文档安全审计\"},{\"ItemCode\":\"1247426\",\"ItemName\":\"01-班车晚点统计\",\"ParentId\":\"1247422\",\"CateName\":\"编辑、下载、管理:黄江平、张倩、黄家欣、后勤部-文档管理、黄晶、文控中心文档管理、文档安全审计\"},{\"ItemCode\":\"1247421\",\"ItemName\":\"01-后勤部工作分工及对接人\",\"ParentId\":\"972717\",\"CateName\":\"编辑、管理、查看:柯进红、姚春瑜、党玉珠、张倩、后勤部-文档管理、黄晶、文控中心文档管理、文档安全审计\"},{\"ItemCode\":\"1247424\",\"ItemName\":\"04-宿舍相关数据\",\"ParentId\":\"972717\",\"CateName\":\"编辑、下载、管理:姚春瑜、刘媚诗、俞光飞、谈颜金、梁洁琼、后勤部-文档管理、黄晶、文控中心文档管理、文档安全审计\"},{\"ItemCode\":\"1247437\",\"ItemName\":\"02-宿舍卫生检查结果\",\"ParentId\":\"1247424\",\"CateName\":\"编辑、下载、管理:姚春瑜、刘媚诗、俞光飞、谈颜金、梁洁琼、后勤部-文档管理、黄晶、文控中心文档管理、文档安全审计\"},{\"ItemCode\":\"1774234\",\"ItemName\":\"01-2022年各月宿舍安全卫生检查汇总表\",\"ParentId\":\"1247437\",\"CateName\":\"编辑、下载、管理:姚春瑜、刘媚诗、谈颜金、梁洁琼、后勤部-文档管理、黄晶、文控中心文档管理、文档安全审计\"},{\"ItemCode\":\"1247436\",\"ItemName\":\"01-每月住宿明细-考勤使用\",\"ParentId\":\"1247424\",\"CateName\":\"编辑、下载、管理:姚春瑜、刘媚诗、谈颜金、梁洁琼、公司、后勤部-文档管理、黄晶、文控中心文档管理、文档安全审计\"},{\"ItemCode\":\"1591654\",\"ItemName\":\"01-2022年\",\"ParentId\":\"1247436\",\"CateName\":\"编辑、下载、管理:姚春瑜、刘媚诗、谈颜金、梁洁琼、公司、后勤部-文档管理、黄晶、文控中心文档管理、文档安全审计\"},{\"ItemCode\":\"2126061\",\"ItemName\":\"02-2023年\",\"ParentId\":\"1247436\",\"CateName\":\"编辑、下载、管理:姚春瑜、刘媚诗、谈颜金、梁洁琼、后勤部-文档管理、黄晶、文控中心文档管理、文档安全审计\"}]\n" +
                "[{\"ParentId\":\"0\",\"ItemCode\":\"972717\",\"children\":[{\"ParentId\":\"972717\",\"ItemCode\":\"1247423\",\"children\":[{\"ParentId\":\"1247423\",\"ItemCode\":\"1688818\",\"children\":[{\"ParentId\":\"1688818\",\"ItemCode\":\"1762357\",\"ItemName\":\"2022年食材验收结果通知单\",\"CateName\":\"编辑、下载、管理:黄江平、朱光菊、梁桂芳、王鹏伟、钟良德、后勤部-文档管理、黄晶、文控中心文档管理、文档安全审计\"},{\"ParentId\":\"1688818\",\"ItemCode\":\"2071943\",\"ItemName\":\"2023年食材验收结果通知单\",\"CateName\":\"编辑、下载、管理:黄江平、朱光菊、梁桂芳、王鹏伟、钟良德、后勤部-文档管理、黄晶、文控中心文档管理、文档安全审计\"}],\"ItemName\":\"03食材验收结果通知单\",\"CateName\":\"编辑、下载、管理:黄江平、朱光菊、梁桂芳、王鹏伟、钟良德、后勤部-文档管理、黄晶、文控中心文档管理、文档安全审计\"},{\"ParentId\":\"1247423\",\"ItemCode\":\"1247429\",\"ItemName\":\"01-客餐就餐接待明细\",\"CateName\":\"编辑、下载、管理:黄江平、朱光菊、梁桂芳、王鹏伟、钟良德、后勤部-文档管理、黄晶、文控中心文档管理、文档安全审计\"},{\"ParentId\":\"1247423\",\"ItemCode\":\"1247433\",\"ItemName\":\"02-下午茶人数更新\",\"CateName\":\"编辑、下载、管理:黄江平、朱光菊、梁桂芳、王鹏伟、钟良德、后勤部-文档管理、黄晶、文控中心文档管理、文档安全审计\"}],\"ItemName\":\"03-客餐及下午茶数据\",\"CateName\":\"编辑、下载、管理:黄江平、朱光菊、梁桂芳、王鹏伟、钟良德、后勤部-文档管理、黄晶、文控中心文档管理、文档安全审计\"},{\"ParentId\":\"972717\",\"ItemCode\":\"1247422\",\"children\":[{\"ParentId\":\"1247422\",\"ItemCode\":\"1247427\",\"children\":[{\"ParentId\":\"1247427\",\"ItemCode\":\"189655\",\"ItemName\":\"2020年\",\"CateName\":\"编辑、下载、管理:黄江平、张倩、黄家欣、后勤部-文档管理、黄晶、文控中心文档管理、文档安全审计\"},{\"ParentId\":\"1247427\",\"ItemCode\":\"2125987\",\"ItemName\":\"2023年\",\"CateName\":\"编辑、下载、管理:黄江平、张倩、黄家欣、后勤部-文档管理、黄晶、文控中心文档管理、文档安全审计\"},{\"ParentId\":\"1247427\",\"ItemCode\":\"426368\",\"ItemName\":\"2022年\",\"CateName\":\"编辑、下载、管理:黄江平、张倩、黄家欣、后勤部-文档管理、黄晶、文控中心文档管理、文档安全审计\"},{\"ParentId\":\"1247427\",\"ItemCode\":\"189656\",\"ItemName\":\"2019年\",\"CateName\":\"编辑、下载、管理:黄江平、张倩、黄家欣、后勤部-文档管理、黄晶、文控中心文档管理、文档安全审计\"},{\"ParentId\":\"1247427\",\"ItemCode\":\"189654\",\"ItemName\":\"2021年\",\"CateName\":\"编辑、下载、管理:黄江平、后勤部-文档管理、文控中心文档管理、文档安全审计\"}],\"ItemName\":\"02-班车乘坐明细\",\"CateName\":\"编辑、下载、管理:黄江平、后勤部-文档管理、文控中心文档管理、文档安全审计\"},{\"ParentId\":\"1247422\",\"ItemCode\":\"1247426\",\"ItemName\":\"01-班车晚点统计\",\"CateName\":\"编辑、下载、管理:黄江平、后勤部-文档管理、文控中心文档管理、文档安全审计\"}],\"ItemName\":\"02-班车相关数据\",\"CateName\":\"编辑、下载、管理:黄江平、后勤部-文档管理、文控中心文档管理、文档安全审计\"},{\"ParentId\":\"972717\",\"ItemCode\":\"1247421\",\"ItemName\":\"01-后勤部工作分工及对接人\",\"CateName\":\"编辑、管理、查看:柯进红、姚春瑜、党玉珠、后勤部-文档管理、文控中心文档管理、文档安全审计\"},{\"ParentId\":\"972717\",\"ItemCode\":\"1247424\",\"children\":[{\"ParentId\":\"1247424\",\"ItemCode\":\"1247437\",\"children\":[{\"ParentId\":\"1247437\",\"ItemCode\":\"1774234\",\"ItemName\":\"01-2022年各月宿舍安全卫生检查汇总表\",\"CateName\":\"编辑、下载、管理:姚春瑜、刘媚诗、谈颜金、梁洁琼、后勤部-文档管理、文控中心文档管理、文档安全审计\"}],\"ItemName\":\"02-宿舍卫生检查结果\",\"CateName\":\"编辑、下载、管理:姚春瑜、刘媚诗、谈颜金、梁洁琼、后勤部-文档管理、文控中心文档管理、文档安全审计\"},{\"ParentId\":\"1247424\",\"ItemCode\":\"1247436\",\"children\":[{\"ParentId\":\"1247436\",\"ItemCode\":\"1591654\",\"ItemName\":\"01-2022年\",\"CateName\":\"编辑、下载、管理:姚春瑜、刘媚诗、谈颜金、梁洁琼、后勤部-文档管理、文控中心文档管理、文档安全审计\"},{\"ParentId\":\"1247436\",\"ItemCode\":\"2126061\",\"ItemName\":\"02-2023年\",\"CateName\":\"编辑、下载、管理:姚春瑜、刘媚诗、谈颜金、梁洁琼、后勤部-文档管理、文控中心文档管理、文档安全审计\"}],\"ItemName\":\"01-每月住宿明细-考勤使用\",\"CateName\":\"编辑、下载、管理:姚春瑜、刘媚诗、谈颜金、梁洁琼、后勤部-文档管理、文控中心文档管理、文档安全审计\"}],\"ItemName\":\"04-宿舍相关数据\",\"CateName\":\"编辑、下载、管理:姚春瑜、刘媚诗、谈颜金、梁洁琼、后勤部-文档管理、文控中心文档管理、文档安全审计\"}],\"ItemName\":\"5、后勤部\",\"CateName\":\"管理、查看、编辑:后勤部-文档管理、文控中心文档管理、文档安全审计\"}]";
        Map tree = JSON.parseObject(jsonStr, Map.class);
        Tree2ExcelUtil.tree2Excel(tree, "E:\\" + System.currentTimeMillis() + ".xlsx", "ItemName", "CateName", "children");
    }


    /*
     * 删除开头字符串
     */
    public static String trimstart(String inStr, String prefix) {
        if (inStr.startsWith(prefix)) {
            return (inStr.substring(prefix.length()));
        }
        return inStr;
    }
    /*
     * 删除末尾字符串
     */
    public static String trimend(String inStr, String suffix) {
        if (inStr.endsWith(suffix)) {
            return (inStr.substring(0,inStr.length()-suffix.length()));
        }
        return inStr;
    }



}

