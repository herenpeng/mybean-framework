package org.mybeanframework.web.mvc;

import org.mybeanframework.core.context.support.XmlApplication;
import org.mybeanframework.web.mvc.annotation.RequestPath;
import org.mybeanframework.web.mvc.request.AbstractRequestResolver;

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



    public void start() {
        // 启动MyBean框架核心启动器，基于XML配置的方式
        new XmlApplication();
        Set<Map.Entry<String, Object>> entries = beanCore.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            Object beanObject = entry.getValue();
            Class<?> beanClass = beanObject.getClass();
            if (beanClass.isAnnotationPresent(REQUEST_PATH_CLASS)) {
                RequestPath requestPath = beanClass.getAnnotation(REQUEST_PATH_CLASS);
            }

        }
    }

}
