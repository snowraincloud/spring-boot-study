package priv.wjh.permission.infrastructure.enums;

public enum PatternRspEnum implements IRspEnum {
    PATTERN(0,"参数不合规范");

    private final int code;
    private final String message;

    private PatternRspEnum(int code, String message) {
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
