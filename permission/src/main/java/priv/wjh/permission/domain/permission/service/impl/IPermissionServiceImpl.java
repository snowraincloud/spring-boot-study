package priv.wjh.permission.domain.permission.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import priv.wjh.permission.domain.permission.dao.PermissionMapper;
import priv.wjh.permission.domain.permission.po.PermissionPojo;
import priv.wjh.permission.domain.permission.po.RolePojo;
import priv.wjh.permission.domain.permission.service.IPermissionService;

import java.util.List;

/**
 *
 * @author wangjunhao
 */
@Service
@RequiredArgsConstructor
public class IPermissionServiceImpl implements IPermissionService {

    private final PermissionMapper permissionMapper;


    @Override
    public List<PermissionPojo> findByRole(RolePojo rolePojo) {
        return permissionMapper.findByRole(rolePojo);
    }

    @Override
    public List<PermissionPojo> find(PermissionPojo permissionPojo) {
        return permissionMapper.find(permissionPojo);
    }

    @Override
    public PermissionPojo add(PermissionPojo permissionPojo) {
        permissionMapper.add(permissionPojo);
        return permissionPojo;
    }

    @Override
    public PermissionPojo update(PermissionPojo permissionPojo) {
        permissionMapper.update(permissionPojo);
        return permissionPojo;
    }
}
