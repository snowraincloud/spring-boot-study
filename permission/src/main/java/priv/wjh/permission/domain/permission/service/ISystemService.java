package priv.wjh.permission.domain.permission.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import priv.wjh.permission.api.ao.LoginAo;
import priv.wjh.permission.api.ao.UpdatePasswordAo;
import priv.wjh.permission.api.vo.LoginVo;
import priv.wjh.permission.api.vo.MenuVo;

import java.util.List;
import java.util.Optional;


public interface ISystemService {
    LoginVo login(LoginAo loginAo) throws JsonProcessingException;

    void logout(String token);

    Optional<com.wanshun.console.permission.vo.ValidateCodeVo> getValidateCode() throws JsonProcessingException;

    Integer updatePassword(UpdatePasswordAo updatePasswordAo) throws JsonProcessingException;

    List<MenuVo> getModuleMenu(String token) throws JsonProcessingException;
}
