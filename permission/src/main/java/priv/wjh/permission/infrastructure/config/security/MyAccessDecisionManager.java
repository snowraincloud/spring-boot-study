package priv.wjh.permission.infrastructure.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.AntPathMatcher;
import priv.wjh.permission.infrastructure.enums.AccessDeniedRspEnum;
import priv.wjh.permission.infrastructure.exception.MyAccessDeniedException;

import java.util.Collection;

/**
 * <h1>权限验证</h1>
 * @author wangjunhao
 */
@Configuration
public class MyAccessDecisionManager implements AccessDecisionManager{
    private static final Logger log = LoggerFactory.getLogger(MyAccessDecisionManager.class);
    private final IAccessDecision accessDecision;



    public MyAccessDecisionManager(IAccessDecision accessDecision) {
        this.accessDecision = accessDecision;
    }

    /**
     * <h2>默认权限检验</h2>
     * <p>需拥有访问资源所需要的全部权限</p>
     * @return 默认权限检验
     */
    @Bean
    @ConditionalOnMissingBean
    public static IAccessDecision accessDecision(){
        final AntPathMatcher antPathMatcher = new AntPathMatcher();
        return (authentication, object, configAttributes) -> {
            for (ConfigAttribute configAttribute : configAttributes){
                for (GrantedAuthority authority : authentication.getAuthorities()){
                    if (!antPathMatcher.match(authority.getAuthority(), configAttribute.getAttribute())){
                        throw new MyAccessDeniedException(AccessDeniedRspEnum.PERMISSION_DENIED);
                    }
                }
            }
        };
    }

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        if (log.isDebugEnabled()){
            authentication.getAuthorities().forEach(msg -> log.debug("authorities: [{}]", msg.getAuthority()));
        }
        if (log.isDebugEnabled()){
            configAttributes.forEach(msg -> log.debug("configAttributes: [{}]", msg.getAttribute()));
        }
        accessDecision.decide(authentication, object, configAttributes);

    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
