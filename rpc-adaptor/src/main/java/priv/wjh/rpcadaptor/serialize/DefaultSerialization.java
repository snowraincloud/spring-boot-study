package priv.wjh.rpcadaptor.serialize;

public class DefaultSerialization implements ParamSerialization<Object, Object>, ResultDeserialization{
    @Override
    public Object serialize(Object o) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clz) throws Exception {
        throw new UnsupportedOperationException();
    }
}
