package priv.wjh.security.domain.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Objects;


/**
 *
 * @author wangjunhao
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Permission implements GrantedAuthority, Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 权限
     */
    private String authority;

    /**
     * 显示名称
     */
    private String displayName;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, authority, displayName);
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", authority='" + authority + '\'' +
                ", displayName='" + displayName + '\'' +
                '}';
    }
}
