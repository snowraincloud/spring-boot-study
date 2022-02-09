package priv.wjh.permission.domain.permission.service;

import priv.wjh.permission.api.ao.LoginAo;
import priv.wjh.permission.api.vo.LoginVo;
import priv.wjh.permission.api.vo.ValidateCodeVo;

public interface IAuthService {

    LoginVo login(LoginAo ao);

    Integer updatePassword();

    ValidateCodeVo getValidateCode();
}
