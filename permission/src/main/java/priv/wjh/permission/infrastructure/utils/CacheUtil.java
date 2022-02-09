package priv.wjh.permission.infrastructure.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import priv.wjh.permission.infrastructure.enums.rsp.FailRspEnum;
import priv.wjh.permission.infrastructure.exception.PermissionException;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * <h1>缓存工具类</h1>
 *
 * @author wangjunhao
 **/
@Component
@Slf4j
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

    public <T> void put(String key, T value, long expired) {
        if(value instanceof String){
            redisTemplate.opsForValue().set(PREFIX + key, (String) value, expired, TimeUnit.SECONDS);
        }else {
            try {
                redisTemplate.opsForValue().set(PREFIX + key, objectMapper.writeValueAsString(value), expired, TimeUnit.SECONDS);
            } catch (JsonProcessingException e) {
                logger.info("json转换失败: ", e);
                throw new PermissionException(FailRspEnum.JSON_TRANSFORM_FAIL);
            }
        }
    }

}
