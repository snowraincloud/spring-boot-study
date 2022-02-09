package priv.wjh.permission.domain.permission.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import priv.wjh.permission.PermissionTestApplication
import priv.wjh.permission.domain.permission.po.RolePermissionRelationPojo
import spock.lang.Specification

@SpringBootTest(classes = PermissionTestApplication.class)
class RolePermissionRelationMapperTest extends Specification {

    @Autowired
    private RolePermissionRelationMapper rolePermissionRelationMapper

    @Transactional
    def "Add"() {
        given:
        def res = rolePermissionRelationMapper.add(relations)
        expect:
        res == len
        where:
        relations | len
        Arrays.asList(new RolePermissionRelationPojo(1, 1)) | 1
        Arrays.asList(new RolePermissionRelationPojo(1, 1), new RolePermissionRelationPojo(2, 1)) | 2

    }

    @Transactional
    def "DeleteByUserId"() {
        given:
        def res = rolePermissionRelationMapper.deleteByRoleId(id)
        expect:
        res == len
        where:
        id | len
        1 | 2
        2 | 1
    }
}
