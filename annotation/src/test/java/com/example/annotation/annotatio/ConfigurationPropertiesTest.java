package com.example.annotation.annotatio;

import com.example.annotation.config.ConfigurationPropertiesExample;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
@DisplayName("@ConfigurationProperties 注解")
@SpringBootTest
public class ConfigurationPropertiesTest {

    @Autowired
    private ConfigurationPropertiesExample properties;

    @Test
    void test(){
        assertEquals("wjh", ConfigurationPropertiesExample.LASTNAME);
        assertEquals(23, properties.getAge());
        assertEquals(false, properties.getBoss());
        assertEquals(812736000000L, properties.getBirth().getTime());
        assertEquals("v1", properties.getMaps().get("k1"));
        assertEquals("v2", properties.getMaps().get("k2"));
        assertEquals("list1", properties.getLists().get(0));
        assertEquals("list2", properties.getLists().get(1));
        assertEquals("dog", properties.getDog().getName());
        assertEquals(2, properties.getDog().getAge());

    }
}
