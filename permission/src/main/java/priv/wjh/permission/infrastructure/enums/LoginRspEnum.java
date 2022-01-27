package priv.wjh.permission.infrastructure.enums;

/**
 * @author W
 */

public enum LoginRspEnum implements IRspEnum {
    PASSWORD_ERROR(-100, "密码或用户名错误"),
    VALIDATE_CODE_EXPIRED(-105, "验证码不正确或已失效");

    private final int code;
    private final String message;

    private LoginRspEnum(int code, String message) {
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
