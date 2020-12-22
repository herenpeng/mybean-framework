package org.mybeanframework.web.mvc.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

/**
 * 将Java对象转换为JSON格式的字符串，目前支持的类型有：
 * - Java八大基本数据类型以及对应的包装类型
 * - Date类型
 * - BigDecimal类型
 * - Map类型
 * - Collection类型
 * - 遵循规范的JavaBean类型
 *
 * @author herenpeng
 * @since 2020-12-21 11:42
 */
public class JsonUtils {

    /**
     * get方法前缀
     */
    public static final String GET = "get";
    /**
     * is方法前缀
     */
    public static final String IS = "is";

    /**
     * 英文双引号
     */
    public static final String DOUBLE_QUOTE = "\"";
    /**
     * 英文冒号
     */
    public static final String COLON = ":";
    /**
     * 英文逗号
     */
    public static final String COMMA = ",";
    /**
     * 大括号开始
     */
    public static final String BIG_BRACKETS_PREFIX = "{";
    /**
     * 大括号结束
     */
    public static final String BIG_BRACKETS_SUFFIX = "}";
    /**
     * 中括号开始
     */
    public static final String BRACKETS_PREFIX = "[";
    /**
     * 中括号结束
     */
    public static final String BRACKETS_SUFFIX = "]";

    /**
     * 将一个对象转换为json格式
     *
     * @param object 对象
     * @return json格式字符串
     */
    public static String toJson(Object object) {
        String json;
        if (object instanceof Collection) {
            Collection collection = (Collection) object;
            json = toCollectionJson(collection);
        } else {
            json = toObjectJson(object);
        }
        return json;
    }


    /**
     * 将Collection类型转换为转换为json格式的字符串
     *
     * @param Collection Collection集合
     * @return json格式的字符串
     */
    private static String toCollectionJson(Collection Collection) {
        StringBuffer listJson = new StringBuffer();
        listJson.append(BRACKETS_PREFIX);
        Iterator iterator = Collection.iterator();
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
            if (object instanceof Character || object instanceof Long || object instanceof String
                    || object instanceof Date || object instanceof BigDecimal) {
                objectJson.append(DOUBLE_QUOTE).append(object.toString()).append(DOUBLE_QUOTE);
                return objectJson.toString();
            } else if (object instanceof Byte || object instanceof Short || object instanceof Integer
                    || object instanceof Float || object instanceof Double || object instanceof Boolean) {
                objectJson.append(object.toString());
                return objectJson.toString();
            }
            objectJson.append(BIG_BRACKETS_PREFIX);
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
                List<Method> getMethodList = getGetOrIsMethodList(objectClass);
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
            objectJson.append(BIG_BRACKETS_SUFFIX);
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
                .append(COLON).append(toJson(value));
    }


    /**
     * 获取一个类中的所有get方法
     *
     * @param objectClass 类字节码对象
     * @return 所有get方法
     */
    private static List<Method> getGetOrIsMethodList(Class objectClass) {
        List<Method> getMethodList = new ArrayList<>();
        // 获取本类的所有方法
        Method[] declaredMethods = objectClass.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            if (declaredMethod.getName().startsWith(GET) || declaredMethod.getName().startsWith(IS)) {
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
        String fieldName;
        if (getMethodName.startsWith(GET)) {
            fieldName = getMethodName.substring(GET.length());
        } else if (getMethodName.startsWith(IS)) {
            fieldName = getMethodName.substring(IS.length());
        } else {
            return null;
        }
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
