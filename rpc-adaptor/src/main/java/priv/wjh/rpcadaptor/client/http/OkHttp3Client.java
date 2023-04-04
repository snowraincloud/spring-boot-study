package priv.wjh.rpcadaptor.client.http;

import lombok.RequiredArgsConstructor;
import okhttp3.*;
import priv.wjh.rpcadaptor.serialize.JsonDeserialization;
import priv.wjh.rpcadaptor.serialize.JsonSerialization;

@RequiredArgsConstructor
public class OkHttp3Client implements HttpClient{
    public static final MediaType JSON = MediaType.get("application/json");

    private final OkHttpClient client;

    @Override
    public byte[] postForObject(String url, String data) throws Throwable {
        RequestBody body = RequestBody.create(data, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            assert response.body() != null;
            return response.body().bytes();
        }
    }
}
