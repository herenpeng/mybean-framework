package org.mybeanframework.common.json.annotation;

import java.lang.annotation.*;

/**
 * Json工具格式化日期的格式化格式，只在实体类属性上可以注解
 *
 * @author herenpeng
 * @since 2020-12-23 10:26
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JsonDateFormat {

    /**
     * Json格式化日期类型，指定格式化格式
     *
     * @return
     */
    String value() default "yyyy-MM-dd HH:mm:ss";

}
