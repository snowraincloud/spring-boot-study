package priv.wjh.permission.infrastructure.config.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import priv.wjh.permission.infrastructure.jwt.JwtService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <h1>提取请求中的token,验证有效期</h1>
 * <p>
 *     需手动new, bean会变成全局filter
 * </p>
 * @author wangjunhao
 **/
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        jwtService.extractJwt(request).ifPresent(jwt -> {
            jwtService.parserAccessToken(jwt).ifPresent(info -> {
                JwtAuthentication jwtAuthentication = new JwtAuthentication(info, jwt);
                jwtAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(jwtAuthentication);
            });
        });

        filterChain.doFilter(request, response);

    }


}
