package priv.wjh.security.domain.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import priv.wjh.security.domain.auth.User;
import priv.wjh.security.domain.auth.dao.UserDao;
import priv.wjh.security.infrastructure.jwt.JwtService;

import java.util.Optional;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserDao userDao;

    public AuthService(PasswordEncoder passwordEncoder, JwtService jwtService, UserDao userDao) {
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userDao = userDao;
    }


    public Optional<String> login(User user) {
        Optional<User> u = userDao.findRolesByMobile(user.getMobile());
        if (u.isEmpty() || !passwordEncoder.matches(user.getPassword(), u.get().getPassword())) {
            return Optional.empty();
        }
        return Optional.ofNullable(jwtService.generateAccessToken(u.get().getUsername()));
    }
}
