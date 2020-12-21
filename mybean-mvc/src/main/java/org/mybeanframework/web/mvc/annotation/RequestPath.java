package org.mybeanframework.web.mvc.annotation;

import org.mybeanframework.web.mvc.response.enums.ResponseTypeEnum;

import java.lang.annotation.*;

/**
 * 注解，作用于类和方法上，用于指定被访问路径
 *
 * @author herenpeng
 * @since 2020-12-15 8:23
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
     * @return 访问路径
     */
    String value() default "";

    /**
     * 返回类型，默认为视图
     *
     * @return 响应类型
     */
    ResponseTypeEnum type() default ResponseTypeEnum.VIEW;

}
