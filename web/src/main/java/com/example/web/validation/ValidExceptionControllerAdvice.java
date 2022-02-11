package com.example.web.validation;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <h1>valid异常 捕获</h1>
 * <p>参数在校验失败的时候会抛出的MethodArgumentNotValidException或者BindException两种异常，
 * 可以在全局的异常处理器中捕捉到这两种异常，将提示信息或者自定义信息返回给客户端。</p>
 * @author wangjunhao
 **/
@RestControllerAdvice
public class ValidExceptionControllerAdvice {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class, BindException.class})
    public String handleValidException(Exception e){
        BindingResult bindingResult = null;
        if (e instanceof MethodArgumentNotValidException) {
            bindingResult = ((MethodArgumentNotValidException)e).getBindingResult();
        } else if (e instanceof BindException) {
            bindingResult = ((BindException)e).getBindingResult();
        }
        // 返回异常信息
        return Objects.requireNonNull(bindingResult).getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(","));

    }
}
