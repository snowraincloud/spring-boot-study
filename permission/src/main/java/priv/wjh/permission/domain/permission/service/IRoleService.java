package priv.wjh.permission.domain.permission.service;

import priv.wjh.permission.domain.permission.po.RolePojo;
import priv.wjh.permission.domain.permission.po.UserPojo;

import java.util.List;

/**
 * @author wangjunhao
 */
public interface IRoleService {
    List<RolePojo> findByUser(UserPojo user);

    List<RolePojo> find(RolePojo rolePojo);

    RolePojo add(RolePojo rolePojo);

    int update(RolePojo rolePojo);
}
