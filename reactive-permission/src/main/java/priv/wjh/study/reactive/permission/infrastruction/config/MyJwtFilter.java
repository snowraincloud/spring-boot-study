package priv.wjh.study.reactive.permission.infrastruction.config;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.Collections;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
@RequiredArgsConstructor
public class MyJwtFilter implements WebFilter {
    public static final String HEADER_PREFIX = "Bearer ";



    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = resolveToken(exchange.getRequest());
        if (token != null){
            Context context = ReactiveSecurityContextHolder
                    .withAuthentication(new UsernamePasswordAuthenticationToken(token, token, Collections.emptyList()));

            return chain.filter(exchange)
                    .contextWrite(context);

        }
        return chain.filter(exchange);
    }

    private String resolveToken(ServerHttpRequest request) {
        String bearerToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(HEADER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
