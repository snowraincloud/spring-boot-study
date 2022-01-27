package priv.wjh.permission.infrastructure.enums;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
public enum FailRspEnum implements IRspEnum {
    ZERO(0, "请求参数错误"),
    ;

    private final int code;
    private final String message;

    private FailRspEnum(int code, String message) {
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
