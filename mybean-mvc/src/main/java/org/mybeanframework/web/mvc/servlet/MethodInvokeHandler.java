package org.mybeanframework.web.mvc.servlet;

import org.mybeanframework.web.mvc.MvcApplication;
import org.mybeanframework.web.mvc.annotation.RequestParam;
import org.mybeanframework.web.mvc.annotation.RequestPath;
import org.mybeanframework.web.mvc.request.BeanAndMethod;
import org.mybeanframework.web.mvc.response.ResolverHandler;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
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
        Object object;
        switch (methodParameterHandler(method, request, response)) {
            case 1:
                object = method.invoke(bean, invokeParameterMap.get(0));
                break;
            case 2:
                object = method.invoke(bean, invokeParameterMap.get(0), invokeParameterMap.get(1));
                break;
            case 3:
                object = method.invoke(bean, invokeParameterMap.get(0), invokeParameterMap.get(1),
                        invokeParameterMap.get(2));
                break;
            case 4:
                object = method.invoke(bean, invokeParameterMap.get(0), invokeParameterMap.get(1),
                        invokeParameterMap.get(2), invokeParameterMap.get(3));
                break;
            case 5:
                object = method.invoke(bean, invokeParameterMap.get(0), invokeParameterMap.get(1),
                        invokeParameterMap.get(2), invokeParameterMap.get(3), invokeParameterMap.get(4));
                break;
            case 6:
                object = method.invoke(bean, invokeParameterMap.get(0), invokeParameterMap.get(1),
                        invokeParameterMap.get(2), invokeParameterMap.get(3), invokeParameterMap.get(4),
                        invokeParameterMap.get(5));
                break;
            case 7:
                object = method.invoke(bean, invokeParameterMap.get(0), invokeParameterMap.get(1),
                        invokeParameterMap.get(2), invokeParameterMap.get(3), invokeParameterMap.get(4),
                        invokeParameterMap.get(5), invokeParameterMap.get(6));
                break;
            case 8:
                object = method.invoke(bean, invokeParameterMap.get(0), invokeParameterMap.get(1),
                        invokeParameterMap.get(2), invokeParameterMap.get(3), invokeParameterMap.get(4),
                        invokeParameterMap.get(5), invokeParameterMap.get(6), invokeParameterMap.get(7));
                break;
            case 9:
                object = method.invoke(bean, invokeParameterMap.get(0), invokeParameterMap.get(1),
                        invokeParameterMap.get(2), invokeParameterMap.get(3), invokeParameterMap.get(4),
                        invokeParameterMap.get(5), invokeParameterMap.get(6), invokeParameterMap.get(7),
                        invokeParameterMap.get(8));
                break;
            case 10:
                object = method.invoke(bean, invokeParameterMap.get(0), invokeParameterMap.get(1),
                        invokeParameterMap.get(2), invokeParameterMap.get(3), invokeParameterMap.get(4),
                        invokeParameterMap.get(5), invokeParameterMap.get(6), invokeParameterMap.get(7),
                        invokeParameterMap.get(8), invokeParameterMap.get(9));
                break;
            default:
                throw new RuntimeException(method + "方法参数溢出");
        }
        return object;
    }

    /**
     * 字节码对象
     */
    public static final Class<ServletRequest> SERVLET_REQUEST_CLASS = ServletRequest.class;
    public static final Class<ServletResponse> SERVLET_RESPONSE_CLASS = ServletResponse.class;
    public static final Class<RequestParam> REQUEST_PARAM_CLASS = RequestParam.class;
    public static final Class<String> STRING_CLASS = String.class;
    public static final Class<Byte> BYTE_CLASS = Byte.class;
    public static final Class<Short> SHORT_CLASS = Short.class;
    public static final Class<Integer> INTEGER_CLASS = Integer.class;
    public static final Class<Long> LONG_CLASS = Long.class;
    public static final Class<Float> FLOAT_CLASS = Float.class;
    public static final Class<Double> DOUBLE_CLASS = Double.class;
    public static final Class<Boolean> BOOLEAN_CLASS = Boolean.class;
    public static final Class<Character> CHARACTER_CLASS = Character.class;
    public static final Class<Map> MAP_CLASS = Map.class;

    /**
     * 参数拦截器，获取参数并将参数放入invokeParameterMap中
     *
     * @param method   方法对象
     * @param request  HttpServletRequest对象
     * @param response HttpServletResponse对象
     * @return 参数个数
     */
    private static int methodParameterHandler(Method method, HttpServletRequest request, HttpServletResponse response) {
        Parameter[] invokeParameters = method.getParameters();
        for (int i = 0; i < invokeParameters.length; i++) {
            Parameter invokeParameter = invokeParameters[i];
            Class<?> invokeParameterType = invokeParameter.getType();
            // HttpServletRequest和HttpServletResponse的参数注入
            if (SERVLET_REQUEST_CLASS.isAssignableFrom(invokeParameterType) || invokeParameterType == SERVLET_REQUEST_CLASS) {
                invokeParameterMap.put(i, request);
            } else if (SERVLET_RESPONSE_CLASS.isAssignableFrom(invokeParameterType) || invokeParameterType == SERVLET_RESPONSE_CLASS) {
                invokeParameterMap.put(i, response);
                // 使用Map传参
            } else if (MAP_CLASS == invokeParameterType) {
                Map<String, Object> map = new HashMap<>(16);
                Map<String, String[]> parameterMap = request.getParameterMap();
                for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                    String[] param = entry.getValue();
                    if (param.length == 1) {
                        String value = param[0];
                        if (TRUE.equals(value) || FALSE.equals(value)) {
                            map.put(entry.getKey(), Boolean.valueOf(value));
                        } else if (value.matches(NUMBER_REGEX)) {
                            Long longValue = Long.valueOf(value);
                            if (longValue > Integer.MIN_VALUE && longValue < Integer.MAX_VALUE) {
                                map.put(entry.getKey(), Integer.valueOf(value));
                            } else {
                                map.put(entry.getKey(), longValue);
                            }
                        } else {
                            map.put(entry.getKey(), value);
                        }
                    } else {
                        map.put(entry.getKey(), param);
                    }
                }
                invokeParameterMap.put(i, map);
            } else {
                // 普通参数，如果有@RequestParam注解，取值注入
                String parameterValue = null;
                RequestParam requestParam = invokeParameter.getAnnotation(REQUEST_PARAM_CLASS);
                if (requestParam != null) {
                    parameterValue = request.getParameter(requestParam.value());
                }
                invokeParameterMap.put(i, assignment(invokeParameterType, parameterValue));
            }
        }
        return invokeParameters.length;
    }

    /**
     * 匹配纯数字的正则表达式
     */
    public static final String NUMBER_REGEX = "\\d+";
    /**
     * 匹配浮点型的正则表达式
     */
    public static final String DOUBLE_REGEX = "\\d+.?\\d+";
    /**
     * 空字符串
     */
    public static final String EMPTY_STRING = "";
    public static final String TRUE = "true";
    public static final String FALSE = "false";

    /**
     * 将String类型的参数转换为对应的参数类型
     *
     * @param invokeParameterType 参数类型字节码对象
     * @param parameterValue      String类型参数值
     * @return
     */
    private static Object assignment(Class<?> invokeParameterType, String parameterValue) {
        if (parameterValue == null || EMPTY_STRING.equals(parameterValue)) {
            return null;
        }
        if (invokeParameterType == STRING_CLASS) {
            return parameterValue;
        } else if (invokeParameterType == BYTE_CLASS && parameterValue.matches(NUMBER_REGEX)) {
            return Byte.valueOf(parameterValue);
        } else if (invokeParameterType == SHORT_CLASS && parameterValue.matches(NUMBER_REGEX)) {
            return Short.valueOf(parameterValue);
        } else if (invokeParameterType == INTEGER_CLASS && parameterValue.matches(NUMBER_REGEX)) {
            return Integer.valueOf(parameterValue);
        } else if (invokeParameterType == LONG_CLASS && parameterValue.matches(NUMBER_REGEX)) {
            return Long.valueOf(parameterValue);
        } else if (invokeParameterType == FLOAT_CLASS && parameterValue.matches(DOUBLE_REGEX)) {
            return Float.valueOf(parameterValue);
        } else if (invokeParameterType == DOUBLE_CLASS && parameterValue.matches(DOUBLE_REGEX)) {
            return Double.valueOf(parameterValue);
        } else if (invokeParameterType == BOOLEAN_CLASS && TRUE.equals(parameterValue)) {
            return true;
        } else if (invokeParameterType == BOOLEAN_CLASS && FALSE.equals(parameterValue)) {
            return false;
        } else if (invokeParameterType == CHARACTER_CLASS && parameterValue.length() == 1) {
            return parameterValue.charAt(0);
        }
        return null;
    }

    /**
     * 执行方法的参数容器
     */
    private static final Map<Integer, Object> invokeParameterMap = new HashMap<>(16);

}
