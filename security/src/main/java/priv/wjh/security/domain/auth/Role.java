package priv.wjh.security.domain.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author wangjunhao
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增长 ID，唯一标识
     */
    private Long id;

    /**
     * 角色名称，有唯一约束，不能重复
     */
    private String roleName;

    /**
     * 显示名称
     */
    private String displayName;

    /**
     * 用户列表
     */
    private Set<User> users = new HashSet<>();

    /**
     * 权限列表
     */
    private Set<Permission> permissions = new HashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", displayName='" + displayName + '\'' +
                ", users=" + users +
                ", permissions=" + permissions +
                '}';
    }
}
