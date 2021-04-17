package org.mybeanframework.web.mvc;

import org.mybeanframework.common.constant.StringConst;
import org.mybeanframework.common.util.StringUtils;
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


    public void start() {
        // 启动MyBean框架核心启动器，基于XML配置的方式
        new XmlApplication();
        Set<Map.Entry<String, Object>> entries = beanCore.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            Object beanObject = entry.getValue();
            Class<?> beanClass = beanObject.getClass();
            if (beanClass.isAnnotationPresent(RequestPath.class)) {
                RequestPath beanRequestPath = beanClass.getAnnotation(RequestPath.class);
                String beanPath = beanRequestPath.value();
                if (!beanPath.startsWith(StringConst.URL_SEPARATOR)) {
                    beanPath = StringConst.URL_SEPARATOR + beanPath;
                }
                Method[] methods = beanClass.getDeclaredMethods();
                for (Method method : methods) {
                    StringBuffer requestPath = new StringBuffer();
                    if (method.isAnnotationPresent(RequestPath.class)) {
                        RequestPath methodRequestPath = method.getAnnotation(RequestPath.class);
                        String methodPath = methodRequestPath.value();
                        if (StringUtils.isEmpty(methodPath) || StringConst.URL_SEPARATOR.equals(methodPath)) {
                            requestPath.append(beanPath);
                        } else {
                            if ((methodPath.startsWith(StringConst.URL_SEPARATOR))) {
                                if (StringConst.URL_SEPARATOR.equals(beanPath)) {
                                    requestPath.append(methodPath);
                                } else {
                                    requestPath.append(beanPath).append(methodPath);
                                }
                            } else {
                                if (StringConst.URL_SEPARATOR.equals(beanPath)) {
                                    requestPath.append(beanPath).append(methodPath);
                                } else {
                                    requestPath.append(beanPath).append(StringConst.URL_SEPARATOR).append(methodPath);
                                }
                            }
                        }
                    } else {
                        requestPath.append(beanPath);
                    }
                    BeanAndMethod beanAndMethod = new BeanAndMethod(beanObject, method);
                    requestResolver.put(requestPath.toString(), beanAndMethod);
                }
            }
        }
    }

    public MvcApplication() {
        start();
    }

}
