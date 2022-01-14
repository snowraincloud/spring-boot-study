package com.example.web.validation;

import com.example.web.validation.annotation.ValidEnumValues;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

/**
 * <h1>自定义校验器</h1>
 *
 * @author wangjunhao
 **/
public class ValidEnumValuesConstraintValidator implements ConstraintValidator<ValidEnumValues, Integer> {
    /**
     * 存储枚举的值
     */
    private Set<Integer> ints=new HashSet<>();
    @Override
    public void initialize(ValidEnumValues constraintAnnotation) {
        for (int value : constraintAnnotation.values()) {
            ints.add(value);
        }
    }


    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        //判断是否包含这个值
        return value == null || ints.contains(value);
    }
}
