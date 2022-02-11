package priv.wjh.transaction.domain.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class UserRoleRelationServiceTest extends Specification {
    @Autowired
    private UserRoleRelationService userRoleRelationService


    def "AddAndDel"() {
        when:
        userRoleRelationService.deleteByUserId(1L)
        def res = userRoleRelationService.findByUserId(1L)
        then:
        thrown(RuntimeException.class)
        println(res)
    }
}
