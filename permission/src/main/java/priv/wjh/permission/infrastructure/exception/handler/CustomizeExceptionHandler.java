package priv.wjh.permission.infrastructure.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import priv.wjh.permission.infrastructure.exception.MyMethodArgumentNotValidException;
import priv.wjh.permission.infrastructure.exception.PermissionException;
import priv.wjh.permission.infrastructure.rsp.Rsp;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
@Slf4j
@RestControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(MyMethodArgumentNotValidException.class)
    public Rsp handlerMyMethodArgumentNotValidException(MyMethodArgumentNotValidException myMethodArgumentNotValidException){
        logger.trace("invalid args: {}", myMethodArgumentNotValidException.getMessage());
        return Rsp.fail(-1, myMethodArgumentNotValidException.getMessage());
    }

    @ExceptionHandler(PermissionException.class)
    public Rsp handlerPermissionException(PermissionException e){
        logger.info("permission exception: ", e);
        return Rsp.fail(e.getRspEnum());
    }

}
