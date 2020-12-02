package org.mybeanframework.core.bean;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * Bean实例化工厂
 *
 * @author herenpeng
 */
public class BeanFactory {

    /**
     * 实例化对象的Map集合，其中key为类名首字母小写，value为该类的实例化对象
     */
    private static Map<String, Object> beanMap = new HashMap<>();

    /**
     * BeanFactory的生产方法
     *
     * @param beanNameMap 传入key为类名首字母小写，value为全限定类名的Map
     * @return 返回key为类名首字母小写，value为该类的实例化对象
     */
    protected static Map<String, Object> produce(Map<String, String> beanNameMap) {
        try {
            for (Map.Entry<String, String> entry : beanNameMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                Class<Object> classObject = (Class<Object>) Class.forName(value);
                if (!Modifier.isAbstract(classObject.getModifiers())) {
                    Object object = classObject.newInstance();
                    beanMap.put(key, object);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return beanMap;
    }


}
