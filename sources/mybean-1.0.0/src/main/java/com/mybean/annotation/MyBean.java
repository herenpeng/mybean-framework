package com.mybean.annotation;

import java.lang.annotation.*;

/**
 * 注解，作用于类上，用于指定被MyBean管理的类
 *
 * @author hrp
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyBean {

    /**
     * value属性，用于指定被管理的类的id
     * 有该属性则id为属性值
     * 没有该属性则默认配置id，id默认为类的首字母小写
     *
     * @return id名称
     */
    String value() default "";

}
