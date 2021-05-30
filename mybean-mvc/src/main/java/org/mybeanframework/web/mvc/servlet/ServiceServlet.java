package org.mybeanframework.web.mvc.servlet;

import com.mybeanframework.server.annotation.Service;
import org.mybeanframework.web.mvc.MvcApplication;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 服务Servlet负责分发HTTP请求任务
 *
 * @author herenpeng
 * @since 2020-12-02 15:42
 */
@Service
@WebServlet("/*")
public class ServiceServlet extends HttpServlet {

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) {
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
