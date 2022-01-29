package priv.wjh.permission.domain.permission.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import priv.wjh.permission.api.ao.PermissionAo;
import priv.wjh.permission.domain.permission.dao.PermissionMapper;
import priv.wjh.permission.domain.permission.dao.RolePermissionRelationMapper;
import priv.wjh.permission.domain.permission.po.Permission;
import priv.wjh.permission.domain.permission.service.ILogoutUserService;
import priv.wjh.permission.domain.permission.service.PermissionService;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RolePermissionRelationMapper rolePermissionRelationMapper;

    @Autowired
    private ILogoutUserService logoutUserService;

    @Override
    public List<Permission> selectPermission(PermissionAo permissionRequestAo) {
        return permissionMapper.selectPermission(permissionRequestAo);
    }

    @Override
    public Integer setStatus(PermissionAo permissionRequestAo) {
        int i = permissionMapper.updateStatus(permissionRequestAo);
        if (i != 0 && permissionRequestAo.getStatus() == 0){
            logoutUserService.logoutUserByPermissionId(permissionRequestAo.getId());
            permissionMapper.updateStatusByPid(permissionRequestAo);
        }
        return i;
    }

    @Override
    public Integer insertPermission(PermissionAo permissionRequestAo) {
        int i = permissionMapper.insertPermission(permissionRequestAo);
        return i;
    }

    @Override
    public List<Permission> getPermissions() {
        List<Permission> permissions = permissionMapper.selectByType((byte)0);
        return permissions;
    }

    @Override
    public Integer updatePermission(PermissionAo permissionRequestAo) {
        if(null == permissionRequestAo.getPid() || (permissionRequestAo.getType() != null && permissionRequestAo.getType() == 0)){
            permissionRequestAo.setPid(0L);
        }
        int i = permissionMapper.updatePermission(permissionRequestAo);
        if(0 == i){
            throw new RuntimeException("修改权限失败");
        }
        if (permissionRequestAo.getStatus() == 0){
            permissionMapper.updateStatusByPid(permissionRequestAo);
            logoutUserService.logoutUserByPermissionId(permissionRequestAo.getId());
        }
        return i;
    }
}
