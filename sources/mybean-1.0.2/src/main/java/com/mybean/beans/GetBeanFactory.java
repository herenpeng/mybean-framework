package com.mybean.beans;

import com.mybean.util.GetBeanUtils;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 主要用于解析类上的@GetBean注解，并为有该注解的属性注入实例
 * @author hrp
 */
public class GetBeanFactory {

    protected static Map<String, Object> produce(Map<String, Object> beanMap) {
        try {
            Set<Map.Entry<String, Object>> entries = beanMap.entrySet();
            for (Map.Entry<String, Object> entry : entries) {
                Object currentObject = entry.getValue();
                Class<?> currentBeanClass = currentObject.getClass();
                Set<Field> fields = GetBeanUtils.getFieldNames(currentBeanClass);
                for (Field field : fields) {
                    field.setAccessible(true);
                    String key = GetBeanUtils.getAnnotationValue(field);
                    Object fieldBeanObject = null;
                    if (key != null && key.length() > 0) {
                        fieldBeanObject = beanMap.get(key);
                    } else {
                        fieldBeanObject = findFieldBeanObject(beanMap, field.getType());
                    }
                    field.set(currentObject, fieldBeanObject);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return beanMap;
    }


    private static Object findFieldBeanObject(Map<String, Object> beanMap, Class<?> fieldType) {
        Set<Map.Entry<String, Object>> entries = beanMap.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            Object currentObject = entry.getValue();
            Class<?> currentBeanClass = currentObject.getClass();
            if (currentBeanClass == fieldType) {
                return currentObject;
            }
        }
        return null;
    }


}
