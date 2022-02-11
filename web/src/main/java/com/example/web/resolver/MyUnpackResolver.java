package com.example.web.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <h1>解包参数解析器</h1>
 * {@link MyUnpack}
 * @author wangjunhao
 **/
@Component
public class MyUnpackResolver implements HandlerMethodArgumentResolver {

    private final ObjectMapper objectMapper;
    private final Validator validator;
    public MyUnpackResolver(ObjectMapper objectMapper, Validator validator) {
        this.objectMapper = objectMapper;
        this.validator = validator;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(MyUnpack.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // 解包
        BaseSelectAo ao = objectMapper.readValue(
                Objects.requireNonNull(webRequest.getNativeRequest(HttpServletRequest.class))
                        .getInputStream(), BaseSelectAo.class);
        // 参数
        Object object = objectMapper.readValue(ao.getData(), parameter.getParameterType());
        // 参数校验
        if (parameter.hasParameterAnnotation(Valid.class)){
            Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);
            if (!CollectionUtils.isEmpty(constraintViolations)){
                // 返回错误信息
                throw new MyMethodArgumentNotValidException(
                        constraintViolations.stream()
                                .map((e) -> e.getPropertyPath().iterator().next().getName() + ": " + e.getMessage())
                                .collect(Collectors.joining(";")));
            }
        }
        return object;
    }
}
