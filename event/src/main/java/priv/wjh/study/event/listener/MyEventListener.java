package priv.wjh.study.event.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import priv.wjh.study.event.model.EventTypeEnums;
import priv.wjh.study.event.model.MyEvent;

/**
 * <h1>事件监听器</h1>
 * <p>监听对应的事件对象</p>
 * @author wangjunhao
 **/
@Component
@Slf4j
public class MyEventListener {

    EventTypeEnums type;

    @EventListener(condition = "#myEvent.type==T(priv.wjh.study.event.model.EventTypeEnums).START ||#myEvent.type==T(priv.wjh.study.event.model.EventTypeEnums).END")
    public void handlerStartAndEndEvent(MyEvent myEvent){
        log.info("HandlerStartAndEndEvent receive a event: {}", myEvent);
        type = myEvent.getType();
        throw new RuntimeException("1111111");
    }

    @EventListener
    public void handlerEvent(MyEvent myEvent){
        log.info("HandlerEvent receive a event: {}", myEvent);
    }
}
