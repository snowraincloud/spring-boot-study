package priv.wjh.permission.domain.permission.dao;

import org.apache.ibatis.annotations.Mapper;
import priv.wjh.permission.api.ao.RoleAo;
import priv.wjh.permission.domain.permission.po.Role;

import java.util.List;

@Mapper
public interface RoleMapper{

    List<Role> selectAll();

    int updateStatus(RoleAo roleRequestAo);

    List<Role> selectRole(RoleAo roleRequestAo);

    int insertRole(RoleAo roleRequestAo);

    int updateRole(RoleAo roleRequestAo);
}