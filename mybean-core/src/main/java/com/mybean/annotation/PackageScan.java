package com.mybean.annotation;

import java.lang.annotation.*;

/**
 * 注解，作用于类上，用于指定包扫描的路径
 *
 * @author herenpeng
 * @since 2020-2-5 8:59
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PackageScan {

    /**
     * value属性值，用于指定包扫描的路径
     * 如果value为空，那么就扫描该注解所在的那个类所在的包
     * 如果value有值，那么就扫描被指定的包
     *
     * @return 包扫描路径
     */
    String value() default "";
}
