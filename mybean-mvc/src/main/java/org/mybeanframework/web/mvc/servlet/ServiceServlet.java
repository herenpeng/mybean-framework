package org.mybeanframework.web.mvc.servlet;

import org.mybeanframework.web.mvc.MvcApplication;
import org.mybeanframework.web.mvc.request.BeanAndMethod;
import org.mybeanframework.web.mvc.response.ResolverHandler;
import org.mybeanframework.web.mvc.response.ViewResolver;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 服务Servlet负责分发HTTP请求任务
 *
 * @author herenpeng
 * @since 2020-12-02 15:42
 */
@WebServlet("/*")
public class ServiceServlet implements Servlet {

    /**
     * 文件后缀分割符号
     */
    public static final String FILE_SUFFIX_SEPARATOR = ".";
    /**
     * 静态资源路径标识
     */
    public static final String STATIC_RESOURCES_FLAG = "@";

    /**
     * Mvc核心启动器
     */
    MvcApplication mvcApplication;

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String uri = request.getRequestURI();
        try {
            if (uri.contains(FILE_SUFFIX_SEPARATOR) &&
                    StaticResourcesHandler.isStaticResources(uri.substring(uri.lastIndexOf(FILE_SUFFIX_SEPARATOR)))) {
                if (uri.contains(STATIC_RESOURCES_FLAG)) {
                    String viewName = uri.substring(uri.indexOf(STATIC_RESOURCES_FLAG) + 1);
                    ViewResolver.resolver(viewName, response);
                } else {
                    ViewResolver.resolver(uri, response);
                }
            } else {
                // 去掉请求中的项目名称
                String projectName = request.getContextPath();
                if (projectName != null && projectName.length() > 0) {
                    uri = uri.substring(uri.indexOf(projectName) + projectName.length());
                }
                // 获取请求路径对应的类和方法
                BeanAndMethod beanAndMethod = mvcApplication.requestResolver(uri);
                Object bean = beanAndMethod.getBean();
                Method method = beanAndMethod.getMethod();
                // 执行对应的方法
                Object object = method.invoke(bean, request, response);
                // 处理返回结果
                if (object != null) {
                    ResolverHandler.resolver(object, method, response);
                }
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
        mvcApplication = new MvcApplication();
        mvcApplication.start();
    }

    @Override
    public void destroy() {
    }
}
