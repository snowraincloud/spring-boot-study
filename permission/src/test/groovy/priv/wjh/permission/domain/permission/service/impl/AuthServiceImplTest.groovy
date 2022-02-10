package priv.wjh.permission.domain.permission.service.impl

import org.mockito.Mockito
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import priv.wjh.permission.api.ao.LoginAo
import priv.wjh.permission.api.ao.UpdatePasswordAo
import priv.wjh.permission.domain.permission.dao.AuthMapper
import priv.wjh.permission.domain.permission.po.PermissionPojo
import priv.wjh.permission.infrastructure.config.security.MyAuthentication
import priv.wjh.permission.infrastructure.exception.PermissionException
import priv.wjh.permission.infrastructure.jwt.JwtService
import priv.wjh.permission.infrastructure.utils.CacheUtil
import priv.wjh.permission.infrastructure.utils.CaptchaUtil
import spock.lang.Specification

class AuthServiceImplTest extends Specification {
    def authMapper = Mock(AuthMapper)
    def cacheUtil = Mock(CacheUtil)
    def jwtService = Mock(JwtService)
    def captchaUtil = Mock(CaptchaUtil)

    def authService = new AuthServiceImpl(authMapper, cacheUtil, jwtService, captchaUtil)

    static def securityContextHolder = Mockito.mockStatic(SecurityContextHolder.class)
    static def securityContext = Mockito.mock(SecurityContext)
    static def authentication = Mockito.mock(MyAuthentication)

    def setupSpec() {
        securityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext)
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication)
    }


    def "Login fail"() {
        when:
        cacheUtil.get(_) >> code
        authMapper.findUser(_) >> user
        authService.login(ao)
        then:
        def e = thrown(PermissionException.class)
        e.message == msg
        println(e)

        where:
        ao            | code             | user || msg
        new LoginAo() | Optional.empty() | null || "验证码不正确或已失效"
        toAo("1")     | Optional.of("0") | null || "验证码不正确或已失效"
        toAo("1")     | Optional.of("1") | null || "密码或用户名错误"
    }

    def toAo(code) {
        LoginAo loginAo = new LoginAo()
        loginAo.authCode = code
        return loginAo
    }


    def "Logout"() {
        given:
        Mockito.when(authentication.getCredentials()).thenReturn("token")
        when:
        authService.logout()
        then:
        1 * cacheUtil.remove(_)

    }

    def "UpdatePassword"() {
        when:
        authService.updatePassword(new UpdatePasswordAo())
        authMapper.updatePassword(_ as UpdatePasswordAo) >> 1
        then:
        1 * authMapper.updatePassword(_)
    }

    def "GetValidateCode"() {
        when:
        authService.getValidateCode()
        then:
        1 * captchaUtil.getText()
        1 * captchaUtil.getCode(_)
        1 * cacheUtil.put(_, _, _)

    }

    def "GetTreePermission"() {
        given:
        Mockito.when(authentication.getId()).thenReturn(id)
        def c = (id == 1L ? 0 : 1)
        when:
        authMapper.findParentPermission(_) >> new ArrayList<PermissionPojo>()
        def menu = authService.getTreePermission()
        then:
        if (id != 1L) {
            menu.size() == len
        }
        c * authMapper.findRoleByUserId(_)
        c * authMapper.findPermissionByRoleIds(_) >> permissions
        println(menu)

        where:
        id | permissions      || len
        1L | null             || 3
        2L | getPermissions() || 9
    }


    def getPermissions() {
        def res = new ArrayList<PermissionPojo>()
        for (i in 1..10) {
            def tmp = new PermissionPojo()
            tmp.id = i
            res.add(tmp)
        }
        for (i in 11..20) {
            def tmp = new PermissionPojo()
            tmp.id = i
            tmp.pid = i - 10
            res.add(tmp)
        }
        return res
    }
}
