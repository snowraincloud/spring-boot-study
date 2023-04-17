package priv.wjh.study.easyexcel;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Item {

    private String ItemCode;
    private String ItemName;
    private String Hierarchy;
    private String CateName;
    private String ParentId;
    private List<Item> children = new ArrayList<>();
}
