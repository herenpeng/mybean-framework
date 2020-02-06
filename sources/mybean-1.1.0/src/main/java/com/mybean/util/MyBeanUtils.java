package com.mybean.util;

import com.mybean.annotation.MyBean;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 注解@MyBean的工具类，用于处理@MyBean注解相关的问题
 *
 * @author hrp
 */
public class MyBeanUtils {

    /**
     * 常量：MyBean.class
     */
    private final static Class<MyBean> ANNOTATION_CLASS = MyBean.class;

    /**
     * 获取所有被@MyBean注解的类，
     * 如果@MyBean有value属性值，就将key修改为@MyBean的value值
     *
     * @param packageScanBeanNameMap 传入一个全限定类名Map
     * @return 返回所有被@MyBean注解标识的类的全限定类名
     * @throws ClassNotFoundException 异常
     */
    public static Map<String, String> getBeanNameMap(Map<String, String> packageScanBeanNameMap) throws ClassNotFoundException {
        Map<String, String> addMap = new HashMap<>();
        Set<Map.Entry<String, String>> entries = packageScanBeanNameMap.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            String value = entry.getValue();
            Class<?> beanClass = Class.forName(value);
            if (!beanClass.isAnnotationPresent(ANNOTATION_CLASS)) {
                iterator.remove();
            } else {
                MyBean myBean = beanClass.getAnnotation(ANNOTATION_CLASS);
                String key = myBean.value();
                if (key != null && key.length() > 0) {
                    iterator.remove();
                    addMap.put(key, value);
                }
            }
        }
        packageScanBeanNameMap.putAll(addMap);
        return packageScanBeanNameMap;
    }


}
