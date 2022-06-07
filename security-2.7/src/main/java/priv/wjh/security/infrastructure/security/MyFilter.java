package priv.wjh.security.infrastructure.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
@AllArgsConstructor
@Slf4j
public class MyFilter extends OncePerRequestFilter {
    private final MyPermission myPermission;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("{}", this.getClass().getName());
        myPermission.print();
        throw new AccessDeniedException("111");
//        filterChain.doFilter(request, response);
    }
}
