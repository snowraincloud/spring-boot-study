package priv.wjh.rpcadaptor.proxy;

import java.lang.reflect.AnnotatedType;

public interface RpcAdaptorFactory {

    <T> T newInstance(Class<?> clz);

    boolean support(Class<?> clz);
}
