package com.mybeanframework.http.annotation;

import java.lang.annotation.*;

/**
 * @author herenpeng
 * @since 2021-04-17 17:51
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HttpServer {

    /**
     * 端口号
     *
     * @return
     */
    int port() default 8888;

}
