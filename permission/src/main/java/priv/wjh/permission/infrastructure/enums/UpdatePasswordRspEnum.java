package priv.wjh.permission.infrastructure.enums;

/**
 * @author wangjunhao
 */

public enum UpdatePasswordRspEnum implements IRspEnum {
    PASSWORD_ERROR(-8, "原始密码错误"),
    NOT_SAME_PASSWORD(-8, "新密码不能和旧密码相同"),
    CONFIRM_ERROR(-8, "两次输入密码不同"),
    UPDATE_FAIL(-8, "更新密码失败")
    ;

    private final int code;
    private final String message;

    private UpdatePasswordRspEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
