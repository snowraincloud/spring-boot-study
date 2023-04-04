package priv.wjh.rpcadaptor.proxy;


import lombok.RequiredArgsConstructor;
import priv.wjh.rpcadaptor.annotation.HttpAdaptor;
import priv.wjh.rpcadaptor.client.RpcAdaptorClient;
import priv.wjh.rpcadaptor.client.http.HttpAdaptorClient;
import priv.wjh.rpcadaptor.client.http.HttpClient;
import priv.wjh.rpcadaptor.client.http.HttpMethod;
import priv.wjh.rpcadaptor.serialize.JsonDeserialization;
import priv.wjh.rpcadaptor.serialize.JsonSerialization;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;

@RequiredArgsConstructor
public class HttpAdaptorFactory implements RpcAdaptorFactory{
    private final HttpClient httpClient;
    private final HashMap<Class<?>, JsonSerialization> serializationHashMap;
    private final HashMap<Class<?>, JsonDeserialization> deserializationHashMap;

    @Override
    public <T> T newInstance(Class<?> clz) {
        HashMap<Method, HttpMethod> map = new HashMap<>();
        for (Method method : clz.getMethods()) {
            HttpAdaptor annotation = method.getAnnotation(HttpAdaptor.class);
            if (annotation != null){
                String url = annotation.url();
                JsonSerialization jsonSerialization = serializationHashMap.get(annotation.paramSerialization());
                JsonDeserialization jsonDeserialization = deserializationHashMap.get(
                        annotation.resultDeserialization());
                map.put(method, new HttpMethod(url, jsonSerialization, jsonDeserialization));
            }
        }
        RpcAdaptorClient rpcAdaptorClient = new HttpAdaptorClient(httpClient, map);
        RpcAdaptorInvocationHandler handler = new RpcAdaptorInvocationHandler(rpcAdaptorClient);

        T proxy = (T) Proxy.newProxyInstance(handler.getClass().getClassLoader(),
                                             new Class<?>[] {clz}, handler);
        return proxy;
    }

    @Override
    public boolean support(Class<?> clz) {
       return clz.equals(HttpAdaptor.class);
    }
}
