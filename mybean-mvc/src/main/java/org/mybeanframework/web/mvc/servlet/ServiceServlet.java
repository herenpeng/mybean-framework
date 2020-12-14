package org.mybeanframework.web.mvc.servlet;

import org.mybeanframework.core.Application;
import org.mybeanframework.core.context.support.PropertiesApplication;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
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
     * 路径分隔符
     */
    private static final String SEPARATE = "/";
    /**
     * 常量，静态资源路径包
     */
    private static final String STATIC = "static";
    /**
     * 常量：tomcat页面标签的小图标请求路径
     */
    private static final String ICO = "/favicon.ico";
    /**
     * 常量，默认的视图解析前缀
     */
    private static final String DEFAULT_VIEW_PREFIX = "static/";
    /**
     * 类成员变量，框架的核心容器
     */
    private Application application;

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
            if (!uri.contains(STATIC) && !uri.contains(ICO)) {
                String projectName = request.getContextPath();
                if (projectName != null && projectName.length() > 0) {
                    uri = uri.substring(uri.indexOf(projectName) + projectName.length());
                }
                String className = uri.substring(uri.indexOf(SEPARATE) + 1, uri.lastIndexOf(SEPARATE));
                String methodName = uri.substring(uri.lastIndexOf(SEPARATE) + 1);
                Object applicationBean = application.getBean(className);
                Method method = applicationBean.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
                Object object = method.invoke(applicationBean, request, response);
                if (object instanceof String) {
                    ServletOutputStream out = response.getOutputStream();
                    String viewPath = (String) object;
                    InputStream is = this.getClass().getClassLoader().getResourceAsStream(DEFAULT_VIEW_PREFIX + viewPath);
                    byte[] bytes = new byte[1024];
                    int len = 0;
                    while ((len = is.read(bytes)) != -1) {
                        out.write(bytes, 0, len);
                    }
                    out.flush();
                    out.close();
                    is.close();
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
        application = new PropertiesApplication();
    }

    @Override
    public void destroy() {
    }
}
