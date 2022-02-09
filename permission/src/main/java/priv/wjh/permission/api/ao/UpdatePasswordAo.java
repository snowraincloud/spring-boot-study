package priv.wjh.permission.api.ao;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UpdatePasswordAo {
    private String token;
    private String oldPassword;
    private String newPassword;
}
