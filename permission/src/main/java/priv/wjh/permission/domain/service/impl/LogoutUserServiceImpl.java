package priv.wjh.permission.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import priv.wjh.permission.domain.permission.dao.RolePermissionRelationMapper;
import priv.wjh.permission.domain.permission.dao.UserRoleRelationMapper;
import priv.wjh.permission.domain.service.ILogoutUserService;
import priv.wjh.permission.infrastructure.utils.CacheUtil;

@Service
public class LogoutUserServiceImpl implements ILogoutUserService {

    @Autowired
    private CacheUtil cacheUtil;
    @Autowired
    private UserRoleRelationMapper userRoleRelationMapper;

    @Autowired
    private RolePermissionRelationMapper rolePermissionRelationMapper;


    private void remove(long id){
        cacheUtil.remove(id);
    }


    @Override
    public void logoutUserByPermissionId(Long permissionId) {
//        List<Long> ids = rolePermissionRelationMapper.selectUserIdByPermissionId(permissionId);
//        for (Long id : ids){
//            if (id != null){
//                remove(id);
//            }
//        }
    }

    @Override
    public void logoutByRoleId(Long roleId) {
//        List<Long> ids = userRoleRelationMapper.selectUserIdByRoleId(roleId);
//        for (Long id : ids){
//            if (id != null){
//                remove(id);
//            }
//        }
    }

    @Override
    public void logoutByUserId(Long id) {
//        remove(id);
    }

}
