package priv.wjh.study.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson2.JSON;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

public class EasyExcelTest {
    /**
     * 不创建对象的写
     */
    @Test
    public void noModelWrite() {
        // 写法1
        String fileName = "./test.xls";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName).registerWriteHandler(new MyMergeStrategy()).head(head()).sheet("模板").doWrite(dataList());
    }

    private List<List<String>> head() {
        List<List<String>> list = ListUtils.newArrayList();
        List<String> head0 = ListUtils.newArrayList();
        head0.add("字符串" + System.currentTimeMillis());
        List<String> head1 = ListUtils.newArrayList();
        head1.add("数字" + System.currentTimeMillis());
        List<String> head2 = ListUtils.newArrayList();
        head2.add("日期" + System.currentTimeMillis());
        head2.add("1111");

        List<String> head3 = ListUtils.newArrayList();
        head3.add("test" + System.currentTimeMillis());
        list.add(head0);
        list.add(head1);
        list.add(head2);
        list.add(head3);
//        list.add(List.of("test"));
        return list;
    }

    private List<List<Object>> dataList() {
        String json = "{\"ParentId\":\"54366\",\"ItemCode\":\"54490\",\"children\":[{\"ParentId\":\"54490\",\"ItemCode\":\"54492\",\"children\":[{\"ParentId\":\"54492\",\"ItemCode\":\"54496\",\"ItemName\":\"2018年IT设备调动确认表\",\"CateName\":\"编辑管理:厨邦信息技术部、文档安全审计、信息技术部、信息技术部-文档管理、\"},{\"ParentId\":\"54492\",\"ItemCode\":\"54495\",\"ItemName\":\"2019年IT设备调动确认表\",\"CateName\":\"编辑管理:厨邦信息技术部、文档安全审计、信息技术部、信息技术部-文档管理、\"},{\"ParentId\":\"54492\",\"ItemCode\":\"54493\",\"ItemName\":\"IT类设备出入登记表扫描件\",\"CateName\":\"编辑管理:厨邦信息技术部、文档安全审计、信息技术部、信息技术部-文档管理、\"},{\"ParentId\":\"54492\",\"ItemCode\":\"54494\",\"ItemName\":\"2020年IT设备调动确认表\",\"CateName\":\"编辑管理:厨邦信息技术部、文档安全审计、信息技术部、信息技术部-文档管理、\"}],\"ItemName\":\"1、设备管理\",\"CateName\":\"编辑管理:厨邦信息技术部、文档安全审计、信息技术部、信息技术部-文档管理、\"},{\"ParentId\":\"54490\",\"ItemCode\":\"54491\",\"ItemName\":\"2、物品管理\",\"CateName\":\"编辑管理:厨邦信息技术部、文档安全审计、信息技术部、信息技术部-文档管理、\"}],\"ItemName\":\"10、设备间管理\",\"CateName\":\"编辑管理:厨邦信息技术部、文档安全审计、信息技术部、信息技术部-文档管理、\"}";
        Item item = JSON.parseObject(json, Item.class);


        List<List<Object>> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            List<Object> data = ListUtils.newArrayList();
            data.add("字符串" + i);
            data.add(0.56);
            data.add(0.56);
            data.add(0.56);
            data.add(0.56);
            data.add(0.56);
            data.add(0.56);
            data.add(new Date());
            list.add(data);
        }
        return list;
    }
}
