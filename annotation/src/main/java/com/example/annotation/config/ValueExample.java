package com.example.annotation.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * //TODO
 *
 *  <h1>@Value的值有两类：</h1>
 *  <p>使用@Value注解时，需要在对应的class实体类上添加 @Component 注解，把实体类装配进容器才能正常使用。</p>
 *  <li>${ property : default_value } 将 application.yml/application.properties配置文件的参数值读取出来</li>
 *  <li>#{ obj.property? :default_value } 表示SpEL表达式通常用来获取bean的属性，或者调用bean的某个方法。当然还有可以表示常量</li>
 * @author wangjunhao
 **/
@Getter
@Setter
@Component
public class ValueExample {
    @Value("${customize.name}")
    private String name;

    @Value("#{valueExample.name}")
    private String name1;

    @Value("${customize.value}")
    private String value;
    @Value("${customize.zero-to-ten}")
    private int zeroToTen;
    @Value("${customize.ten-to-twenty}")
    private int tenToTwenty;

    private static String author;
    @Value("${customize.author}")
    public void setAuthor(String author) {
        ValueExample.author = author;
    }
    public String getAuthor() {
        return ValueExample.author;
    }
}
