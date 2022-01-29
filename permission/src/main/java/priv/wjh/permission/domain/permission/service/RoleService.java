package priv.wjh.permission.domain.permission.service;

import priv.wjh.permission.api.ao.RoleAo;
import priv.wjh.permission.api.vo.PesVo;
import priv.wjh.permission.domain.permission.po.Role;

import java.util.List;

public interface RoleService {
    List<Role> selectRole(RoleAo roleRequestAo);

    Integer setStatus(RoleAo roleRequestAo);

    List<PesVo> getPermissions(RoleAo roleRequestAo);

//    Integer setPermissions(RoleRequestAo roleRequestAo);

    Integer insertRole(RoleAo roleRequestAo);

    Integer updateRole(RoleAo roleRequestAo);
}
