package priv.wjh.permission.domain.permission.dao;

import org.apache.ibatis.annotations.Mapper;
import priv.wjh.permission.api.ao.PermissionRequestAo;
import priv.wjh.permission.domain.permission.po.Permission;
import priv.wjh.permission.domain.permission.po.Role;

import java.util.List;


@Mapper
public interface PermissionMapper{

    List<Permission> selectAll();

    List<Permission> selectPermission(PermissionRequestAo permissionRequestAo);

    List<Permission> selectByType(Byte type);

    int updateStatus(PermissionRequestAo permissionRequestAo);

    int updateStatusByPid(PermissionRequestAo permissionRequestAo);

    int insertPermission(PermissionRequestAo permissionRequestAo);

    int updatePermission(PermissionRequestAo permissionRequestAo);

    List<Permission> selectByIds(List<Long> ids);

    List<Permission> selectEnableByRoles(List<Role> roles);
}