package com.mybeanframework.server;

import com.mybeanframework.http.Request;
import com.mybeanframework.http.Response;
import com.mybeanframework.server.annotation.HttpServer;
import com.mybeanframework.server.util.HttpServerThreadPool;
import com.mybeanframework.server.util.Logger;
import com.mybeanframework.servlet.HttpServletRegistry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author herenpeng
 * @since 2021-04-17 18:10
 */
public class HttpServerRunner {

    public static final HttpServerRunner runner = new HttpServerRunner();
    public static final HttpServletRegistry registry = new HttpServletRegistry();

    public static final Logger LOGGER = Logger.getLogger();


    public static void run(final Class<?> clazz, final String[] args) {
        run(clazz);
    }

    public static void run(final Class<?> serverClass) {
        if (serverClass.isAnnotationPresent(HttpServer.class)) {
            HttpServer httpServer = serverClass.getAnnotation(HttpServer.class);
            // 获取端口号
            int port = httpServer.port();
            try {
                runner.createSocket(port);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void createSocket(final int port) throws IOException {
        final ServerSocket server = new ServerSocket(port);
        try {
            while (true) {
                final Socket socket = server.accept();
                HttpServerThreadPool.execute(() -> {
                    try {
                        HttpServletRequest request = new Request(socket);

                        HttpServletResponse response = new Response(socket);

                        registry.service(request, response);
                        socket.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (Exception e) {
            LOGGER.error("服务启动异常");
            e.printStackTrace();
        } finally {
            LOGGER.info("服务销毁");
            registry.destroy();
        }

    }

}
