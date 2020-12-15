package org.mybeanframework.core.bean;

import org.dom4j.Element;
import org.mybeanframework.core.util.SetBeanUtils;
import org.mybeanframework.core.util.XmlHelper;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.List;
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



    // protected static void setBean() {
    //     try {
    //         Set<Map.Entry<String, Object>> entries = beanCore.entrySet();
    //         for (Map.Entry<String, Object> entry : entries) {
    //             Object beanObject = entry.getValue();
    //             Class<?> beanClass = beanObject.getClass();
    //             // 获取所有被@SetBean注解，或者配置文件引入的属性集合
    //             Set<Field> fields = SetBeanUtils.getAnnotationFieldNames(beanClass);
    //             for (Field field : fields) {
    //                 String name = SetBeanUtils.getAnnotationValue(field);
    //                 Object setBeanObject = null;
    //                 // 如果@SetBean的value值不为null并且不为空，则使用名称注入的方式
    //                 if (name != null && name.length() > 0) {
    //                     setBeanObject = beanCore.get(name);
    //                     // 如果@SetBean的value值为null或为空，则使用类型注入的方式
    //                 } else {
    //                     setBeanObject = findSetBean(field.getType());
    //                 }
    //                 // 将Bean实例注入到属性中去
    //                 field.set(beanObject, setBeanObject);
    //             }
    //             // 解析xml配置文件的内容
    //             String beanId = entry.getKey();
    //             Element beanElement = XmlHelper.getBean(beanId);
    //             List<Element> setElementList = XmlHelper.getSet(beanElement);
    //             for (Element setBean : setElementList) {
    //                 String setName = XmlHelper.getSetName(setBean);
    //                 Field field = beanClass.getField(setName);
    //                 String setRef = XmlHelper.getSetRef(setBean);
    //                 Object setBeanObject = beanCore.get(setRef);
    //                 field.set(beanObject, setBeanObject);
    //             }
    //         }
    //     } catch (IllegalAccessException | NoSuchFieldException e) {
    //         e.printStackTrace();
    //     }
    // }



}
