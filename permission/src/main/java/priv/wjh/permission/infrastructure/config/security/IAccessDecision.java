package priv.wjh.permission.infrastructure.config.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;

import java.util.Collection;

/**
 * <h1>检验用户是否具有访问权限</h1>
 *
 * @author wangjunhao
 **/
@FunctionalInterface
public interface IAccessDecision {
    /**
     * <h2>检验用户是否具有访问资源的权限</h2>
     * @param authentication 用户权限
     * @param object {@link org.springframework.security.web.FilterInvocation}
     * @param configAttributes 资源需要的权限
     * @throws AccessDeniedException 权限不足抛出异常
     */
    void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException;
}
