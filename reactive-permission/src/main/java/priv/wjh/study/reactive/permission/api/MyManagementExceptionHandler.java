package priv.wjh.study.reactive.permission.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.stream.Collectors;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
@Slf4j
@RestControllerAdvice
public class MyManagementExceptionHandler {

    /**
     * <h2>reactive web 框架检验失败异常处理器</h2>
     * @param exception reactive web下validation框架检验失败异常
     * @return 响应值
     */
    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String validExceptionHandler(WebExchangeBindException exception) {
        return getErr(exception.getBindingResult());
    }

    private String getErr(BindingResult result) {
        String msg = "";
        if (result.hasErrors()) {
            msg = result.getAllErrors()
                    .stream()
                    .map(err -> (FieldError) err)
                    .map(err -> err.getField() + "" + err.getDefaultMessage())
                    .collect(Collectors.joining(", "));
        }
        return msg;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String exceptionHandler(Exception ex) {
        log.error("Exception: ", ex);
        return ex.getMessage();
    }
}
