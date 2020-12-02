package org.mybeanframework.core.util;

import org.mybeanframework.core.annotation.SetBean;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * 注解@SetBean的工具类，用于处理@SetBean注解相关的问题
 *
 * @author herenpeng
 */
public class SetBeanUtils {

    /**
     * 常量：GetBean.class
     */
    private final static Class<SetBean> ANNOTATION_CLASS = SetBean.class;

    /**
     * 扫描所有一个类上的被@SetBean注解的属性
     *
     * @param currentBeanClass 传入一个类的字节码对象
     * @return 返回该类上所有被@SetBean注解的属性集合
     */
    public static Set<Field> getFieldNames(Class<?> currentBeanClass) {
        Field[] fields = currentBeanClass.getDeclaredFields();
        Set<Field> set = new HashSet<>();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(ANNOTATION_CLASS)) {
                set.add(field);
            }
        }
        return set;
    }

    /**
     * 传入一个属性，返回该属性上@SetBean注解的value值
     *
     * @param field 传入一个属性
     * @return 返回该属性上@SetBean注解的value值
     */
    public static String getAnnotationValue(Field field) {
        SetBean getBean = field.getAnnotation(ANNOTATION_CLASS);
        return getBean.value();
    }


}
