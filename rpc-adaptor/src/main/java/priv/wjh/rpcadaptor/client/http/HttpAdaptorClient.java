package priv.wjh.rpcadaptor.client.http;

import lombok.RequiredArgsConstructor;
import priv.wjh.rpcadaptor.client.RpcAdaptorClient;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

@RequiredArgsConstructor
public class HttpAdaptorClient implements RpcAdaptorClient {
    private final HttpClient httpClient;
    private final Map<Method, HttpMethod> dispatch;

    @Override
    public Object invoke(Method method, Object[] args) throws Throwable {
        if (args.length != 1) {
            throw new UnsupportedOperationException("invalid parameter, only one parameter is supported");
        }
        Class<?> returnType = method.getReturnType();
        HttpMethod httpMethod = dispatch.get(method);
        String param = httpMethod.serialization().serialize(args[0]);
        byte[] bytes = httpClient.postForObject(httpMethod.url(), param);
        return httpMethod.deserialization().deserialize(bytes, method.getReturnType());
    }
}
