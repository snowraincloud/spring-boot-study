package priv.wjh.rpcadaptor.proxy;

import lombok.RequiredArgsConstructor;
import priv.wjh.rpcadaptor.client.RpcAdaptorClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@RequiredArgsConstructor
public class RpcAdaptorInvocationHandler implements InvocationHandler {
    private final RpcAdaptorClient rpcAdaptorClient;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("equals".equals(method.getName())) {
            throw new UnsupportedOperationException();
        } else if ("hashCode".equals(method.getName())) {
            throw new UnsupportedOperationException();
        } else if ("toString".equals(method.getName())) {
            throw new UnsupportedOperationException();
        }
        return rpcAdaptorClient.invoke(method, args);
    }
}
