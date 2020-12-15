package org.mybeanframework.web.mvc.annotation;

import java.lang.annotation.*;

/**
 * 注解，作用于类和方法上，用于指定被访问路径
 *
 * @author herenpeng
 * @since 2020-2-4 8:23
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestPath {

    /**
     * value属性，用于指定访问路径
     * 有该属性则访问路径为属性值
     * 没有该属性则默认为空
     *
     * @return id名称
     */
    String value() default "";

}
