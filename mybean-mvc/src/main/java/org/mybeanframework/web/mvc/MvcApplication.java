package org.mybeanframework.web.mvc;

import org.mybeanframework.core.Application;
import org.mybeanframework.core.context.support.PropertiesApplication;
import org.mybeanframework.core.context.support.XmlApplication;
import org.mybeanframework.web.mvc.annotation.RequestPath;
import org.mybeanframework.web.mvc.request.AbstractRequestResolver;
import org.mybeanframework.web.mvc.request.BeanAndMethod;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * MVC框架启动器
 *
 * @author herenpeng
 * @since 2020-12-15 15:28
 */
public class MvcApplication extends AbstractRequestResolver {

    /**
     * 定义一个常量，@RequestPath注解的字节码对象
     */
    public static final Class<RequestPath> REQUEST_PATH_CLASS = RequestPath.class;

    /**
     * 路径分隔符
     */
    public static final String URL_SEPARATE = "/";


    public void start() {
        // 启动MyBean框架核心启动器，基于XML配置的方式
        new XmlApplication();
        Set<Map.Entry<String, Object>> entries = beanCore.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            Object beanObject = entry.getValue();
            Class<?> beanClass = beanObject.getClass();
            if (beanClass.isAnnotationPresent(REQUEST_PATH_CLASS)) {
                RequestPath beanRequestPath = beanClass.getAnnotation(REQUEST_PATH_CLASS);
                String path = beanRequestPath.value();
                if (!path.startsWith(URL_SEPARATE)) {
                    path = URL_SEPARATE + path;
                }
                Method[] methods = beanClass.getMethods();
                for (Method method : methods) {
                    String requestPath = "";
                    if (method.isAnnotationPresent(REQUEST_PATH_CLASS)) {
                        RequestPath methodRequestPath = method.getAnnotation(REQUEST_PATH_CLASS);
                        String methodPath = methodRequestPath.value();
                        if (!methodPath.startsWith(URL_SEPARATE)) {
                            methodPath = URL_SEPARATE + methodPath;
                        }
                        requestPath = path + methodPath;
                    } else {
                        requestPath = path;
                    }
                    BeanAndMethod beanAndMethod = new BeanAndMethod(beanObject, method);
                    requestResolver.put(requestPath, beanAndMethod);
                }
            }
        }
    }

}
