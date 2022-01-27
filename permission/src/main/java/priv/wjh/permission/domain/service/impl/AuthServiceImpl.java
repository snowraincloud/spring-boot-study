package priv.wjh.permission.domain.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import priv.wjh.permission.domain.permission.dao.PermissionMapper;
import priv.wjh.permission.domain.permission.dao.UserMapper;
import priv.wjh.permission.domain.permission.dao.UserRoleRelationMapper;
import priv.wjh.permission.domain.permission.po.Permission;
import priv.wjh.permission.domain.permission.po.Role;
import priv.wjh.permission.domain.permission.po.User;
import priv.wjh.permission.domain.service.IAuthService;
import priv.wjh.permission.infrastructure.jwt.JwtToken;
import priv.wjh.permission.infrastructure.utils.CacheUtil;

import java.util.*;

@Slf4j
@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private JwtToken jwtToken;
    @Autowired
    private UserRoleRelationMapper userRoleRelationMapper;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private CacheUtil cacheUtil;

    @Override
    public boolean checkCache(String token){
        try {
            Optional<String> optionalS =  cacheUtil.get(token);
            if (optionalS.isPresent()){
                cacheUtil.put(token, getId(token), 10 * 60 * 6);
                return true;
            }
            return false;
        }catch (Exception e){
            log.info("检验缓存失败", e);
            return false;
        }
    }


    @Override
    public Long getId(String token){
        return Long.valueOf(jwtToken.getUserInfoFromToken(token));
    }


    @Override
    public List<Permission> getPermission(Long id) {
        if (id.equals(1L)){
            List<Permission> tmp = permissionMapper.selectAll();
            List<Permission> permissions = new ArrayList<>();
            Set<Long> ids = new HashSet<>();
            for (Permission permission : tmp){
                if (permission.getType() == 0) {
                    permissions.add(permission);
                    ids.add(permission.getId());
                }
            }
            for (Permission permission : tmp){
                if (permission.getType() == 1 && ids.contains(permission.getPid())) {
                    permissions.add(permission);
                }
            }
            return permissions;
        }else {
            User user = userMapper.selectById(id);
            if (user.getStatus().equals(0)){
                return new ArrayList<>();
            }
            List<Role> roles = userRoleRelationMapper.selectByUserId(id);

            return permissionMapper.selectEnableByRoles(roles);
        }
    }

    @Override
    public List<Permission> getPermission(String token) {
        Long id = Long.valueOf(jwtToken.getUserInfoFromToken(token));
        return getPermission(id);
    }
}
