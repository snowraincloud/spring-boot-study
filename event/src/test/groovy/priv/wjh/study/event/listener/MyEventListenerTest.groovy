package priv.wjh.study.event.listener

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationEventPublisher
import priv.wjh.study.event.model.EventTypeEnums
import priv.wjh.study.event.model.MyEvent
import spock.lang.Specification

@SpringBootTest
class MyEventListenerTest extends Specification {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher

    @Autowired
    private MyEventListener myEventListener

    def "MyEventHandler"() {
        given:
        MyEvent myEvent = new MyEvent("start", type)
        myEventListener.type = null
        applicationEventPublisher.publishEvent(myEvent)
        expect:
        myEventListener.type == expectType

        where:
        type                  || expectType
        EventTypeEnums.START  || EventTypeEnums.START
        EventTypeEnums.MIDDLE || null
        EventTypeEnums.END    || EventTypeEnums.END
    }
}
