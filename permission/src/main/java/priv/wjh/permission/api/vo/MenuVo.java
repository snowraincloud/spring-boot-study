package priv.wjh.permission.api.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import priv.wjh.permission.domain.permission.po.PermissionPojo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangjunhao
 */
@Data
@NoArgsConstructor
public class MenuVo {
    private Long id;
    private String resourcesName;
    private String resourcesUrl;
    private String imgName;
    private int type;
    private List<MenuVo> children = new ArrayList<>();


    public static MenuVo fromPermission(PermissionPojo permissionPojo){
        MenuVo menuVo = new MenuVo();
        menuVo.id = permissionPojo.getId();
        menuVo.resourcesName = permissionPojo.getName();
        menuVo.resourcesUrl = permissionPojo.getUri();
        menuVo.type = permissionPojo.getType();
        return menuVo;
    }
}