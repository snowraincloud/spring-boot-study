package priv.wjh.rpcadaptor.serialize;

import java.io.IOException;

public interface ResultDeserialization {

    <T> T deserialize(byte[] data, Class<T> clz) throws Exception;
}
