package com.example.annotation.annotatio;


import com.example.annotation.config.ValueExample;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
@DisplayName("@Value 注解")
@SpringBootTest
public class ValueTest {
    @Autowired
    private ValueExample valueExample;

    @DisplayName("${}")
    @Test
    void test$(){
        assertEquals("snow-line", valueExample.getName());
        assertEquals("snow", valueExample.getAuthor());

        assertThat(valueExample.getZeroToTen(), allOf(greaterThanOrEqualTo(0), lessThanOrEqualTo(10)));
        assertThat(valueExample.getTenToTwenty(), allOf(greaterThanOrEqualTo(10), lessThanOrEqualTo(20)));

        System.out.println(valueExample.getValue());
    }

    @DisplayName("#{}")
    @Test
    void testHashtag(){
        assertEquals("snow-line", valueExample.getName1());
    }


}
