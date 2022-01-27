package priv.wjh.permission.infrastructure.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import priv.wjh.permission.infrastructure.exception.PermissionException;
import priv.wjh.permission.infrastructure.rsp.Rsp;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
@Slf4j
@RestControllerAdvice
public class PermissionExceptionHandler {
    @ExceptionHandler(PermissionException.class)
    public Rsp handlerPermissionException(PermissionException e){
        log.info("permission exception: ", e);
        return Rsp.fail(e.getRspEnum());
    }
}
