package priv.wjh.security.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import priv.wjh.security.application.AuthApplicationService;
import priv.wjh.security.domain.auth.User;


/**
 * @author wangjunhao
 */


@RestController
@RequestMapping("/")
public class AuthorizeResource {
    private final AuthApplicationService authApplicationService;


    public AuthorizeResource(AuthApplicationService authApplicationService) {
        this.authApplicationService = authApplicationService;
    }

    @PostMapping("login")
    public String login(@RequestBody User user){
        return authApplicationService.login(user).orElse("Failed to login");
    }

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("test")
    public String test(){
        return "method name is test";
    }
}
