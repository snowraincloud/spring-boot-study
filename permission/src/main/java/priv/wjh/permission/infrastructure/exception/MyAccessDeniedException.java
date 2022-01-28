package priv.wjh.permission.infrastructure.exception;

import org.springframework.security.access.AccessDeniedException;
import priv.wjh.permission.infrastructure.enums.IRspEnum;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
public class MyAccessDeniedException extends AccessDeniedException {
    private final IRspEnum rspEnum;
    public MyAccessDeniedException(IRspEnum rspEnum) {
        super(rspEnum.getMessage());
        this.rspEnum = rspEnum;
    }

    public MyAccessDeniedException(IRspEnum rspEnum, Throwable cause) {
        super(rspEnum.getMessage(), cause);
        this.rspEnum = rspEnum;
    }

    public IRspEnum getRspEnum() {
        return rspEnum;
    }
}
