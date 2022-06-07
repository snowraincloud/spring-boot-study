package priv.wjh.security.infrastructure.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
@Slf4j
@Component
public class MyPermission {

    public void print(){
      log.info(this.getClass().getName());
    }
}
