package priv.wjh.study.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson2.JSON;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ExcelUtils {

    /**
     * 列表转换为树
     */
    public static List<Item> listToTree(List<Item> items) {
        // 转换为map
        Map<String, Item> map = items.stream()
                .collect(Collectors.toMap(Item::getItemCode, Function.identity()));
        List<Item> res = new ArrayList<>();
        for (Item item : map.values()) {
            // 构建层级关系
            if (map.containsKey(item.getParentId())) {
                map.get(item.getParentId())
                        .getChildren()
                        .add(item);
            } else {
                res.add(item);
            }
        }
        return res;
    }

    /**
     * 移除多余的层
     */
    public static void removeChildren(List<Item> tree, int n) {
        // 上一层
        List<Item> pre = new ArrayList<>();
        // 当前层
        List<Item> cur = tree;
        // 遍历到保留的最后一层
        for (int i = 0; i < n; i++) {
            pre = cur;
            // 获取下一层数据
            cur = cur.stream()
                    .flatMap(item -> item.getChildren()
                            .stream())
                    .collect(Collectors.toList());
        }
        for (Item item : pre) {
            // 移除其他层
            item.setChildren(new ArrayList<>());
        }
    }

    /**
     * 计算数的总层数
     */
    public static int countLayers(List<Item> tree) {
        List<Item> cur = tree;
        int layers = 0;
        while (CollectionUtils.isNotEmpty(cur)){
            layers += 1;
            cur = cur.stream()
                    .flatMap(item -> item.getChildren()
                            .stream())
                    .collect(Collectors.toList());
        }
        return layers;
    }

    public static void preProcess(List<Item> tree, int n) {
        removeChildren(tree, n);
    }


    /**
     * 导出excel
     * @param items 导出的数据
     * @param filename 文件路径和名称
     * @param layers 导出的层数
     */
    public static void exportExcel(List<Item> items, String filename,  int layers){
        System.out.println("item size: " + items.size());
        List<Item> tree = listToTree(items);
        preProcess(tree, layers);
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(filename)
                .registerWriteHandler(new TreeWriterHandler(tree))
                .sheet("excel")
                .doWrite(new ArrayList<>());
    }

    public static void main(String[] args) {
        String json = "[{\"ItemCode\":\"972717\",\"ItemName\":\"5、后勤部\",\"ParentId\":\"24716\",\"Hierarchy\":\"0\",\"CateName\":\"管理、查看、编辑:后勤部-\"},{\"ItemCode\":\"1247424\",\"ItemName\":\"04-宿舍相关数据\",\"ParentId\":\"972717\",\"Hierarchy\":\"1\",\"CateName\":\"编辑、下载、管理:-文档管理、王晴邈、黄晶、文控中心文档管理、文档安全审计\"},{\"ItemCode\":\"1247437\",\"ItemName\":\"02-宿舍卫生检查结果\",\"ParentId\":\"1247424\",\"Hierarchy\":\"2\",\"CateName\":\"编辑、下载、管理:-文档管理、王晴邈、黄晶、文控中心文档管理、文档安全审计\"},{\"ItemCode\":\"1774234\",\"ItemName\":\"01-2022年各月宿舍安全卫生检查汇总表\",\"ParentId\":\"1247437\",\"Hierarchy\":\"3\",\"CateName\":\"编辑、下载、管理:-文档管理、王晴邈、黄晶、文控中心文档管理、文档安全审计\"},{\"ItemCode\":\"1247436\",\"ItemName\":\"01-每月住宿明细-考勤使用\",\"ParentId\":\"1247424\",\"Hierarchy\":\"2\",\"CateName\":\"编辑、下载、管理:-文档管理、王晴邈、黄晶、文控中心文档管理、文档安全审计\"},{\"ItemCode\":\"1591654\",\"ItemName\":\"01-2022年\",\"ParentId\":\"1247436\",\"Hierarchy\":\"3\",\"CateName\":\"编辑、下载、管理:-文档管理、王晴邈、黄晶、文控中心文档管理、文档安全审计\"},{\"ItemCode\":\"2126061\",\"ItemName\":\"02-2023年\",\"ParentId\":\"1247436\",\"Hierarchy\":\"3\",\"CateName\":\"编辑、下载、管理:-文档管理、王晴邈、黄晶、文控中心文档管理、文档安全审计\"},{\"ItemCode\":\"1247422\",\"ItemName\":\"02-班车相关数据\",\"ParentId\":\"972717\",\"Hierarchy\":\"1\",\"CateName\":\"编辑、下载、管理:-文档管理、王晴邈、黄晶、文控中心文档管理、文档安全审计\"},{\"ItemCode\":\"1247426\",\"ItemName\":\"01-班车晚点统计\",\"ParentId\":\"1247422\",\"Hierarchy\":\"2\",\"CateName\":\"编辑、下载、管理:-文档管理、王晴邈、黄晶、文控中心文档管理、文档安全审计\"},{\"ItemCode\":\"1247427\",\"ItemName\":\"02-班车乘坐明细\",\"ParentId\":\"1247422\",\"Hierarchy\":\"2\",\"CateName\":\"编辑、下载、管理:-文档管理、王晴邈、黄晶、文控中心文档管理、文档安全审计\"},{\"ItemCode\":\"189654\",\"ItemName\":\"2021年\",\"ParentId\":\"1247427\",\"Hierarchy\":\"3\",\"CateName\":\"编辑、下载、管理:-文档管理、王晴邈、黄晶、文控中心文档管理、文档安全审计\"},{\"ItemCode\":\"2125987\",\"ItemName\":\"2023年\",\"ParentId\":\"1247427\",\"Hierarchy\":\"3\",\"CateName\":\"编辑、下载、管理:-文档管理、王晴邈、黄晶、文控中心文档管理、文档安全审计\"},{\"ItemCode\":\"426368\",\"ItemName\":\"2022年\",\"ParentId\":\"1247427\",\"Hierarchy\":\"3\",\"CateName\":\"编辑、下载、管理:-文档管理、王晴邈、黄晶、文控中心文档管理、文档安全审计\"},{\"ItemCode\":\"189656\",\"ItemName\":\"2019年\",\"ParentId\":\"1247427\",\"Hierarchy\":\"3\",\"CateName\":\"编辑、下载、管理:-文档管理、王晴邈、黄晶、文控中心文档管理、文档安全审计\"},{\"ItemCode\":\"189655\",\"ItemName\":\"2020年\",\"ParentId\":\"1247427\",\"Hierarchy\":\"3\",\"CateName\":\"编辑、下载、管理:-文档管理、王晴邈、黄晶、文控中心文档管理、文档安全审计\"},{\"ItemCode\":\"1247423\",\"ItemName\":\"03-客餐及下午茶数据\",\"ParentId\":\"972717\",\"Hierarchy\":\"1\",\"CateName\":\"编辑、下载、管理:-文档管理、王晴邈、黄晶、文控中心文档管理、文档安全审计\"},{\"ItemCode\":\"1247433\",\"ItemName\":\"02-下午茶人数更新\",\"ParentId\":\"1247423\",\"Hierarchy\":\"2\",\"CateName\":\"编辑、下载、管理:-文档管理、王晴邈、黄晶、文控中心文档管理、文档安全审计\"},{\"ItemCode\":\"1247429\",\"ItemName\":\"01-客餐就餐接待明细\",\"ParentId\":\"1247423\",\"Hierarchy\":\"2\",\"CateName\":\"编辑、下载、管理:-文档管理、王晴邈、黄晶、文控中心文档管理、文档安全审计\"},{\"ItemCode\":\"1688818\",\"ItemName\":\"03食材验收结果通知单\",\"ParentId\":\"1247423\",\"Hierarchy\":\"2\",\"CateName\":\"编辑、下载、管理:-文档管理、王晴邈、黄晶、文控中心文档管理、文档安全审计\"},{\"ItemCode\":\"2071943\",\"ItemName\":\"2023年食材验收结果通知单\",\"ParentId\":\"1688818\",\"Hierarchy\":\"3\",\"CateName\":\"编辑、下载、管理:-文档管理、王晴邈、黄晶、文控中心文档管理、文档安全审计\"},{\"ItemCode\":\"1762357\",\"ItemName\":\"2022年食材验收结果通知单\",\"ParentId\":\"1688818\",\"Hierarchy\":\"3\",\"CateName\":\"编辑、下载、管理:-文档管理、王晴邈、黄晶、文控中心文档管理、文档安全审计\"},{\"ItemCode\":\"1247421\",\"ItemName\":\"01-后勤部工作分工及对接人\",\"ParentId\":\"972717\",\"Hierarchy\":\"1\",\"CateName\":\"编辑、管理、查看:柯进红、姚春瑜、党玉珠、张倩、后勤部-\"}]";
        List<Item> items = JSON.parseArray(json, Item.class);
        exportExcel(items, "./test.xls", 5);
        System.out.println("--------");
    }
}
