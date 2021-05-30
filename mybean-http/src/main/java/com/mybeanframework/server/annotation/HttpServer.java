package com.mybeanframework.server.annotation;

import java.lang.annotation.*;

/**
 * @author herenpeng
 * @since 2021-05-30 12:55
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

