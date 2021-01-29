package org.mybeanframework.common.util;

import org.mybeanframework.common.annotation.JsonDateFormat;
import org.mybeanframework.common.annotation.JsonNullIgnore;
import org.mybeanframework.common.constant.JsonConst;

import java.beans.IntrospectionException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
public class JsonUtils extends JsonConst {

    /**
     * 将一个对象转换为json格式
     *
     * @param object 对象
     * @return json格式字符串
     */
    public static String toJson(Object object) {
        if (object == null) {
            return null;
        }
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
                List<MethodUtils.BeanContent> readMethodList = MethodUtils.getReadMethodList(objectClass);
                Iterator<MethodUtils.BeanContent> iterator = readMethodList.iterator();
                while (iterator.hasNext()) {
                    MethodUtils.BeanContent beanContent = iterator.next();
                    Method method = beanContent.getMethod();
                    String fieldName = beanContent.getProperties();
                    Object value = method.invoke(object);
                    Field field = objectClass.getDeclaredField(fieldName);
                    if (ObjectUtils.isEmpty(value) && field.getAnnotation(JsonNullIgnore.class) != null) {
                        continue;
                    }
                    if (ObjectUtils.isNotEmpty(value) && value instanceof Date) {
                        Date date = (Date) value;
                        JsonDateFormat jsonDateFormat = field.getAnnotation(JsonDateFormat.class);
                        if (jsonDateFormat != null) {
                            SimpleDateFormat sdf = new SimpleDateFormat(jsonDateFormat.value());
                            value = sdf.format(date);
                        }
                    }
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
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 拼接json字符串的属性
     *
     * @param objectJson json格式字符串 StringBuffer类型
     * @param fieldName  属性名称
     * @param value      属性值
     */
    private static void splicingJsonAttributes(StringBuffer objectJson, String fieldName, Object value) {
        objectJson.append(DOUBLE_QUOTE).append(fieldName).append(DOUBLE_QUOTE)
                .append(COLON).append(toJson(value));
    }

}
