package priv.wjh.permission.domain.permission.service;

import priv.wjh.permission.api.ao.RoleRequestAo;
import priv.wjh.permission.api.vo.PesVo;
import priv.wjh.permission.domain.permission.po.Role;

import java.util.List;

public interface RoleService {
    List<Role> selectRole(RoleRequestAo roleRequestAo);

    Integer setStatus(RoleRequestAo roleRequestAo);

    List<PesVo> getPermissions(RoleRequestAo roleRequestAo);

//    Integer setPermissions(RoleRequestAo roleRequestAo);

    Integer insertRole(RoleRequestAo roleRequestAo);

    Integer updateRole(RoleRequestAo roleRequestAo);
}
