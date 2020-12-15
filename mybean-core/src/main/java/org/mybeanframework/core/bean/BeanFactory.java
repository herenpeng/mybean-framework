package org.mybeanframework.core.bean;

import java.lang.reflect.Modifier;
import java.util.Map;

/**
 * Bean实例化工厂
 *
 * @author herenpeng
 */
public class BeanFactory extends AbstractBeanFactory {

    /**
     * BeanFactory的生产方法，实例化Bean
     */
    protected static void instanceBean() {
        try {
            // 循环所有的beanName，实例化每一个Bean对象
            for (Map.Entry<String, String> entry : beanNameMap.entrySet()) {
                // 获取全限定类名
                String value = entry.getValue();
                // 实例化对象
                Class<Object> classObject = (Class<Object>) Class.forName(value);
                // 如果不是抽象类，将Bean实例注入核心容器
                if (!Modifier.isAbstract(classObject.getModifiers())) {
                    Object object = classObject.newInstance();
                    beanCore.put(entry.getKey(), object);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

}
