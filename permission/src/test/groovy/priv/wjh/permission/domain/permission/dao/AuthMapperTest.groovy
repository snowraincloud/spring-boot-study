package priv.wjh.permission.domain.permission.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import priv.wjh.permission.PermissionTestApplication
import priv.wjh.permission.api.ao.LoginAo
import priv.wjh.permission.api.ao.UpdatePasswordAo
import spock.lang.Specification

@SpringBootTest(classes = PermissionTestApplication.class)
class AuthMapperTest extends Specification {

    @Autowired
    private AuthMapper authMapper

    def "FindUser"() {
        given:
        def loginAo = new LoginAo()
        loginAo.loginName = "admin"
        loginAo.password = "e10adc3949ba59abbe56e057f20f883e"
        def user = authMapper.findUser(loginAo)
        expect:
        user.id == 1
        println(user)
    }

    @Transactional
    def "UpdatePassword"() {
        given:
        def updatePasswordAo = new UpdatePasswordAo()
        updatePasswordAo.id = 1
        updatePasswordAo.oldPassword = "e10adc3949ba59abbe56e057f20f883e"
        updatePasswordAo.newPassword = "1"
        int res = authMapper.updatePassword(updatePasswordAo)
        and:
        def loginAo = new LoginAo()
        loginAo.loginName = "admin"
        loginAo.password = "1"
        def user = authMapper.findUser(loginAo)
        expect:
        res == 1
        user.password == "1"
        println(user)
    }

    def "FindEnablePermission"() {
        given:
        def permissions = authMapper.findEnablePermission()
        expect:
        permissions.size() == 3
        println(permissions)
    }

    def "FindRoleByUserId"() {
        given:
        def roleIds = authMapper.findRoleByUserId(1)
        expect:
        roleIds.size() == 2
        println(roleIds)
    }

    def "FindPermissionByRoleIds"() {
        given:
        def permissions = authMapper.findPermissionByRoleIds(Arrays.asList(1L))
        expect:
        permissions.size() == 1
        println(permissions)
    }

    def "FindParentPermission"() {
        given:
        def permission = authMapper.findParentPermission(Arrays.asList(1L))
        expect:
        permission.size() == 1
        permission.get(0).getName() == "one"
        println(permission)
    }
}
