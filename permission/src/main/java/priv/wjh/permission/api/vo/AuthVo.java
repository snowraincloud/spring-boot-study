package priv.wjh.permission.api.vo;

import priv.wjh.permission.domain.permission.po.Permission;
import priv.wjh.permission.domain.permission.po.Role;
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
    private List<Role> roles;
    private List<Permission> permissions;
}
