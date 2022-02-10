package priv.wjh.permission.api.ao;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wangjunhao
 */
@NoArgsConstructor
@Data
public class UpdatePasswordAo {
    private Long id;
    private String oldPassword;
    private String newPassword;
}
