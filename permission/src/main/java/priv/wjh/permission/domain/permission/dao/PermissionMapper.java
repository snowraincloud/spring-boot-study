package priv.wjh.permission.domain.permission.dao;

import org.apache.ibatis.annotations.Mapper;
import priv.wjh.permission.api.ao.PermissionAo;
import priv.wjh.permission.domain.permission.po.Permission;
import priv.wjh.permission.domain.permission.po.Role;

import java.util.List;


@Mapper
public interface PermissionMapper{

    List<Permission> selectAll();

    List<Permission> selectPermission(PermissionAo permissionRequestAo);

    List<Permission> selectByType(Byte type);

    int updateStatus(PermissionAo permissionRequestAo);

    int updateStatusByPid(PermissionAo permissionRequestAo);

    int insertPermission(PermissionAo permissionRequestAo);

    int updatePermission(PermissionAo permissionRequestAo);

    List<Permission> selectByIds(List<Long> ids);

    List<Permission> selectEnableByRoles(List<Role> roles);
}