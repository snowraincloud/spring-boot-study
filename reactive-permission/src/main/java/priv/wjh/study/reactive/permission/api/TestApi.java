package priv.wjh.study.reactive.permission.api;

import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
@RestController
public class TestApi {
    @Data
    static class TestBody{
        @NotNull
        private String name;
    }


    @PostMapping
    public void test(@RequestBody @Validated TestBody body){
        
    }

    @GetMapping
    public Mono<Void> test() {
        return ReactiveSecurityContextHolder.getContext()
                .switchIfEmpty(Mono.error(new RuntimeException("获取用户信息失败1111111111")))
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .cast(String.class)
                .flatMap(authentication -> {
                    throw new RuntimeException("122222222222222222222222222");
//                    System.out.println(authentication);
//                    return Mono.empty();
                });
    }
}
