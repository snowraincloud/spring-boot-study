package priv.wjh.permission.infrastructure.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import priv.wjh.permission.infrastructure.enums.rsp.AccessDeniedRspEnum;
import priv.wjh.permission.infrastructure.exception.MyAccessDeniedException;
import priv.wjh.permission.infrastructure.rsp.Rsp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <h1>（匿名/已认证）的用户访问无权限资源时的异常</h1>
 *
 * @author wangjunhao
 **/
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler, AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public MyAccessDeniedHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
        if (e instanceof MyAccessDeniedException){
            handler(response, Rsp.fail(((MyAccessDeniedException) e).getRspEnum()));
        }else {
            handler(response, Rsp.fail(AccessDeniedRspEnum.DEFAULT.getCode(), e.getMessage()));
        }
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        handler(response, "请登陆");
    }

    private void handler(HttpServletResponse response, Object res) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/javascript;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        objectMapper.writeValue(response.getWriter(), res);
    }
}
