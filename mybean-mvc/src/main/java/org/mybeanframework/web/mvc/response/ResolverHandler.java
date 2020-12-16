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

    public static final Class<RequestPath> REQUEST_PATH_CLASS = RequestPath.class;

    public static void resolver(Object object, Method method, HttpServletResponse response) {
        if (method.isAnnotationPresent(REQUEST_PATH_CLASS)) {
            RequestPath requestPath = method.getAnnotation(REQUEST_PATH_CLASS);
            ResponseTypeEnum type = requestPath.type();
            switch (type) {
                case VIEW:
                    ViewResolver.resolver(object, response);
                    break;
                case DATA:
                    DataResolver.resolver(object, response);
                    break;
                // case JSON:
                //     break;
                default:
            }
        } else {
            ViewResolver.resolver(object, response);
        }
    }

}
