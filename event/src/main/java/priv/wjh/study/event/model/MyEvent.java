package priv.wjh.study.event.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
@Getter
@ToString
@AllArgsConstructor
public class MyEvent {
    private String msg;
    private EventTypeEnums type;
}
