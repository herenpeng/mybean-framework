package com.mybean.mvc;

import com.mybean.context.Application;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 前端核心控制器，负责分发任务，但是该版本尚不成熟，会拦截静态资源
 *
 * @author hrp
 * @date 2020/2/5 15:09
 */
public class MyBeanControllerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * 路径分隔符
     */
    private final static String SEPARATE = "/";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Application application = (Application) getServletConfig().getServletContext().getAttribute("application");
            String uri = req.getRequestURI();
            String projectName = req.getContextPath();
            if (projectName != null && projectName.length() > 0) {
                uri = uri.substring(uri.indexOf(projectName) + projectName.length());
            }
            String className = uri.substring(uri.indexOf(SEPARATE)+1, uri.lastIndexOf(SEPARATE));
            String methodName = uri.substring(uri.lastIndexOf(SEPARATE) + 1);
            Object object = application.getBean(className);
            Method method = object.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(object, req, resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
