package priv.wjh.permission.api.ao;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wangjunhao
 */
@Data
public class RoleAo {

    private Long id;

    private String name;

    private String description;

    private LocalDateTime createTime;

    private Boolean status;

    private List<Long> permissionIds;

}
