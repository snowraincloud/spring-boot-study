package priv.wjh.rpcadaptor.serialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class JacksonSerialization implements JsonSerialization, JsonDeserialization {
    private final ObjectMapper objectMapper;

    @Override
    public String serialize(Object o) throws JsonProcessingException {
        return objectMapper.writeValueAsString(o);
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clz) throws IOException {
        return objectMapper.readValue(data, clz);
    }
}
