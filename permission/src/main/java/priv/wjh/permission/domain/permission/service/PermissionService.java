package priv.wjh.permission.domain.permission.service;

import priv.wjh.permission.api.ao.PermissionAo;
import priv.wjh.permission.domain.permission.po.Permission;

import java.util.List;

public interface PermissionService {
    List<Permission> selectPermission(PermissionAo permissionRequestAo);

    Integer setStatus(PermissionAo permissionRequestAo);

    Integer insertPermission(PermissionAo permissionRequestAo);

    List<Permission> getPermissions();

    Integer updatePermission(PermissionAo permissionRequestAo);
}
