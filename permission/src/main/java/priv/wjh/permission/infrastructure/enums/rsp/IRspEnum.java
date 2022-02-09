package priv.wjh.permission.infrastructure.enums.rsp;


/**
 * <h1>返回值状态码与消息</h1>
 * @author wangjunhao
 */
public interface IRspEnum {
    /**
     * <h2>状态码</h2>
     * @return 状态码
     */
    int getCode();

    /**
     * <h2>提示信息</h2>
     * @return 信息
     */
    String getMessage();
}
