package com.mybeanframework.server.annotation;

import java.lang.annotation.*;

/**
 * @author herenpeng
 * @since 2021-05-30 13:42
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Service {
}
