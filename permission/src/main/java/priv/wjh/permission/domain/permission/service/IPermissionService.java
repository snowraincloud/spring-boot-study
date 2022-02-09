package priv.wjh.permission.domain.permission.service;

import priv.wjh.permission.domain.permission.po.PermissionPojo;
import priv.wjh.permission.domain.permission.po.RolePojo;

import java.util.List;

public interface IPermissionService {

    List<PermissionPojo> findByRole(RolePojo rolePojo);

    List<PermissionPojo> find(PermissionPojo permissionPojo);

    PermissionPojo add(PermissionPojo permissionPojo);

    PermissionPojo update(PermissionPojo permissionPojo);
}
