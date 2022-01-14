package priv.wjh.security.infrastructure.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import priv.wjh.security.infrastructure.jwt.JwtService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <h1>提取jwt 并验证有效期</h1>
 *
 * @author wangjunhao
 **/
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        jwtService.extractJwt(request)
                .flatMap(jwtService::parserAccessToken)
                .ifPresent(username -> {
                    JwtAuthentication jwtAuthentication = new JwtAuthentication(username);
                    jwtAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(jwtAuthentication);
                });

        filterChain.doFilter(request, response);
    }


}
