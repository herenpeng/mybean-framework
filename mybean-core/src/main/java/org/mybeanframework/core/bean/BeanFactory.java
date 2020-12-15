package org.mybeanframework.core.bean;

import org.mybeanframework.core.util.SetBeanUtils;
import org.mybeanframework.core.util.XmlHelper;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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


    /**
     * 传入所有被管理的BeanMap，返回属性注入完成的BeanMap
     */
    protected static void setBean() {
        try {
            Set<Map.Entry<String, Object>> entries = beanCore.entrySet();
            for (Map.Entry<String, Object> entry : entries) {
                Object beanObject = entry.getValue();
                Class<?> beanClass = beanObject.getClass();
                // 获取所有被@SetBean注解，或者配置文件引入的属性集合
                Set<Field> fields = SetBeanUtils.getAnnotationFieldNames(beanClass);
                for (Field field : fields) {
                    String name = SetBeanUtils.getAnnotationValue(field);
                    Object setBeanObject = null;
                    // 如果@SetBean的value值不为null并且不为空，则使用名称注入的方式
                    if (name != null && name.length() > 0) {
                        setBeanObject = beanCore.get(name);
                        // 如果@SetBean的value值为null或为空，则使用类型注入的方式
                    } else {
                        setBeanObject = findSetBean(field.getType());
                    }
                    // 将Bean实例注入到属性中去
                    field.set(beanObject, setBeanObject);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查找BeanMap中的同一个类或者这个类的子类或实现
     *
     * @param fieldTypeClass 需要查找的类的字节码对象
     * @return
     */
    private static Object findSetBean(Class<?> fieldTypeClass) {
        Set<Map.Entry<String, Object>> entries = beanCore.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
        // 使用迭代器循环Bean实例对象
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            // 循环取出每一个Bean实例
            Object currentBean = entry.getValue();
            // 获取Bean实例的字节码对象
            Class<?> currentBeanClass = currentBean.getClass();
            // 如果Bean实例的字节码对象等于属性类型的字节码对象，或者属性类型的字节码对象是当前Bean实例的父类或接口
            if (currentBeanClass == fieldTypeClass || fieldTypeClass.isAssignableFrom(currentBeanClass)) {
                // 返回当前的Bean实例
                return currentBean;
            }
        }
        // 如果未找到匹配的实例，返回null
        return null;
    }

}
