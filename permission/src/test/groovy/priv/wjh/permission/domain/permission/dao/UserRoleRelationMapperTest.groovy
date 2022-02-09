package priv.wjh.permission.domain.permission.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import priv.wjh.permission.PermissionTestApplication
import priv.wjh.permission.domain.permission.po.UserRoleRelationPojo
import spock.lang.Specification

@SpringBootTest(classes = PermissionTestApplication.class)
class UserRoleRelationMapperTest extends Specification {
    @Autowired
    private UserRoleRelationMapper userRoleRelationMapper

    @Transactional
    def "Add"() {
        given:
        def res = userRoleRelationMapper.add(relations)
        expect:
        res == len
        where:
        relations | len
        Arrays.asList(new UserRoleRelationPojo(1, 1)) | 1
        Arrays.asList(new UserRoleRelationPojo(1, 1), new UserRoleRelationPojo(2, 1)) | 2

    }


    @Transactional
    def "DeleteByUserId"() {
        given:
        def res = userRoleRelationMapper.deleteByUserId(id)
        expect:
        res == len
        where:
        id | len
        1 | 2
        2 | 1
    }
}
