package priv.wjh.permission.domain.permission.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import priv.wjh.permission.api.ao.LoginAo;
import priv.wjh.permission.api.ao.UpdatePasswordAo;
import priv.wjh.permission.api.vo.LoginVo;
import priv.wjh.permission.api.vo.ValidateCodeVo;
import priv.wjh.permission.domain.permission.dao.AuthMapper;
import priv.wjh.permission.domain.permission.po.PermissionPojo;
import priv.wjh.permission.domain.permission.po.UserPojo;
import priv.wjh.permission.domain.permission.service.IAuthService;
import priv.wjh.permission.infrastructure.config.security.MyAuthentication;
import priv.wjh.permission.infrastructure.enums.rsp.FailRspEnum;
import priv.wjh.permission.infrastructure.enums.rsp.LoginRspEnum;
import priv.wjh.permission.infrastructure.exception.PermissionException;
import priv.wjh.permission.infrastructure.jwt.JwtService;
import priv.wjh.permission.infrastructure.utils.CacheUtil;
import priv.wjh.permission.infrastructure.utils.CaptchaUtil;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wangjunhao
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final AuthMapper authMapper;
    private final CacheUtil cacheUtil;
    private final JwtService jwtService;
    private final CaptchaUtil captchaUtil;


    @Override
    public LoginVo login(LoginAo ao) {
        // 检验图片验证码
        String code = cacheUtil.get(ao.getKeyCode())
                .orElseThrow(() -> new PermissionException(LoginRspEnum.VALIDATE_CODE_EXPIRED));

        if (!code.equals(ao.getAuthCode())) {
            throw new PermissionException(LoginRspEnum.VALIDATE_CODE_EXPIRED);
        }
        // 查询用户账号
        UserPojo userPojo = authMapper.findUser(ao);
        if (userPojo == null) {
            throw new PermissionException(LoginRspEnum.PASSWORD_ERROR);
        }

        LoginVo loginVo = new LoginVo();
        loginVo.setLoginName(userPojo.getUsername());
        loginVo.setUserName(userPojo.getUsername());
        loginVo.setId(userPojo.getId());

        String token = jwtService.generateAccessToken(String.valueOf(userPojo.getId()));
        loginVo.setToken(token);

        cacheUtil.put(token, userPojo.getId(), 60 * 10);
        return loginVo;
    }

    @Override
    public void logout() {
        MyAuthentication authentication = (MyAuthentication) SecurityContextHolder.getContext()
                .getAuthentication();
        cacheUtil.remove((String) authentication.getCredentials());
    }

    @Override
    public Integer updatePassword(UpdatePasswordAo ao) {
        Long id = getId();
        ao.setId(id);
        return authMapper.updatePassword(ao);
    }

    @Override
    public ValidateCodeVo getValidateCode() {
        ValidateCodeVo validateCodeVo = new ValidateCodeVo();
        String key = captchaUtil.getText();
        try {
            validateCodeVo.setAuthCode(captchaUtil.getCode(key));
        } catch (IOException e) {
            logger.info("生成图片验证码失败:", e);
            throw new PermissionException(FailRspEnum.GENERATE_CAPTCHA_FAIL);
        }
        validateCodeVo.setKeyCode(UUID.randomUUID()
                                          .toString()
                                          .replace("-", ""));
        cacheUtil.put(validateCodeVo.getKeyCode(), key, 60);
        return validateCodeVo;
    }


    @Override
    public List<PermissionPojo> getPermission() {
        Long id = getId();
        // 超级管理员拥有所有启用的权限
        if (id.equals(1L)) {
            return authMapper.findEnablePermission();
        }
        List<Long> roles = authMapper.findRoleByUserId(id);
        List<PermissionPojo> permissions = authMapper.findPermissionByRoleIds(roles);

        return new ArrayList<>(new HashSet<>(permissions));
    }


    @Override
    public List<PermissionPojo> getTreePermission() {
        List<PermissionPojo> permissions = getPermission();
        if (getId().equals(1L)) {
            return permissions;
        }
        permissions.addAll(authMapper.findParentPermission(
                permissions.stream()
                        .map(PermissionPojo::getPid)
                        .filter(Objects::nonNull)
                        .distinct()
                        .collect(Collectors.toList())
        ));
        return permissions;
    }


    private Long getId() {
        MyAuthentication authentication = (MyAuthentication) SecurityContextHolder.getContext()
                .getAuthentication();
        return authentication.getId();
    }
}
