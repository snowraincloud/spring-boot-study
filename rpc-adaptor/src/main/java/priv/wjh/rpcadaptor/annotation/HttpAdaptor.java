package priv.wjh.rpcadaptor.annotation;

import org.springframework.core.annotation.AliasFor;
import priv.wjh.rpcadaptor.serialize.JacksonSerialization;
import priv.wjh.rpcadaptor.serialize.ParamSerialization;
import priv.wjh.rpcadaptor.serialize.ResultDeserialization;

import java.lang.annotation.*;

/**
 * http 协议客户端
 *
 * @author wangjunhao
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RpcAdaptor(protocol = "http")
public @interface HttpAdaptor {
    /**
     * Alias for {@link RpcAdaptor#value()}.
     */
    @AliasFor(annotation = RpcAdaptor.class)
    String value() default "";

    /**
     * Alias for {@link RpcAdaptor#uri()}.
     */
    @AliasFor(annotation = RpcAdaptor.class)
    String url() default "";

    @AliasFor(annotation = RpcAdaptor.class)
    Class<? extends ParamSerialization> paramSerialization() default JacksonSerialization.class;

    /**
     * 返参序列化器
     */
    @AliasFor(annotation = RpcAdaptor.class)
    Class<? extends ResultDeserialization> resultDeserialization() default JacksonSerialization.class;
}
