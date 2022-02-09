package priv.wjh.permission.domain.permission.service.impl


import priv.wjh.permission.domain.permission.dao.UserMapper
import priv.wjh.permission.domain.permission.dao.UserRoleRelationMapper
import priv.wjh.permission.domain.permission.po.UserPojo
import priv.wjh.permission.domain.permission.po.UserRoleRelationPojo
import priv.wjh.permission.infrastructure.exception.PermissionException
import spock.lang.Specification

class UserServiceImplTest extends Specification {


    def "add user and role"() {
        given:
        UserMapper userMapper = Mock(UserMapper)
        UserRoleRelationMapper userRoleRelationMapper = Mock(UserRoleRelationMapper)
        def userService = new UserServiceImpl(userMapper, userRoleRelationMapper)

        when:
        userService.add(user)

        then:
        addUserCount * userMapper.add(user)
        addRelationCount * userRoleRelationMapper.add(_) >> (addRelationCount != 0 ? user.roleIds.size() : 0)

        where:
        user                                || addUserCount | addRelationCount
        new UserPojo()                      || 1            | 0
        toUser(1, null)                     || 1            | 0
        toUser(null, Arrays.asList(1L, 2L)) || 1            | 0
        toUser(1, Arrays.asList(1L, 2L))    || 1            | 1
    }

    def "add user expect permissionException"() {
        given:
        UserMapper userMapper = Stub(UserMapper)
        UserRoleRelationMapper userRoleRelationMapper = Stub(UserRoleRelationMapper)
        def userService = new UserServiceImpl(userMapper, userRoleRelationMapper)

        when:
        userRoleRelationMapper.add(_ as List<UserRoleRelationPojo>) >> 0
        userService.add(user)

        then:
        thrown(PermissionException.class)

        where:
        user                             || o
        toUser(1, Arrays.asList(1L, 2L)) || 1
    }


    def toUser(id, roleIds) {
        def user = new UserPojo()
        if (id) {
            user.id = id
        }
        if (roleIds) {
            user.roleIds = roleIds
        }
        return user
    }

    def "update user and role"() {
        given:
        UserMapper userMapper = Mock(UserMapper)
        UserRoleRelationMapper userRoleRelationMapper = Mock(UserRoleRelationMapper)
        def userService = new UserServiceImpl(userMapper, userRoleRelationMapper)

        when:
        userService.update(user)

        then:
        updateUserCount * userMapper.update(_) >> 1
        deleteRelationCount * userRoleRelationMapper.deleteByUserId(_)
        addRelationCount * userRoleRelationMapper.add(_) >> (addRelationCount != 0 ? user.roleIds.size() : 0)

        where:
        user                             || updateUserCount | deleteRelationCount | addRelationCount
        new UserPojo()                   || 1               | 0                   | 0
        toUser(1, null)                  || 1               | 0                   | 0
        toUser(1, Arrays.asList(1L, 2L)) || 1               | 1                   | 1
    }

    def "update user and role, expect permissionException"(){
        given:
        UserMapper userMapper = Stub(UserMapper)
        UserRoleRelationMapper userRoleRelationMapper = Stub(UserRoleRelationMapper)
        def userService = new UserServiceImpl(userMapper, userRoleRelationMapper)

        when:
        userMapper.update(_ as UserPojo) >> (addRelationCount != 0 ? 1 : 0)
        userRoleRelationMapper.add(_ as List<UserRoleRelationPojo>) >> 0
        and:
        userService.update(user)

        then:
        def e = thrown(PermissionException.class)
        e != null

        where:
        user                             || addRelationCount
        toUser(1, Arrays.asList(1L, 2L)) || 0
        toUser(1, Arrays.asList(1L, 2L)) || 1
    }
}
