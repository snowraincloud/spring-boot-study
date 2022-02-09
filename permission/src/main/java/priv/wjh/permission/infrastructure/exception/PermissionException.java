package priv.wjh.permission.infrastructure.exception;

import priv.wjh.permission.infrastructure.enums.rsp.IRspEnum;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
public class PermissionException extends RuntimeException {

    private final IRspEnum rspEnum;

    public PermissionException(IRspEnum rspEnum) {
        super(rspEnum.getMessage());
        this.rspEnum = rspEnum;
    }

    public PermissionException(IRspEnum rspEnum, Throwable cause) {
        super(rspEnum.getMessage(), cause);
        this.rspEnum = rspEnum;
    }

    public IRspEnum getRspEnum() {
        return rspEnum;
    }
}
