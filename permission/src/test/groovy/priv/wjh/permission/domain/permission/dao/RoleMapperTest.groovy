package priv.wjh.permission.domain.permission.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import priv.wjh.permission.PermissionTestApplication
import priv.wjh.permission.domain.permission.po.RolePojo
import priv.wjh.permission.domain.permission.po.UserPojo
import spock.lang.Specification

import java.time.LocalDateTime

@SpringBootTest(classes = PermissionTestApplication.class)
class RoleMapperTest extends Specification {
    @Autowired
    private RoleMapper roleMapper

    def "FindByUser"() {
        given:
        def user = new UserPojo()
        user.id = 1
        def roles = roleMapper.findByUser(user)
        expect:
        roles.size() == 2
        roles.stream().forEach(System.out::println)
    }

    def "Find"() {
        given:
        def res = roleMapper.find(role)
        expect:
        res.size() == len
        println(res)

        where:
        role                                            | len
        new RolePojo()                                  | 3
        toRole(1, null, null, null)                     | 1
        toRole(null, "test", null, null)                | 3
        toRole(null, "test1", null, null)               | 2
        toRole(null, "1test", null, null)               | 1
        toRole(null, null, true, null)                  | 3
        toRole(null, null, false, null)                 | 0
        toRole(null, null, null, "2033-02-01T00:00:00") | 2
        toRole(null, null, null, "2033-02-02T00:00:00") | 1

    }

    def toRole(id, name, status, createTime) {
        def role = new RolePojo()
        if (id) {
            role.id = id
        }
        if (name) {
            role.name = name
        }

        if (status != null) {
            role.status = status
        }
        if (createTime) {
            role.createTime = LocalDateTime.parse(createTime)
        }

        return role
    }

    @Transactional
    def "Add"() {
        given:
        def role = new RolePojo()
        role.name = "add"
        role.description = "add"
        role.status = false
        when:
        def res = roleMapper.add(role)
        def resRole = roleMapper.find(role)

        then:
        res == 1
        resRole.size() == 1
        resRole.get(0).id == role.id
        println(resRole)
    }

    @Transactional
    def "Update"() {
        given:
        def role = new RolePojo()
        role.id = 1
        role.name = "update"
        role.description = "update"
        role.status = false
        when:
        def res = roleMapper.update(role)
        def resRole = roleMapper.find(role)
        then:
        res == 1
        resRole.size() == 1
        with(resRole.get(0)){
            id == role.id
            name == role.name
            description == role.description
            status == role.status
        }
        println(resRole)
    }
}
