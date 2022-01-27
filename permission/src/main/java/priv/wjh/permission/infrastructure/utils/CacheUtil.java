package priv.wjh.permission.infrastructure.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * <h1>缓存工具类</h1>
 *
 * @author wangjunhao
 **/
@Component
@Configuration
public class CacheUtil {

    private final String PREFIX = "permission_";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;


    public Optional<String> get(String key){
        return Optional.ofNullable(redisTemplate.opsForValue().get(PREFIX + key));
    }

    public <T> void remove(T key){
        redisTemplate.delete(PREFIX + key);
    }

    public <T> void put(String key, T value, long expired) throws JsonProcessingException {
        if(value instanceof String){
            redisTemplate.opsForValue().set(PREFIX + key, (String) value, expired, TimeUnit.SECONDS);
        }else {
            redisTemplate.opsForValue().set(PREFIX + key, objectMapper.writeValueAsString(value), expired, TimeUnit.SECONDS);
        }
    }

}
