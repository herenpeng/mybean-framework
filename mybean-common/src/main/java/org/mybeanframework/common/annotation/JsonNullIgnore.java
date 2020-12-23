package org.mybeanframework.common.annotation;

import java.lang.annotation.*;

/**
 * Json格式化时，使用该注解，可以将值为null的属性，忽略转换为json属性，只在实体类属性上可以注解
 *
 * @author herenpeng
 * @since 2020-12-23 11:54
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JsonNullIgnore {
}
