package com.example.web.validation.annotation;


import com.example.web.validation.ValidEnumValuesConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <h1>自定义校验注解</h1>
 *
 * @author wangjunhao
 **/
@Documented
@Constraint(validatedBy = { ValidEnumValuesConstraintValidator.class})
@Target({ METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface ValidEnumValues {
    /**
     * 提示消息
     */
    String message() default "传入的值不在范围内";
    /**
     * 分组
     * @return
     */
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
    /**
     * 可以传入的值
     * @return
     */
    int[] values() default { };
}
