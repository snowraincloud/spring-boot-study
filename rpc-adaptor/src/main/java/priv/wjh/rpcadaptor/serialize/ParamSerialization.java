package priv.wjh.rpcadaptor.serialize;

/**
 * 参数序列化
 */
public interface ParamSerialization<T, V> {
    V serialize(T t) throws Exception;
}
