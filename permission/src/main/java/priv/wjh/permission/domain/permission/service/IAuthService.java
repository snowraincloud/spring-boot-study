package priv.wjh.permission.domain.permission.service;

import priv.wjh.permission.api.ao.LoginAo;
import priv.wjh.permission.api.ao.UpdatePasswordAo;
import priv.wjh.permission.api.vo.LoginVo;
import priv.wjh.permission.api.vo.ValidateCodeVo;
import priv.wjh.permission.domain.permission.po.PermissionPojo;

import java.util.List;

/**
 * @author wangjunhao
 */
public interface IAuthService {

    LoginVo login(LoginAo ao);

    Integer updatePassword(UpdatePasswordAo ao);

    ValidateCodeVo getValidateCode();

    void logout();

    List<PermissionPojo> getPermission();

    List<PermissionPojo> getTreePermission();
}
