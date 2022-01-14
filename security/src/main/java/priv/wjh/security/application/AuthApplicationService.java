package priv.wjh.security.application;

import org.springframework.stereotype.Service;
import priv.wjh.security.domain.auth.User;
import priv.wjh.security.domain.auth.service.AuthService;

import java.util.Optional;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
@Service
public class AuthApplicationService {
    private final AuthService authService;

    public AuthApplicationService(AuthService authService) {
        this.authService = authService;
    }

    public Optional<String> login(User user){
        return authService.login(user);
    }
}
