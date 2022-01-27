package priv.wjh.permission.infrastructure.config.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

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
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        handler(response, "权限不足");
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        handler(response, "请登陆");
    }

    private void handler(HttpServletResponse response, String res) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/javascript;charset=utf-8");
        response.getWriter().print(res);
    }
}
