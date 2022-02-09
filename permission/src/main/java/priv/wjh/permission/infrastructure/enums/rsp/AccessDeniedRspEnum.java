package priv.wjh.permission.infrastructure.enums.rsp;

/**
 * <h1>拒绝访问,返回值信息枚举</h1>
 *
 * @author wangjunhao
 **/
public enum AccessDeniedRspEnum implements IRspEnum{
    DEFAULT(-10, "默认状态码"),
    PERMISSION_DENIED(-11, "权限不足")
    ;
    private final int code;
    private final String message;

    AccessDeniedRspEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
