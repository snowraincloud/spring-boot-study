package priv.wjh.transaction.domain.po;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRoleRelationPojo {
    private Long id;

    private Long userId;

    private Long roleId;

    public UserRoleRelationPojo(Long userId, Long roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}