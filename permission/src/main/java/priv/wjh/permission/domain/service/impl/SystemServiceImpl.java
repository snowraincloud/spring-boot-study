package priv.wjh.permission.domain.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import priv.wjh.permission.api.ao.LoginAo;
import priv.wjh.permission.api.ao.UpdatePasswordAo;
import priv.wjh.permission.api.vo.LoginVo;
import priv.wjh.permission.api.vo.MenuVo;
import priv.wjh.permission.domain.permission.dao.PermissionMapper;
import priv.wjh.permission.domain.permission.dao.UserMapper;
import priv.wjh.permission.domain.permission.dao.UserRoleRelationMapper;
import priv.wjh.permission.domain.permission.po.Permission;
import priv.wjh.permission.domain.permission.po.User;
import priv.wjh.permission.domain.service.IAuthService;
import priv.wjh.permission.domain.service.ISystemService;
import priv.wjh.permission.infrastructure.enums.LoginRspEnum;
import priv.wjh.permission.infrastructure.enums.UpdatePasswordRspEnum;
import priv.wjh.permission.infrastructure.exception.PermissionException;
import priv.wjh.permission.infrastructure.jwt.JwtToken;
import priv.wjh.permission.infrastructure.utils.CacheUtil;
import priv.wjh.permission.infrastructure.utils.ValidateCode;

import java.io.IOException;
import java.util.*;

@Service
public class SystemServiceImpl implements ISystemService {
    private static final Logger logger = LoggerFactory.getLogger(SystemServiceImpl.class);
    @Autowired
    private CacheUtil cacheUtil;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtToken jwtToken;
    @Autowired
    private UserRoleRelationMapper userRoleRelationMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private IAuthService authService;

    @Override
    public LoginVo login(LoginAo loginAo) throws JsonProcessingException {
        String code = cacheUtil.get(loginAo.getKeyCode())
                .orElseThrow(() -> new PermissionException(LoginRspEnum.VALIDATE_CODE_EXPIRED));
        if (!code.equals(loginAo.getAuthCode())) {
            throw new PermissionException(LoginRspEnum.VALIDATE_CODE_EXPIRED);
        }

        User user = userMapper.selectByUsername(loginAo.getLoginName());
        if (user == null || !user.getPassword().equals(loginAo.getPassword())) {
            throw new PermissionException(LoginRspEnum.PASSWORD_ERROR);
        }
        user.setLoginTime(new Date());
        int res = userMapper.updateLoginTimeByLogin(user);
        if (res == 0) {
            throw new PermissionException(LoginRspEnum.PASSWORD_ERROR);
        }
        LoginVo loginVo = new LoginVo();
        loginVo.setLoginTime(user.getLoginTime());
        loginVo.setLoginName(user.getUsername());
        loginVo.setUserName(user.getUsername());
        loginVo.setId(user.getId());
        String token = jwtToken.createToken(String.valueOf(user.getId()));
        loginVo.setToken(token);
//
//        AuthVo authVo = new AuthVo();
//        authVo.setId(user.getId());
//        authVo.setToken(token);
//        authVo.setPassword(loginAo.getPassword());
//        if (authVo.getId().equals(1L)){
//            List<Permission> tmp = permissionMapper.selectAll();
//            List<Permission> permissions = new ArrayList<>();
//            Set<Long> ids = new HashSet<>();
//            for (Permission permission : tmp){
//                if (permission.getType() == 0) {
//                    permissions.add(permission);
//                    ids.add(permission.getId());
//                }
//            }
//            for (Permission permission : tmp){
//                if (permission.getType() == 1 && ids.contains(permission.getPid())) {
//                    permissions.add(permission);
//                }
//            }
//            authVo.setPermissions(permissions);
//        }else {
//            authVo.setRoles(userRoleRelationMapper.selectByUserId(user.getId()));
//            authVo.setPermissions(userRoleRelationMapper.selectPermissionByUserId(user.getId()));
//        }
//        cacheUtil.put(String.valueOf(user.getId()), authVo, 60 * 10 * 6);
//        cacheUtil.increment(user.getId());
        cacheUtil.put(token, user.getId(), 60 * 10);
//        RedisUtil.setex(token, String.valueOf(user.getId()), 60 * 10 * 1000);
        return loginVo;
    }

    @Override
    public void logout(String token) {
//        try {
//            cacheUtil.remove(token);
//        } catch (Exception e) {
//
//        }

//        String userInfoFromToken = jwtToken.getUserInfoFromToken(token);
//        try {
//            if(null != userInfoFromToken){
//                long key = Long.parseLong(userInfoFromToken);
//                Integer count = cacheUtil.getCount(key);
//                if (count.equals(1)){
//                    cacheUtil.remove(userInfoFromToken);
//                    cacheUtil.removeKey(key);
//                }else {
//                    cacheUtil.decrement(key);
//                }
//            }
//        }catch (Exception e){
//            logger.info("登出异常", e);
////            throw new PermissionException(FailRspEnum.RE_LOGIN);
//        }

    }

    @Override
    public Optional<com.wanshun.console.permission.vo.ValidateCodeVo> getValidateCode() throws JsonProcessingException {
        ValidateCode valImage = new ValidateCode();
        com.wanshun.console.permission.vo.ValidateCodeVo vo = new com.wanshun.console.permission.vo.ValidateCodeVo();
        try {
            vo.setAuthCode(valImage.getBase64());
        } catch (IOException e) {
            logger.warn("生成验证码失败", e);
            return Optional.empty();
        }
        String uuid = UUID.randomUUID().toString().replace("-", "");
        vo.setKeyCode(uuid);
        cacheUtil.put(uuid, valImage.getCode(), 60);
        return Optional.of(vo);
    }

    @Override
    public Integer updatePassword(UpdatePasswordAo updatePasswordAo) {
        if (updatePasswordAo.getNewPassword().equals(updatePasswordAo.getOldPassword())) {
            throw new PermissionException(UpdatePasswordRspEnum.NOT_SAME_PASSWORD);
        }
        if (!updatePasswordAo.getNewPassword().equals(updatePasswordAo.getConfirmPassword())) {
            throw new PermissionException(UpdatePasswordRspEnum.CONFIRM_ERROR);
        }
        long id = authService.getId(updatePasswordAo.getToken());

        User user = userMapper.selectById(id);

        if (!user.getPassword().equals(updatePasswordAo.getOldPassword())) {
            throw new PermissionException(UpdatePasswordRspEnum.PASSWORD_ERROR);
        }
        user.setPassword(updatePasswordAo.getNewPassword());
        int res = userMapper.updatePassword(user);
        if (res == 0) {
            throw new PermissionException(UpdatePasswordRspEnum.UPDATE_FAIL);
        }
        cacheUtil.remove(updatePasswordAo.getToken());
        return res;
    }

    @Override
    public List<MenuVo> getModuleMenu(String token) {
        Map<Long, MenuVo> map = new TreeMap<>();

        List<Permission> permissions = authService.getPermission(token);

        int id = 1;

        for (Permission permission : permissions) {
            if (permission.getPid().equals(0L)) {
                MenuVo menuVo = new MenuVo();
                menuVo.setResourcesName(permission.getName());
                menuVo.setResourcesUrl(permission.getUri());
                menuVo.setType(0);
                menuVo.setId(id++);
                menuVo.setImgName("icon-jixiaopaiming");
                menuVo.setChildren(new ArrayList<>());
                map.put(permission.getId(), menuVo);
            }
        }

        for (Permission permission : permissions) {
            if (!permission.getPid().equals(0L)) {
                MenuVo parent = map.get(permission.getPid());
                MenuVo menuVo = new MenuVo();
                menuVo.setResourcesName(permission.getName());
                menuVo.setResourcesUrl(permission.getUri());
                menuVo.setType(2);
                menuVo.setId(parent.getId() * 10 + (parent.getChildren().size() + 1));
                menuVo.setImgName("icon-jixiaopaiming");
                if (!parent.getIds().contains(permission.getId())){
                    parent.getChildren().add(menuVo);
                    parent.getIds().add(permission.getId());
                }
            }
        }

        List<MenuVo> res = new ArrayList<>();

        for (Map.Entry<Long, MenuVo> entry : map.entrySet()) {
            res.add(entry.getValue());
        }

        return res;
    }
}
