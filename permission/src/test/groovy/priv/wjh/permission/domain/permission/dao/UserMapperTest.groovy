package priv.wjh.permission.domain.permission.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import priv.wjh.permission.PermissionTestApplication
import priv.wjh.permission.domain.permission.po.UserPojo
import spock.lang.Specification

import java.time.LocalDateTime

@SpringBootTest(classes = PermissionTestApplication.class)
class UserMapperTest extends Specification {
    @Autowired
    private UserMapper userMapper

    def "test for Find"() {
        given:
        def res = userMapper.find(userPojo)
        expect:
        res.size() == expectRes
        res.stream().forEach(System.out::println)

        where:
        userPojo                                                                   | expectRes
        new UserPojo()                                                             | 4
        toUser("admin", null, null, null, null)                                    | 1
        toUser("admin", 1, null, null, null)                                       | 1
        toUser("admin", 1, true, null, null)                                       | 1
        toUser("test", null, null, null, null)                                     | 3
        toUser("test1", null, null, null, null)                                    | 2
        toUser(null, 1, null, null, null)                                          | 1
        toUser(null, null, false, null, null)                                      | 0
        toUser(null, null, null, LocalDateTime.parse("2033-02-01T01:00:00"), null) | 2
        toUser(null, null, null, LocalDateTime.parse("2033-02-03T01:00:00"), null) | 1
        toUser(null, null, null, null, LocalDateTime.parse("2033-02-01T01:00:00")) | 2
        toUser(null, null, null, null, LocalDateTime.parse("2033-02-05T01:00:00")) | 1

    }

    def toUser(name, id, status, createTime, loginTime) {
        def user = new UserPojo()
        if (name) {
            user.username = name
        }
        if (id) {
            user.id = id
        }
        if (status != null) {
            user.status = status
        }
        if (createTime) {
            user.createTime = createTime
        }
        if (loginTime) {
            user.loginTime = loginTime
        }
        return user
    }


    @Transactional
    def "Add"() {
        given:
        def user = new UserPojo()
        user.username = "add"
        user.password = "add"
        user.status = false
        when:
        userMapper.add(user)
        def res = userMapper.find(user).get(0)
        then:
        user.id != null
        res.id == user.id
        println(res)
    }

    @Transactional
    def "Update"() {
        given:
        def user = new UserPojo()
        user.id = 1
        user.username = "update"
        user.password = "update"
        user.status = false
        when:
        userMapper.update(user)
        def res = userMapper.find(user).get(0)
        then:
        with(res) {
            id == user.id
            username == user.username
            password == user.password
            status == user.status
        }
        println(res)
    }
}
