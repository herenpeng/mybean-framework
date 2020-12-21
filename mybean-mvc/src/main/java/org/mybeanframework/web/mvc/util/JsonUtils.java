package org.mybeanframework.web.mvc.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author herenpeng
 * @since 2020-12-21 11:42
 */
public class JsonUtils {

    /**
     * get方法前缀
     */
    public static final String GET = "get";

    /**
     * 双引号
     */
    public static final String DOUBLE_QUOTE = "\"";
    /**
     * 冒号
     */
    public static final String COLON = ":";
    /**
     * 逗号
     */
    public static final String COMMA = ",";
    /**
     * 前大括号
     */
    public static final String BIG_PARANTHESES_PREFIX = "{";
    /**
     * 后大括号
     */
    public static final String BIG_PARANTHESES_SUFFIX = "}";
    /**
     * 前中括号
     */
    public static final String BRACKETS_PREFIX = "[";
    /**
     * 后大括号
     */
    public static final String BRACKETS_SUFFIX = "]";

    /**
     * 将一个对象转换为json格式
     *
     * @param object 对象
     * @return json格式字符串
     */
    public static String toJson(Object object) {
        String json = null;
        if (object instanceof String) {
            json = object.toString();
        } else if (object instanceof List) {
            List list = (List) object;
            json = toListJson(list);
        } else {
            json = toObjectJson(object);
        }
        return json;
    }


    /**
     * 将List转换为转换为json格式的字符串
     *
     * @param list
     * @return
     */
    private static String toListJson(List list) {
        StringBuffer listJson = new StringBuffer();
        listJson.append(BRACKETS_PREFIX);
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            Object obj = iterator.next();
            String objectJson = toObjectJson(obj);
            listJson.append(objectJson);
            listJson.append(COMMA);
        }
        listJson.deleteCharAt(listJson.length() - 1);
        listJson.append(BRACKETS_SUFFIX);
        return listJson.toString();
    }


    /**
     * 将普通的JavaBean转换为json格式的字符串，默认为普通的JavaBean或者Map
     *
     * @param object 对象
     * @return json格式字符串
     */
    private static String toObjectJson(Object object) {
        try {
            StringBuffer objectJson = new StringBuffer();
            objectJson.append(BIG_PARANTHESES_PREFIX);
            if (object instanceof Map) {
                Map map = (Map) object;
                Set<Map.Entry> entrySet = map.entrySet();
                Iterator<Map.Entry> iterator = entrySet.iterator();
                while (iterator.hasNext()) {
                    Map.Entry entry = iterator.next();
                    Object key = entry.getKey();
                    Object value = entry.getValue();
                    splicingJsonAttributes(objectJson, key.toString(), value);
                    objectJson.append(COMMA);
                }
            } else {
                Class<?> objectClass = object.getClass();
                List<Method> getMethodList = getGetMethodList(objectClass);
                Iterator<Method> iterator = getMethodList.iterator();
                while (iterator.hasNext()) {
                    Method method = iterator.next();
                    String fieldName = getFieldName(method);
                    Object value = method.invoke(object);
                    splicingJsonAttributes(objectJson, fieldName, value);
                    objectJson.append(COMMA);
                }
            }
            objectJson.deleteCharAt(objectJson.length() - 1);
            objectJson.append(BIG_PARANTHESES_SUFFIX);
            return objectJson.toString();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 拼接json字符串的属性
     *
     * @param objectJson json格式字符串 StringBuffer类型
     * @param name       属性名称
     * @param value      属性值
     */
    private static void splicingJsonAttributes(StringBuffer objectJson, String name, Object value) {
        objectJson.append(DOUBLE_QUOTE).append(name).append(DOUBLE_QUOTE)
                .append(COLON);
        if (value instanceof Boolean) {
            objectJson.append(value);
        } else {
            objectJson.append(DOUBLE_QUOTE).append(value).append(DOUBLE_QUOTE);
        }
    }


    /**
     * 获取一个类中的所有get方法
     *
     * @param objectClass 类字节码对象
     * @return 所有get方法
     */
    private static List<Method> getGetMethodList(Class objectClass) {
        List<Method> getMethodList = new ArrayList<>();
        // 获取本类的所有方法
        Method[] declaredMethods = objectClass.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            if (declaredMethod.getName().startsWith(GET)) {
                getMethodList.add(declaredMethod);
            }
        }
        return getMethodList;
    }

    /**
     * 通过Get方法获取属性名称
     *
     * @param getMethod get方法
     * @return 属性名称，默认遵守JavaBean规范
     */
    private static String getFieldName(Method getMethod) {
        String getMethodName = getMethod.getName();
        String fieldName = getMethodName.substring(GET.length());
        // 如果首字母为小写，默认返回即可
        if (Character.isLowerCase(fieldName.charAt(0))) {
            return fieldName;
            // 如果首字母大写，则返回首字母小写的字符串
        } else {
            return (new StringBuilder())
                    .append(Character.toLowerCase(fieldName.charAt(0)))
                    .append(fieldName.substring(1)).toString();
        }
    }


}
