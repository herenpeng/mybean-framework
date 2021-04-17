package com.mybeanframework.http.server;

import com.mybeanframework.http.annotation.HttpServer;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.ServerSocket;

/**
 * @author herenpeng
 * @since 2021-04-17 18:10
 */
public class HttpServerRunner {

    public static void run(Class<?> clazz, String[] args) {
        run(clazz);
    }


    public static void run(Class<?> clazz) {
        if (clazz.isAnnotationPresent(HttpServer.class)) {
            HttpServer httpServer = clazz.getAnnotation(HttpServer.class);
            // 获取端口号
            int port = httpServer.port();

        }
    }

    public static void createSocket(int port) throws IOException {
        ServerSocket socket = new ServerSocket(port);

    }

}
