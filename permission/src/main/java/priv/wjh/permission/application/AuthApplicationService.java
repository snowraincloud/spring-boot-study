package priv.wjh.permission.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import priv.wjh.permission.api.ao.LoginAo;
import priv.wjh.permission.api.ao.UpdatePasswordAo;
import priv.wjh.permission.api.vo.LoginVo;
import priv.wjh.permission.api.vo.ValidateCodeVo;
import priv.wjh.permission.domain.permission.service.IAuthService;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
@Component
@RequiredArgsConstructor
public class AuthApplicationService {

    private final IAuthService authService;


    public LoginVo login(LoginAo ao) {
        return authService.login(ao);
    }

    public void logout() {
    }

    public Integer updatePassword(UpdatePasswordAo ao) {
        return authService.updatePassword();
    }

    public ValidateCodeVo getValidateCode() {
        return authService.getValidateCode();
    }

    public String getMenu() {
        return "";
    }
}
