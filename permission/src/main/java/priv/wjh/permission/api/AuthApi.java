package priv.wjh.permission.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import priv.wjh.permission.api.ao.LoginAo;
import priv.wjh.permission.api.ao.UpdatePasswordAo;
import priv.wjh.permission.api.vo.LoginVo;
import priv.wjh.permission.api.vo.ValidateCodeVo;
import priv.wjh.permission.application.AuthApplicationService;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthApi {

    private final AuthApplicationService authApplicationService;

    @PostMapping("/login")
    public LoginVo login(@RequestBody LoginAo ao) {
        return authApplicationService.login(ao);
    }

    @PostMapping("/logout")
    public void logout() {
        authApplicationService.logout();
    }

    @PutMapping("updatePassword")
    public Integer updatePassword(@RequestBody UpdatePasswordAo ao) {
        return authApplicationService.updatePassword(ao);
    }

    @GetMapping("/validate/image/code")
    public ValidateCodeVo getValidateCode() {
        return authApplicationService.getValidateCode();
    }

    @GetMapping("/menu")
    public String getModuleMenu() {
        return authApplicationService.getMenu();
    }
}
