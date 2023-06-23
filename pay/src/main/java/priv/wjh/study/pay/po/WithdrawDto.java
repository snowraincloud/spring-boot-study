package priv.wjh.study.pay.po;

import lombok.Data;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
@Data
public class WithdrawDto {
    private Long id;
    private String alipayTradeSn;

    private Long userId;

    private Long amount;

    private String toAlipayAccount;
    private String realName;

}
