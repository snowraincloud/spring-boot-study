package priv.wjh.permission.api.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wangjunhao
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidateCodeVo {
    private String keyCode;
    private String authCode;
}
