package org.mybeanframework.core.filter;

import org.mybeanframework.core.context.Application;
import org.mybeanframework.core.context.support.AnnotationApplication;
import org.mybeanframework.core.context.support.PropertiesApplication;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 前端核心控制器，负责分发任务，释放静态资源
 *
 * @author herenpeng
 * @since 2020-2-5 15:09
 */
public class MyBeanControllerFilter implements Filter {
    private static final long serialVersionUID = 1L;

    /**
     * 路径分隔符
     */
    private final static String SEPARATE = "/";
    /**
     * 常量，静态资源路径包
     */
    private final static String STATIC = "static";
    /**
     * 常量：tomcat页面标签的小图标请求路径
     */
    private final static String ICO = "/favicon.ico";
    /**
     * 常量，默认的视图解析前缀
     */
    private final static String DEFAULT_VIEW_PREFIX = ".." + SEPARATE + STATIC + SEPARATE;
    /**
     * 类成员变量，框架的核心容器
     */
    private Application application;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        try {
            if (!uri.contains(STATIC) && !uri.contains(ICO)) {
                String projectName = req.getContextPath();
                if (projectName != null && projectName.length() > 0) {
                    uri = uri.substring(uri.indexOf(projectName) + projectName.length());
                }
                String className = uri.substring(uri.indexOf(SEPARATE) + 1, uri.lastIndexOf(SEPARATE));
                String methodName = uri.substring(uri.lastIndexOf(SEPARATE) + 1);
                Object object = application.getBean(className);
                Method method = object.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
                Object viewName = method.invoke(object, req, resp);
                if (viewName instanceof String) {
                    req.getRequestDispatcher(DEFAULT_VIEW_PREFIX + viewName).forward(req, resp);
                }
            } else {
                chain.doFilter(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 常量：框架配置文件名的参数名
     */
    private final static String MYBEAN_CONFIG_LOCATION = "ConfigLocation";
    /**
     * 常量：框架包扫描路径的参数名
     */
    private final static String MYBEAN_PACKAGE_SCAN = "PackageScan";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String ConfigLocation = filterConfig.getInitParameter(MYBEAN_CONFIG_LOCATION);
        String PackageScan = filterConfig.getInitParameter(MYBEAN_PACKAGE_SCAN);
        if (ConfigLocation != null && ConfigLocation.length() > 0) {
            application = new PropertiesApplication(ConfigLocation);
        } else if (PackageScan != null && PackageScan.length() > 0) {
            application = new AnnotationApplication(PackageScan);
        } else {
            application = new PropertiesApplication();
        }
    }

    @Override
    public void destroy() {
        if (application != null) {
            application.close();
            application = null;
        }
    }

}
