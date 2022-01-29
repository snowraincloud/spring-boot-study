package priv.wjh.permission.domain.permission.service;

import priv.wjh.permission.api.ao.PermissionRequestAo;
import priv.wjh.permission.domain.permission.po.Permission;

import java.util.List;

public interface PermissionService {
    List<Permission> selectPermission(PermissionRequestAo permissionRequestAo);

    Integer setStatus(PermissionRequestAo permissionRequestAo);

    Integer insertPermission(PermissionRequestAo permissionRequestAo);

    List<Permission> getPermissions();

    Integer updatePermission(PermissionRequestAo permissionRequestAo);
}
