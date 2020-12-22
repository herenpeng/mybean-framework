package org.mybeanframework.web.mvc.servlet;

import org.mybeanframework.web.mvc.MvcApplication;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 服务Servlet负责分发HTTP请求任务
 *
 * @author herenpeng
 * @since 2020-12-02 15:42
 */
@WebServlet("/*")
public class ServiceServlet implements Servlet {

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        try {
            // 如果是静态资源请求
            String uri = request.getRequestURI();
            if (StaticResourcesHandler.isStaticResources(uri)) {
                StaticResourcesHandler.handler(uri, response);
            } else {
                MethodInvokeHandler.handler(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void init(ServletConfig servletConfig) {
        MethodInvokeHandler.mvcApplication = new MvcApplication();
    }

    @Override
    public void destroy() {
    }
}
