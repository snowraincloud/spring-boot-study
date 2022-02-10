package priv.wjh.permission.domain.permission.dao;

import org.apache.ibatis.annotations.Mapper;
import priv.wjh.permission.api.ao.LoginAo;
import priv.wjh.permission.api.ao.UpdatePasswordAo;
import priv.wjh.permission.domain.permission.po.PermissionPojo;
import priv.wjh.permission.domain.permission.po.UserPojo;

import java.util.List;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
@Mapper
public interface AuthMapper {
    UserPojo findUser(LoginAo ao);

    int updatePassword(UpdatePasswordAo ao);

    List<PermissionPojo> findEnablePermission();

    List<Long> findRoleByUserId(Long id);

    List<PermissionPojo> findPermissionByRoleIds(List<Long> roles);

    List<PermissionPojo> findParentPermission(List<Long> ids);
}
