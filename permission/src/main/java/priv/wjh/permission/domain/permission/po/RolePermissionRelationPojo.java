package priv.wjh.permission.domain.permission.po;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
@Data
@NoArgsConstructor
public class RolePermissionRelationPojo {
    private Long id;
    private Long roleId;
    private Long PermissionId;

    public RolePermissionRelationPojo(Long roleId, Long permissionId) {
        this.roleId = roleId;
        PermissionId = permissionId;
    }
}
