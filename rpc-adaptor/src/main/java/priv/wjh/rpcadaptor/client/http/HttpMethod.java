package priv.wjh.rpcadaptor.client.http;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import priv.wjh.rpcadaptor.serialize.JsonDeserialization;
import priv.wjh.rpcadaptor.serialize.JsonSerialization;

public record HttpMethod(String url, JsonSerialization serialization, JsonDeserialization deserialization) {
}
