package com.example.web.validation;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * //TODO
 * <p>Hibernate Validator的校验模式有两种情况</p>
 * <ol>
 * <li>快速失败（验证失败一个，快速返回错误信息）</li>
 * <li>普通模式（默认，会校验完所有的属性，然后返回所有的验证失败信息）<br>
 * 我们一般使用第一种，SpringBoot需要开启配置</li>
 * </ol>
 * @author wangjunhao
 **/
//@Configuration
public class ValidConfig {
    @Bean
    public Validator validator(){
        ValidatorFactory validatorFactory = Validation
                .byProvider( HibernateValidator.class )
                .configure()
                // 快速失败
                .failFast( true )
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }
}
