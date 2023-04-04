package priv.wjh.rpcadaptor.client;

import java.lang.reflect.Method;

public interface RpcAdaptorClient {
    Object invoke(Method method, Object[] args) throws Throwable;
}
