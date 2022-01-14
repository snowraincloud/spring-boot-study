package priv.wjh.security.domain.auth;


import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 *
 * @author wangjunhao
 **/
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails, Serializable {
    private static final long serialVersionUID = 1L;


    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码哈希
     */
    private String password;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 电邮地址
     */
    private String email;

    /**
     * 是否启用两步验证
     */
    private boolean usingMfa = false;

    /**
     * 角色列表，使用 Set 确保不重复
     */
    private Set<Role> roles = new HashSet<>();

    /**
     * 是否激活，默认激活
     */
    private boolean enabled = true;

    /**
     * 账户是否未过期，默认未过期
     */
    private boolean accountNonExpired = true;

    /**
     * 账户是否未锁定，默认未锁定
     */
    private boolean accountNonLocked = true;

    /**
     * 密码是否未过期，默认未过期
     */
    private boolean credentialsNonExpired = true;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .flatMap(role -> Stream.concat(
                        Stream.of(new SimpleGrantedAuthority(role.getRoleName())),
                        role.getPermissions()
                                .stream())
                )
                .collect(Collectors.toSet());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(user.id, id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", usingMfa=" + usingMfa +
                ", roles=" + roles +
                ", enabled=" + enabled +
                ", accountNonExpired=" + accountNonExpired +
                ", accountNonLocked=" + accountNonLocked +
                ", credentialsNonExpired=" + credentialsNonExpired +
                '}';
    }
}
