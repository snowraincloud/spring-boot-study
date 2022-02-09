package priv.wjh.permission.domain.permission.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import priv.wjh.permission.PermissionTestApplication
import priv.wjh.permission.domain.permission.po.PermissionPojo
import priv.wjh.permission.domain.permission.po.RolePojo
import spock.lang.Specification

import java.time.LocalDateTime

@SpringBootTest(classes = PermissionTestApplication.class)
class PermissionMapperTest extends Specification {
    @Autowired
    private PermissionMapper permissionMapper

    def "FindByRole"() {
        given:
        def role = new RolePojo()
        role.id = 1
        when:
        def res = permissionMapper.findByRole(role)
        then:
        res.size() == 2
        println(res)
    }

    def "Find"() {
        given:
        def permission = new PermissionPojo()
        permission.pid = 1
        permission.name = "na"
        permission.value = "va"
        permission.type = 1
        permission.uri = "ur"
        permission.status = 0
        permission.createTime = LocalDateTime.parse("2033-02-01T00:00:00")
        and:
        def res = permissionMapper.find(permission)
        expect:
        res.size() == 1
        res.get(0).id == 2
        println(res)
    }

    @Transactional
    def "Add"() {
        given:
        def permission = new PermissionPojo()
        permission.pid = 1
        permission.name = "add"
        permission.type = 1
        permission.status = false
        when:
        def res = permissionMapper.add(permission)
        def resRole = permissionMapper.find(permission)

        then:
        res == 1
        resRole.size() == 1
        resRole.get(0).id == permission.id
        println(resRole)
    }

    @Transactional
    def "Update"() {
        given:
        def permission = new PermissionPojo()
        permission.id = 1
        permission.name = "update"
        permission.type = 1
        permission.status = false
        when:
        def res = permissionMapper.update(permission)
        def resRole = permissionMapper.find(permission)
        then:
        res == 1
        resRole.size() == 1
        resRole.get(0).id == permission.id
        println(resRole)
    }
}
