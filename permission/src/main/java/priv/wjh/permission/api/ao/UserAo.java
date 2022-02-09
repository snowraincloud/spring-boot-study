package priv.wjh.permission.api.ao;


import lombok.Data;

import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wangjunhao
 */
@Data
public class UserAo {
    @Positive(message = "用户ID必须大于零")
    private Long id;

    private String username;

    private String password;

    @Past(message = "创建时间不能大于当前时间")
    private LocalDateTime createTime;
    @Past(message = "登陆时间不能大于当前时间")
    private LocalDateTime loginTime;
    private Boolean status;

    private List<Long> roleIds;
}
