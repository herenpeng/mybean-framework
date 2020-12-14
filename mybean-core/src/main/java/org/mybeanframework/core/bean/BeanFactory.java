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
            // 循环所有的beanName，实例化每一个Bean对象
            for (Map.Entry<String, String> entry : beanNameMap.entrySet()) {
                // 获取全限定类名
                String value = entry.getValue();
                // 实例化对象
                Class<Object> classObject = (Class<Object>) Class.forName(value);
                // 如果不是抽象类
                if (!Modifier.isAbstract(classObject.getModifiers())) {
                    Object object = classObject.newInstance();
                    beanMap.put(entry.getKey(), object);
                } else {
                    // 如果是抽象类，则移除对应的Bean
                    beanMap.remove(entry.getKey());
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
