package com.example.annotation.config;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * <p>宽松的绑定</p>
 * <li>person.firstName:使用标准方式</li>
 * <li>person.first-name:大写用-</li>
 * <li>person.first_name:大写用_</li>
 * <li>PERSON_FIRST_NAME: 系统属性推荐使用这种写法</li>
 * <hr>
 * <p>JSR303数据校验（@Validation）</p>
 * <hr>
 * <p>支持转换器</p>
 * <p>通过实现Converter接口自定义转换器</p>
 * <hr>
 * <p>自动生成配置元数据</p>
 * @author wangjunhao
 **/
@Setter
@Getter
@Validated
@Configuration
@ConfigurationProperties(prefix = "person")
public class ConfigurationPropertiesExample {
    public static String LASTNAME;

    @Range(message = "年龄必须在1到120之间。", min = 1, max = 120)
    private int age;

    private Boolean boss;

    private Date birth;

    private Map<String,Object> maps;

    private List<String> lists;

    private Dog dog;

    @Getter
    @Setter
    public static class Dog {

        private String name;

        private int age;

    }

    public void setLASTNAME(String LASTNAME) {
        ConfigurationPropertiesExample.LASTNAME = LASTNAME;
    }
}
