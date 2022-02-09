package priv.wjh.permission.api.vo;

import priv.wjh.permission.domain.permission.po.PermissionPojo;
import priv.wjh.permission.domain.permission.po.RolePojo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
@Data
@NoArgsConstructor
public class AuthVo {
    private Long id;
    private String password;
    private String token;
    private List<RolePojo> rolePojos;
    private List<PermissionPojo> permissionPojos;
}
