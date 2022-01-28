package priv.wjh.permission.infrastructure.config.security;

import org.springframework.security.access.ConfigAttribute;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * <h1>提供访问资源需要的权限</h1>
 * @author wangjunhao
 */
@FunctionalInterface
public interface ISourcePermissionProvider<T extends ConfigAttribute >{
    /**
     * <h1>返回访问资源需要的权限列表</h1>
     * <p>为空,表示不需要权限即可访问</p>
     * @param request 请求信息
     * @return 权限列表
     */
    Collection<T> getSource(HttpServletRequest request);
}
