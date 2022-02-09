package priv.wjh.permission.domain.permission.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import priv.wjh.permission.api.ao.LoginAo;
import priv.wjh.permission.api.vo.LoginVo;
import priv.wjh.permission.api.vo.ValidateCodeVo;
import priv.wjh.permission.domain.permission.dao.AuthMapper;
import priv.wjh.permission.domain.permission.po.UserPojo;
import priv.wjh.permission.domain.permission.service.IAuthService;
import priv.wjh.permission.infrastructure.enums.rsp.LoginRspEnum;
import priv.wjh.permission.infrastructure.exception.PermissionException;
import priv.wjh.permission.infrastructure.jwt.JwtService;
import priv.wjh.permission.infrastructure.utils.CacheUtil;

/**
 *
 * @author wangjunhao
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final AuthMapper authMapper;
    private final CacheUtil cacheUtil;
    private final JwtService jwtService;



    @Override
    public LoginVo login(LoginAo ao) {
       String code = cacheUtil.get(ao.getKeyCode())
                .orElseThrow(() -> new PermissionException(LoginRspEnum.VALIDATE_CODE_EXPIRED));

        if (!code.equals(ao.getAuthCode())) {
            throw new PermissionException(LoginRspEnum.VALIDATE_CODE_EXPIRED);
        }
        UserPojo userPojo = authMapper.findUser(ao);

        LoginVo loginVo = new LoginVo();
        loginVo.setLoginTime(userPojo.getLoginTime());
        loginVo.setLoginName(userPojo.getUsername());
        loginVo.setUserName(userPojo.getUsername());
        loginVo.setId(userPojo.getId());

        String token = jwtService.generateAccessToken(String.valueOf(userPojo.getId()));
        loginVo.setToken(token);

        cacheUtil.put(token, userPojo, 60 * 10);
        return loginVo;
    }

    @Override
    public Integer updatePassword() {
        SecurityContextHolder.getContext().getAuthentication();
        return null;
    }

    @Override
    public ValidateCodeVo getValidateCode() {
        return null;
    }
}
