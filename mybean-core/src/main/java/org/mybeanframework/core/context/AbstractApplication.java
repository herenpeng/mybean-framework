package org.mybeanframework.core.context;

import org.mybeanframework.core.Application;
import org.mybeanframework.core.bean.BeanFactory;
import org.mybeanframework.core.util.SetBeanUtils;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Application的抽象实现，实现了两个getBean方法，定义了MyBean框架的核心容器
 *
 * @author herenpeng
 * @since 2020-2-5 9:34
 */
public abstract class AbstractApplication extends BeanFactory implements Application {

    @Override
    public <T> T getBean(String name) {
        return (T) beanCore.get(name);
    }

    @Override
    public <T> T getBean(String name, Class<T> objClass) {
        Object obj = beanCore.get(name);
        return objClass.isInstance(obj) ? objClass.cast(obj) : null;
    }

    /**
     * 初始化MyBean框架的核心
     */
    protected void initBeanCore() {
        initBeanNameMap();
        // 通过Bean的ID和全限定类名实例化Bean
        produceBean();
        // 依赖注入，最后返回已完成依赖注入的BeanMap
        setBean();
    }

    /**
     * 初始化beanNameMap，需要各个实现类自己实现该方法
     */
    protected abstract void initBeanNameMap();

    /**
     * 属性注入Bean实例，
     */
    protected abstract void setBean();


    /**
     * 传入所有被管理的BeanMap，返回注解方式注入属性的BeanMap
     */
    protected static void annotationSetBean() {
        try {
            Set<Map.Entry<String, Object>> entries = beanCore.entrySet();
            for (Map.Entry<String, Object> entry : entries) {
                Object beanObject = entry.getValue();
                Class<?> beanClass = beanObject.getClass();
                // 获取所有被@SetBean注解
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
    protected static Object findSetBean(Class<?> fieldTypeClass) {
        Set<Map.Entry<String, Object>> entries = beanCore.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
        // 使用迭代器循环Bean实例对象
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            // 循环取出每一个Bean实例
            Object beanObject = entry.getValue();
            // 获取Bean实例的字节码对象
            Class<?> currentBeanClass = beanObject.getClass();
            // 如果Bean实例的字节码对象等于属性类型的字节码对象，或者属性类型的字节码对象是当前Bean实例的父类或接口
            if (currentBeanClass == fieldTypeClass || fieldTypeClass.isAssignableFrom(currentBeanClass)) {
                // 返回当前的Bean实例
                return beanObject;
            }
        }
        // 如果未找到匹配的实例，返回null
        return null;
    }


}
