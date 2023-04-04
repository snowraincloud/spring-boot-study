package priv.wjh.rpcadaptor.client.http;

import org.springframework.lang.Nullable;

public interface HttpClient {
    byte[] postForObject(String url, String data) throws Throwable;
}
