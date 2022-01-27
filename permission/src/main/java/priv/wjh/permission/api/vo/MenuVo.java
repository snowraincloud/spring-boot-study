package priv.wjh.permission.api.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class MenuVo {
    private int id;
    private String resourcesName;
    private String resourcesUrl;
    private String imgName;
    private int type;
    private Set<Long>  ids = new HashSet<>();
    private List<MenuVo> children;
    }