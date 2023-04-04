package priv.wjh.rpcadaptor.proxy;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.Test;
import priv.wjh.rpcadaptor.annotation.HttpAdaptor;
import priv.wjh.rpcadaptor.client.http.HttpClient;
import priv.wjh.rpcadaptor.client.http.OkHttp3Client;
import priv.wjh.rpcadaptor.serialize.JacksonSerialization;
import priv.wjh.rpcadaptor.serialize.JsonDeserialization;
import priv.wjh.rpcadaptor.serialize.JsonSerialization;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HttpAdaptorFactoryTest {

    public interface TodoClient {

        @HttpAdaptor(url = "https://f099ccb4-843e-4044-9c54-7be2bb227f2e.mock.pstmn.io/todo")
        Todo save(Todo todo);
    }


    @Test
    void newInstance() {
        ObjectMapper objectMapper = new ObjectMapper();
        HttpClient httpClient = new OkHttp3Client(new OkHttpClient());
        JacksonSerialization jacksonSerialization = new JacksonSerialization(objectMapper);
        HashMap<Class<?>, JsonSerialization> serializationHashMap = new HashMap<>();
        serializationHashMap.put(JacksonSerialization.class, jacksonSerialization);
        HashMap<Class<?>, JsonDeserialization> deserializationHashMap = new HashMap<>();
        deserializationHashMap.put(JacksonSerialization.class, jacksonSerialization);

        HttpAdaptorFactory httpAdaptorFactory = new HttpAdaptorFactory(httpClient, serializationHashMap, deserializationHashMap);

        TodoClient todoClient = httpAdaptorFactory.newInstance(TodoClient.class);
        System.out.println(todoClient.save(new Todo(1L, 2L, "test", true)));
    }
}
