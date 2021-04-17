package org.mybeanframework.web.mvc.response;

import org.mybeanframework.web.mvc.annotation.RequestPath;
import org.mybeanframework.web.mvc.response.enums.ResponseTypeEnum;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 结果解析器处理
 *
 * @author herenpeng
 * @since 2020-12-16 11:07
 */
public class ResolverHandler {

    /**
     * 注解@RequestPath的字节码对象
     */

    public static void resolver(Object object, Method method, HttpServletResponse response) {
        if (method.isAnnotationPresent(RequestPath.class)) {
            RequestPath requestPath = method.getAnnotation(RequestPath.class);
            ResponseTypeEnum type = requestPath.type();
            switch (type) {
                case VIEW:
                    ViewResolver.resolver(object, response);
                    break;
                case TEXT:
                    TextResolver.resolver(object, response);
                    break;
                case JSON:
                    JsonResolver.resolver(object, response);
                    break;
                default:
            }
        } else {
            ViewResolver.resolver(object, response);
        }
    }

}
