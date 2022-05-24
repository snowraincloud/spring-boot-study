package priv.wjh.study.design.pattern.strategy

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class PayServiceTest extends Specification {
    @Autowired
    IPay payService

    def "Pay"() {
        when:
        payService.pay(type)

        then:
        noExceptionThrown()

        where:
        type            | _
        PayEnum.ALI     | _
        PayEnum.WE_CHAT | _
    }
}
