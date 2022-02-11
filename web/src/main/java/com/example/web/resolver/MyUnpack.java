package com.example.web.resolver;

import java.lang.annotation.*;

/**
 * <h1>解包标识</h1>
 *
 * @author wangjunhao
 **/
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyUnpack {
}
