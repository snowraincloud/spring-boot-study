package priv.wjh.permission.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import priv.wjh.permission.api.ao.PermissionAo;
import priv.wjh.permission.api.ao.RoleAo;
import priv.wjh.permission.api.ao.UserAo;
import priv.wjh.permission.domain.permission.po.PermissionPojo;
import priv.wjh.permission.domain.permission.po.RolePojo;
import priv.wjh.permission.domain.permission.po.UserPojo;
import priv.wjh.permission.domain.permission.service.IPermissionService;
import priv.wjh.permission.domain.permission.service.IRoleService;
import priv.wjh.permission.domain.permission.service.IUserService;

import java.util.List;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
@Component
@RequiredArgsConstructor
public class PermissionApplicationService {
    private final IPermissionService permissionService;
    private final IRoleService roleService;
    private final IUserService userService;


    public List<UserPojo> findUser(UserAo ao) {
        UserPojo user = new UserPojo();
        user.setId(ao.getId());
        user.setStatus(ao.getStatus());
        user.setUsername(ao.getUsername());
        user.setCreateTime(ao.getCreateTime());
        user.setLoginTime(ao.getLoginTime());

        return userService.find(user);
    }

    public List<RolePojo> findRoleByUser(UserAo ao) {
        UserPojo user = new UserPojo();
        user.setId(ao.getId());
        return roleService.findByUser(user);
    }

    public UserPojo addUser(UserAo ao) {
        UserPojo user = new UserPojo();
        user.setUsername(ao.getUsername());
        user.setPassword(ao.getPassword());
        user.setStatus(ao.getStatus());
        user = userService.add(user);
        return user;
    }

    public int updateUser(UserAo ao) {
        UserPojo user = new UserPojo();
        user.setId(ao.getId());
        user.setStatus(ao.getStatus());
        return userService.update(user);
    }

    public List<RolePojo> findRole(RoleAo ao) {
        RolePojo rolePojo = new RolePojo();
        rolePojo.setId(ao.getId());
        rolePojo.setName(ao.getName());
        rolePojo.setCreateTime(ao.getCreateTime());
        rolePojo.setStatus(ao.getStatus());
        return roleService.find(rolePojo);
    }

    public List<PermissionPojo> findPermissionByRole(RoleAo ao) {
        RolePojo rolePojo = new RolePojo();
        rolePojo.setId(ao.getId());
        return permissionService.findByRole(rolePojo);
    }

    public RolePojo addRole(RoleAo ao) {
        RolePojo rolePojo = new RolePojo();
        rolePojo.setName(ao.getName());
        rolePojo.setDescription(ao.getDescription());
        rolePojo.setStatus(ao.getStatus());
        rolePojo = roleService.add(rolePojo);

        return rolePojo;
    }

    public int updateRole(RoleAo ao) {
        RolePojo rolePojo = new RolePojo();
        rolePojo.setId(ao.getId());
        rolePojo.setName(ao.getName());
        rolePojo.setDescription(ao.getDescription());
        rolePojo.setStatus(ao.getStatus());
        return roleService.update(rolePojo);
    }

    public List<PermissionPojo> findPermission(PermissionAo ao) {
        PermissionPojo permissionPojo = new PermissionPojo();
        permissionPojo.setId(ao.getId());
        permissionPojo.setPid(ao.getPid());
        permissionPojo.setName(ao.getName());
        permissionPojo.setValue(ao.getValue());
        permissionPojo.setType(ao.getType());
        permissionPojo.setUri(ao.getUri());
        permissionPojo.setStatus(ao.getStatus());
        permissionPojo.setCreateTime(ao.getCreateTime());
        return permissionService.find(permissionPojo);
    }

    public PermissionPojo addPermission(PermissionAo ao) {
        PermissionPojo permissionPojo = new PermissionPojo();
        permissionPojo.setPid(ao.getPid());
        permissionPojo.setName(ao.getName());
        permissionPojo.setValue(ao.getValue());
        permissionPojo.setType(ao.getType());
        permissionPojo.setUri(ao.getUri());
        permissionPojo.setStatus(ao.getStatus());
        return permissionService.add(permissionPojo);
    }

    public PermissionPojo updatePermission(PermissionAo ao) {
        PermissionPojo permissionPojo = new PermissionPojo();
        permissionPojo.setPid(ao.getPid());
        permissionPojo.setName(ao.getName());
        permissionPojo.setValue(ao.getValue());
        permissionPojo.setType(ao.getType());
        permissionPojo.setUri(ao.getUri());
        permissionPojo.setStatus(ao.getStatus());
        return permissionService.update(permissionPojo);
    }
}
