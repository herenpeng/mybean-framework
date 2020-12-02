package org.mybeanframework.core.bean;

import org.mybeanframework.core.util.MyBeanUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * 对外工厂接口，封装内部细节
 *
 * @author herenpeng
 */
public class FactoryBuilder {

    /**
     * 对外的工厂接口，传入一个输入流，实现底层封装细节
     *
     * @param inputStream 输入流
     * @return 返回最终的beanMap, 其中已经封装了Bean实例，Bean中已被注入了属性
     */
    public static Map<String, Object> produce(InputStream inputStream) {
        return SetBeanFactory.produce(BeanFactory.produce(BeanNameFactory.produce(inputStream)));
    }

    /**
     * 对外的工厂接口，传入被@PackageScan注解的类的字节码对象，实现底层封装细节
     *
     * @param classObject 类的字节码对象
     * @return 初始化完成的BeanMap
     */
    public static Map<String, Object> produce(Class<?> classObject) {
        try {
            return SetBeanFactory.produce(BeanFactory.produce(MyBeanUtils.filter(classObject)));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对外的工厂接口，传入包扫描的路径，实现底层封装细节
     *
     * @param packageScanName 包扫描路径
     * @return 初始化完成的BeanMap
     */
    public static Map<String, Object> produce(String packageScanName) {
        try {
            return SetBeanFactory.produce(BeanFactory.produce(MyBeanUtils.filter(packageScanName)));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
