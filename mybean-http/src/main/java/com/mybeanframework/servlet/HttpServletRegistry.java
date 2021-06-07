package com.mybeanframework.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author herenpeng
 * @since 2021-05-30 13:28
 */
public class HttpServletRegistry {

    private HttpServlet httpServlet;

    public static final String serviceServletFullClassName = "org.mybeanframework.web.mvc.servlet.ServiceServlet";

    public void registry() {
        try {
            Class<? extends HttpServlet> httpServletClass = (Class<? extends HttpServlet>) Class.forName(serviceServletFullClassName);
            this.httpServlet = httpServletClass.newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

    }


    public HttpServletRegistry() {
        registry();
        try {
            // 执行 servlet 的 init 方法
            if (httpServlet != null) {
                httpServlet.init();
            }
        } catch (ServletException e) {
            e.printStackTrace();
        }

    }

    /**
     * Servlet 的销毁方法
     */
    public void destroy() {
        if (httpServlet != null) {
            httpServlet.destroy();
        }
    }


    /**
     * 执行方法
     *
     * @param request
     * @param response
     */
    public void service(HttpServletRequest request, HttpServletResponse response) {
        try {
            this.httpServlet.service(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

}
