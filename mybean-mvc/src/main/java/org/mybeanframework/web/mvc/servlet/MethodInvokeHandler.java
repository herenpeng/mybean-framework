package org.mybeanframework.web.mvc.servlet;

import org.mybeanframework.web.mvc.MvcApplication;
import org.mybeanframework.web.mvc.request.BeanAndMethod;
import org.mybeanframework.web.mvc.response.ResolverHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 执行方法的处理器
 *
 * @author herenpeng
 * @since 2020-12-21 21:10
 */
public class MethodInvokeHandler {

    public static MvcApplication mvcApplication = null;

    /**
     * 执行请求方法
     *
     * @param request  HttpServletRequest对象
     * @param response HttpServletResponse对象
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static void handler(HttpServletRequest request, HttpServletResponse response) throws InvocationTargetException, IllegalAccessException {
        String uri = request.getRequestURI();
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
        Object object = invoke(bean, method, request, response);
        // 处理返回结果
        if (object != null) {
            ResolverHandler.resolver(object, method, response);
        }
    }

    /**
     * 执行方法
     *
     * @param bean     类对象
     * @param method   方法
     * @param request  HttpServletRequest对象
     * @param response HttpServletResponse对象
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Object invoke(Object bean, Method method, HttpServletRequest request, HttpServletResponse response) throws InvocationTargetException, IllegalAccessException {
        Object object = method.invoke(bean, request, response);
        return object;
    }

}
