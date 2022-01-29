package priv.wjh.permission.domain.permission.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import priv.wjh.permission.api.ao.RoleAo;
import priv.wjh.permission.domain.permission.dao.PermissionMapper;
import priv.wjh.permission.domain.permission.dao.RoleMapper;
import priv.wjh.permission.domain.permission.dao.RolePermissionRelationMapper;
import priv.wjh.permission.domain.permission.dao.UserRoleRelationMapper;
import priv.wjh.permission.domain.permission.po.Permission;
import priv.wjh.permission.domain.permission.po.Role;
import priv.wjh.permission.domain.permission.service.ILogoutUserService;
import priv.wjh.permission.domain.permission.service.RoleService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RolePermissionRelationMapper rolePermissionRelationMapper;

    @Autowired
    private UserRoleRelationMapper rserRoleRelationMapper;

    @Autowired
    private PermissionMapper permissionMapper;


    @Autowired
    private ILogoutUserService logoutUserService;

    @Override
    public List<Role> selectRole(RoleAo roleRequestAo) {
        return roleMapper.selectRole(roleRequestAo);
    }

    @Override
    public Integer setStatus(RoleAo roleRequestAo) {
        int i = roleMapper.updateStatus(roleRequestAo);
        if (i != 0 && roleRequestAo.getStatus() == 0){
            logoutUserService.logoutByRoleId(roleRequestAo.getId());
        }
        return i;
    }

    @Override
    public List getPermissions(RoleAo roleRequestAo) {
        if (roleRequestAo.getId() == null){
            List<Permission> permissions = permissionMapper.selectAll();
//            return PermissionUtil.getPesVo(permissions);
            return null;
        }
        List<Permission> permissionsList = rolePermissionRelationMapper.selectByRoleId(roleRequestAo.getId());
        permissionsList = permissionsList.stream()
                .filter(permission -> permission.getPid() != 0)
                .collect(Collectors.toList());
        return permissionsList;
    }

//    @Override
//    @Transactional(rollbackFor = RuntimeException.class)
//    public Integer setPermissions(RoleRequestAo roleRequestAo) {
//        int i = rolePermissionRelationMapper.deleteByRoleId(roleRequestAo.getId());
//        if(i == 0){
//            throw new RuntimeException();
//        }
//        if(null == roleRequestAo.getPermissionId()){
//            return 0;
//        }
//        int j = rolePermissionRelationMapper.insertSelective(roleRequestAo);
//        if(j == 0){
//            throw new RuntimeException();
//        }
//        int z = roleMapper.updateRole(roleRequestAo);
//        if(z == 0){
//            throw new RuntimeException();
//        }
//        return z;
//    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Integer insertRole(RoleAo roleRequestAo) {
        int i = roleMapper.insertRole(roleRequestAo);
        if(i == 0){
            throw new RuntimeException();
        }
        if(roleRequestAo.getPermissionId() == null || roleRequestAo.getPermissionId().size() == 0){
            return 0;
        }

        List<Permission> permissions = permissionMapper.selectByIds(roleRequestAo.getPermissionId());

        Set<Long> ids = new HashSet<>();
        for (Permission permission : permissions){
            ids.add(permission.getId());
            if (permission.getPid() != 0){
                ids.add(permission.getPid());
            }
        }
        roleRequestAo.setPermissionId(new ArrayList<>(ids));
        rolePermissionRelationMapper.insertSelective(roleRequestAo);
        return i;
    }

    @Override
    public Integer updateRole(RoleAo roleRequestAo) {
//        if (roleRequestAo.getId().equals(1L)){
//            roleRequestAo.setStatus((byte) 1);
//        }
        int i = roleMapper.updateRole(roleRequestAo);
        int j = rolePermissionRelationMapper.deleteByRoleId(roleRequestAo.getId());
        if (roleRequestAo.getPermissionId().size() != 0){
            List<Permission> permissions = permissionMapper.selectByIds(roleRequestAo.getPermissionId());

            Set<Long> ids = new HashSet<>();
            for (Permission permission : permissions){
                ids.add(permission.getId());
                if (permission.getPid() != 0){
                    ids.add(permission.getPid());
                }
            }
            roleRequestAo.setPermissionId(new ArrayList<>(ids));
            rolePermissionRelationMapper.insertSelective(roleRequestAo);
        }

        return i;
    }
}
