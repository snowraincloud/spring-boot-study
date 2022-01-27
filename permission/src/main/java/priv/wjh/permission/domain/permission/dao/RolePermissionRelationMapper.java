package priv.wjh.permission.domain.permission.dao;

import org.apache.ibatis.annotations.Mapper;
import priv.wjh.permission.api.ao.RoleRequestAo;
import priv.wjh.permission.domain.permission.po.Permission;

import java.util.List;

@Mapper
public interface RolePermissionRelationMapper{
    int deleteByRoleId(Long id);

    int insertSelective(RoleRequestAo roleRequestAo);

    List<Permission> selectByRoleId(Long id);

    void deleteByPermissionId(Long id);

    List<Long> selectUserIdByPermissionId(Long permissionId);
}