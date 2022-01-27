package priv.wjh.permission.domain.permission.dao;

import org.apache.ibatis.annotations.Mapper;
import priv.wjh.permission.api.ao.RoleRequestAo;
import priv.wjh.permission.domain.permission.po.Role;

import java.util.List;

@Mapper
public interface RoleMapper{

    List<Role> selectAll();

    int updateStatus(RoleRequestAo roleRequestAo);

    List<Role> selectRole(RoleRequestAo roleRequestAo);

    int insertRole(RoleRequestAo roleRequestAo);

    int updateRole(RoleRequestAo roleRequestAo);
}