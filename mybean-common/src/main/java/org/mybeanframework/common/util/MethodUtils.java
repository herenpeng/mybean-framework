package org.mybeanframework.common.util;

import org.mybeanframework.common.constant.JsonConst;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 方法工具类，对一些方法的操作
 *
 * @author herenpeng
 * @since 2020-12-25 14:27
 */
public class MethodUtils extends JsonConst {

    /**
     * 通过get/set方法获取属性名称
     *
     * @param method get/set方法
     * @return 属性名称，默认遵守JavaBean规范
     */
    public static String getFieldName(Method method) {
        String getMethodName = method.getName();
        String fieldName;
        if (getMethodName.startsWith(GET)) {
            fieldName = getMethodName.substring(GET.length());
        } else if (getMethodName.startsWith(IS)) {
            fieldName = getMethodName.substring(IS.length());
        } else if (getMethodName.startsWith(SET)) {
            fieldName = getMethodName.substring(SET.length());
        } else {
            return null;
        }
        // 如果首字母为小写，默认返回即可
        if (Character.isLowerCase(fieldName.charAt(0))) {
            return fieldName;
            // 如果首字母大写，则返回首字母小写的字符串
        } else {
            return (new StringBuilder())
                    .append(Character.toLowerCase(fieldName.charAt(0)))
                    .append(fieldName.substring(1)).toString();
        }
    }


    /**
     * 获取一个类中的所有get/is方法
     *
     * @param objectClass 类字节码对象
     * @return 所有get/is方法
     */
    public static List<Method> getGetMethodList(Class objectClass) {
        List<Method> getMethodList = new ArrayList<>();
        // 获取本类的所有方法
        Method[] declaredMethods = objectClass.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            if (declaredMethod.getName().startsWith(GET) || declaredMethod.getName().startsWith(IS)) {
                getMethodList.add(declaredMethod);
            }
        }
        return getMethodList;
    }


    public static List<Method> getSetMethodList(Class objectClass) {
        List<Method> getMethodList = new ArrayList<>();
        // 获取本类的所有方法
        Method[] declaredMethods = objectClass.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            if (declaredMethod.getName().startsWith(SET)) {
                getMethodList.add(declaredMethod);
            }
        }
        return getMethodList;
    }

}
