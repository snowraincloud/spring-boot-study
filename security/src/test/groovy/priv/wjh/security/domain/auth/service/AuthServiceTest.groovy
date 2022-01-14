package priv.wjh.security.domain.auth.service

import org.springframework.security.crypto.password.PasswordEncoder
import priv.wjh.security.domain.auth.User
import priv.wjh.security.domain.auth.dao.UserDao
import priv.wjh.security.infrastructure.jwt.JwtService
import spock.lang.Specification

class AuthServiceTest extends Specification {
    def passwordEncode = Stub(PasswordEncoder)
    def jwtService = Stub(JwtService)
    def userDao = Stub(UserDao)

    def authService = new AuthService(passwordEncode, jwtService, userDao)


    def "test login"() {
        given:
        userDao.findRolesByMobile(_) >> user
        passwordEncode.matches(_, _) >> match
        jwtService.generateAccessToken(_) >> jwt
        def u = new User()
        u.setPassword("123")

        when:
        def res = authService.login(u)

        then:
        res.orElse("else") == excpectRes

        where:
        user                      | match | jwt       || excpectRes
        Optional.ofNullable(null) | false | null      || "else"
        getUser("123")            | false | null      || "else"
        getUser("123")            | true  | null      || "else"
        getUser("123")            | true  | "jwt_123" || "jwt_123"

    }

    static def getUser(def password) {
        def user = new User();
        user.setPassword(password)
        return Optional.of(user);
    }
}
