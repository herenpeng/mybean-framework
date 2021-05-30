package com.mybean;

import com.mybeanframework.server.annotation.HttpServer;
import com.mybeanframework.server.HttpServerRunner;

/**
 * @author herenpeng
 * @since 2021-05-29 22:39
 */
@HttpServer
public class Application {

    public static void main(String[] args) {
        HttpServerRunner.run(Application.class, args);
    }
}
