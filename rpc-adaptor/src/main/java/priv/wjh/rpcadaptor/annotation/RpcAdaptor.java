package priv.wjh.rpcadaptor.annotation;


import org.springframework.core.annotation.AliasFor;
import priv.wjh.rpcadaptor.serialize.DefaultSerialization;
import priv.wjh.rpcadaptor.serialize.ParamSerialization;
import priv.wjh.rpcadaptor.serialize.ResultDeserialization;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RpcAdaptor {

    String protocol() default "";

    @AliasFor("uri")
    String value() default "";

    @AliasFor("value")
    String uri() default "";

    /**
     * 入参序列化器
     */
    Class<? extends ParamSerialization> paramSerialization() default DefaultSerialization.class;

    /**
     * 返参序列化器
     */
    Class<? extends ResultDeserialization> resultDeserialization() default DefaultSerialization.class;
}
