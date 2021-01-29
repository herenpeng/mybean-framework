package org.mybeanframework.common.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 方法工具类，对一些方法的操作
 *
 * @author herenpeng
 * @since 2020-12-25 14:27
 */
public class MethodUtils {

    /**
     * 获取一个类中的所有get/is方法
     *
     * @param objectClass 类字节码对象
     * @return 所有get/is方法
     */
    public static List<BeanContent> getReadMethodList(Class objectClass) throws IntrospectionException {
        List<BeanContent> readMethodList = new ArrayList<>();
        // 获取本类的所有方法
        BeanInfo beanInfo = Introspector.getBeanInfo(objectClass);
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor properties : pds) {
            BeanContent beanContent = new BeanContent();
            beanContent.setProperties(properties.getName());
            beanContent.setMethod(properties.getReadMethod());
            readMethodList.add(beanContent);
        }
        return readMethodList;
    }


    public static List<BeanContent> getWriteMethodList(Class objectClass) throws IntrospectionException {
        List<BeanContent> writeMethodList = new ArrayList<>();
        // 获取本类的所有方法
        BeanInfo beanInfo = Introspector.getBeanInfo(objectClass);
        PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor properties : pds) {
            BeanContent beanContent = new BeanContent();
            beanContent.setProperties(properties.getName());
            beanContent.setMethod(properties.getWriteMethod());
            writeMethodList.add(beanContent);
        }
        return writeMethodList;
    }


    public static class BeanContent {

        private String properties;

        private Method method;

        public String getProperties() {
            return properties;
        }

        public void setProperties(String properties) {
            this.properties = properties;
        }

        public Method getMethod() {
            return method;
        }

        public void setMethod(Method method) {
            this.method = method;
        }
    }

}
