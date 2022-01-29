package priv.wjh.permission.infrastructure.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import priv.wjh.permission.api.ao.BaseSelectAo;
import priv.wjh.permission.infrastructure.annotation.MyUnpack;
import priv.wjh.permission.infrastructure.exception.MyMethodArgumentNotValidException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * //TODO
 *
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

        BaseSelectAo ao = objectMapper.readValue(
                Objects.requireNonNull(webRequest.getNativeRequest(HttpServletRequest.class))
                        .getInputStream(), BaseSelectAo.class);
        Object object = objectMapper.readValue(ao.getData(), parameter.getParameterType());
        if (parameter.hasParameterAnnotation(Valid.class)){
            Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);
            if (!CollectionUtils.isEmpty(constraintViolations)){
                throw new MyMethodArgumentNotValidException(
                        constraintViolations.stream()
                                .map(ConstraintViolation::getMessage)
                                .collect(Collectors.joining(";")));
            }
        }
        return object;
    }
}
