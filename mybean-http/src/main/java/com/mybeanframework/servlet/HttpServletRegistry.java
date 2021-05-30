package com.mybeanframework.servlet;



import com.mybeanframework.server.annotation.Service;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.ServiceLoader;

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
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }


    public HttpServletRegistry() {
        registry();
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
