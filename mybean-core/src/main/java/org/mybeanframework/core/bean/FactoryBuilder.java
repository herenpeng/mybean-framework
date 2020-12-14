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
        Map<String, String> beanNameMap = BeanNameFactory.propertyProduce(inputStream);
        // 通过Bean的ID和全限定类名实例化Bean
        Map<String, Object> beanMap = BeanFactory.produce(beanNameMap);
        // 依赖注入，最后返回已完成依赖注入的BeanMap
        Map<String, Object> myBeanMap = SetBeanFactory.produce(beanMap);
        return myBeanMap;
    }

    /**
     * 对外的工厂接口，传入被@PackageScan注解的类的字节码对象，实现底层封装细节
     *
     * @param classObject 类的字节码对象
     * @return 初始化完成的BeanMap
     */
    public static Map<String, Object> produce(Class<?> classObject) {
        try {
            // 获取所有被@MyBean注解的Bean的ID和对应的全限定类名
            Map<String, String> beanNameMap = MyBeanUtils.filter(classObject);
            // 通过Bean的ID和全限定类名实例化Bean
            Map<String, Object> beanMap = BeanFactory.produce(beanNameMap);
            // 依赖注入，最后返回已完成依赖注入的BeanMap
            Map<String, Object> myBeanMap = SetBeanFactory.produce(beanMap);
            return myBeanMap;
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
            // 获取所有被@MyBean注解的Bean的ID和对应的全限定类名
            Map<String, String> beanNameMap = MyBeanUtils.filter(packageScanName);
            // 通过Bean的ID和全限定类名实例化Bean
            Map<String, Object> beanMap = BeanFactory.produce(beanNameMap);
            // 依赖注入，最后返回已完成依赖注入的BeanMap
            Map<String, Object> myBeanMap = SetBeanFactory.produce(beanMap);
            return myBeanMap;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
