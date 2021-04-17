package org.mybeanframework.web.mvc.servlet;

import org.mybeanframework.common.util.ClassUtils;
import org.mybeanframework.common.util.NumberUtils;
import org.mybeanframework.common.util.StringUtils;
import org.mybeanframework.web.mvc.MvcApplication;
import org.mybeanframework.web.mvc.annotation.RequestParam;
import org.mybeanframework.web.mvc.request.BeanAndMethod;
import org.mybeanframework.web.mvc.response.ResolverHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

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
        System.out.println(uri);
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
    private static Object invoke(Object bean, Method method, HttpServletRequest request, HttpServletResponse response) throws InvocationTargetException, IllegalAccessException {
        Object[] parameters = methodParameterHandler(method, request, response);
        return method.invoke(bean, parameters);
    }

    /**
     * 参数拦截器，获取参数并将参数放入invokeParameterMap中
     *
     * @param method   方法对象
     * @param request  HttpServletRequest对象
     * @param response HttpServletResponse对象
     * @return 参数个数
     */
    private static Object[] methodParameterHandler(Method method, HttpServletRequest request, HttpServletResponse response) {
        Parameter[] invokeParameters = method.getParameters();
        // 开辟一个长度为参数个数相同的数组
        Object[] parameters = new String[invokeParameters.length];
        for (int i = 0; i < invokeParameters.length; i++) {
            Parameter invokeParameter = invokeParameters[i];
            Class<?> invokeParameterType = invokeParameter.getType();
            Object parameter = null;
            // HttpServletRequest和HttpServletResponse的参数注入
            if (ClassUtils.isOrFromServletRequest(invokeParameterType)) {
                parameters[i] = request;
            } else if (ClassUtils.isOrFromServletResponse(invokeParameterType)) {
                parameters[i] = response;
                // 使用Map传参
            } else if (ClassUtils.isOrFromMap(invokeParameterType)) {
                Map<String, Object> map = new HashMap<>(16);
                Map<String, String[]> parameterMap = request.getParameterMap();
                for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                    String[] param = entry.getValue();
                    if (param.length == 1) {
                        String value = param[0];
                        if (StringUtils.isBooleanString(value)) {
                            map.put(entry.getKey(), Boolean.valueOf(value));
                        } else if (NumberUtils.inIntegerRange(value)) {
                            map.put(entry.getKey(), Integer.valueOf(value));
                        } else if (StringUtils.isNumber(value)) {
                            map.put(entry.getKey(), Long.valueOf(value));
                        } else {
                            map.put(entry.getKey(), value);
                        }
                    } else {
                        map.put(entry.getKey(), param);
                    }
                }
                parameters[i] = map;
            } else {
                // 普通参数，如果有@RequestParam注解，取值注入
                String parameterValue = null;
                RequestParam requestParam = invokeParameter.getAnnotation(RequestParam.class);
                if (requestParam != null) {
                    parameterValue = request.getParameter(requestParam.value());
                }
                parameters[i] = assignment(invokeParameterType, parameterValue);
            }
        }
        return parameters;
    }


    /**
     * 将String类型的参数转换为对应的参数类型
     *
     * @param invokeParameterType 参数类型字节码对象
     * @param parameterValue      String类型参数值
     * @return
     */
    private static Object assignment(Class<?> invokeParameterType, String parameterValue) {
        if (StringUtils.isEmpty(parameterValue)) {
            return null;
        }
        if (ClassUtils.isString(invokeParameterType)) {
            return parameterValue;
        } else if (ClassUtils.isByte(invokeParameterType) && StringUtils.isNumber(parameterValue)) {
            return Byte.valueOf(parameterValue);
        } else if (ClassUtils.isShort(invokeParameterType) && StringUtils.isNumber(parameterValue)) {
            return Short.valueOf(parameterValue);
        } else if (ClassUtils.isInteger(invokeParameterType) && StringUtils.isNumber(parameterValue)) {
            return Integer.valueOf(parameterValue);
        } else if (ClassUtils.isLong(invokeParameterType) && StringUtils.isNumber(parameterValue)) {
            return Long.valueOf(parameterValue);
        } else if (ClassUtils.isFloat(invokeParameterType) && StringUtils.isDecimal(parameterValue)) {
            return Float.valueOf(parameterValue);
        } else if (ClassUtils.isDouble(invokeParameterType) && StringUtils.isDecimal(parameterValue)) {
            return Double.valueOf(parameterValue);
        } else if (ClassUtils.isBoolean(invokeParameterType) && StringUtils.equalsTrue(parameterValue)) {
            return true;
        } else if (ClassUtils.isBoolean(invokeParameterType) && StringUtils.equalsFalse(parameterValue)) {
            return false;
        } else if (ClassUtils.isCharacter(invokeParameterType) && parameterValue.length() == 1) {
            return parameterValue.charAt(0);
        }
        return null;
    }

    /**
     * 执行方法的参数容器
     */
    private static final Map<Integer, Object> invokeParameterMap = new HashMap<>(16);

}
