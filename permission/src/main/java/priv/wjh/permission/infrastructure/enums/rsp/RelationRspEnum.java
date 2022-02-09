package priv.wjh.permission.infrastructure.enums.rsp;

import lombok.RequiredArgsConstructor;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
@RequiredArgsConstructor
public enum RelationRspEnum implements IRspEnum{
    ADD_RELATION_FAIL(-1, "添加关系失败"),
    UPDATE_RELATION_FAIL(-1, "更新关系失败")
    ;
    private final int code;
    private final String message;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
