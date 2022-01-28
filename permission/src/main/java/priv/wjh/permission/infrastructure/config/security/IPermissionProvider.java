package priv.wjh.permission.infrastructure.config.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * <h1>提供用户的权限列表</h1>
 * @author wangjunhao
 */
@FunctionalInterface
public interface IPermissionProvider<T extends GrantedAuthority> {

    /**
     * <h2>根据信息获取权限</h2>
     * <p>验证失败返回 空权限列表</p>
     * @param info 信息
     * @return 权限列表
     */
    List<T> getPermission(String info);
}
