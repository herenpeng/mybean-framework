package org.mybeanframework.web.mvc.annotation;

import java.lang.annotation.*;

/**
 * 请求参数注解，通过该注解自动注入对应的参数，该注解只允许作用于方法参数上
 *
 * @author herenpeng
 * @since 2020-12-22 13:31
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestParam {

    /**
     * 参数名
     *
     * @return 参数名
     */
    String value() default "";
}
