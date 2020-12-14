package org.mybeanframework.core.util;

import org.mybeanframework.core.annotation.MyBean;
import org.mybeanframework.core.bean.AbstractBeanFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 注解@MyBean的工具类，用于处理@MyBean注解相关的问题
 *
 * @author herenpeng
 */
public class MyBeanUtils extends AbstractBeanFactory {

    /**
     * 常量：MyBean.class
     */
    private static final Class<MyBean> MY_BEAN_CLASS = MyBean.class;


    /**
     * 注解@MyBean的过滤器
     *
     * @param packageScanRange 传入包扫描，经过包扫描，MyBean注解过滤
     * @return 返回Map集合
     * @throws IOException            IO异常
     * @throws ClassNotFoundException 类没有找到异常
     */
    public static void getBeanName(String packageScanRange) throws IOException, ClassNotFoundException {
        // 获取所有被包扫描的beanName的ID和对应的全限定类型Map
        Map<String, String> packageScanMap = PackageScanUtils.getPackageScanMap(packageScanRange);
        filter(packageScanMap);
    }

    /**
     * 注解@MyBean的过滤器
     *
     * @param packageScanClass 被@PanckageScan注解的类的字节码对象，经过包扫描，MyBean注解过滤
     * @return 返回Map集合
     * @throws IOException            IO异常
     * @throws ClassNotFoundException 类没有找到异常
     */
    public static void getBeanName(Class<?> packageScanClass) throws IOException, ClassNotFoundException {
        // 获取所有被包扫描的beanName的ID和对应的全限定类型Map
        Map<String, String> packageScanMap = PackageScanUtils.getPackageScanMap(packageScanClass);
        filter(packageScanMap);
    }

    /**
     * 获取所有被@MyBean注解的类，
     * 如果@MyBean有value属性值，就将key修改为@MyBean的value值
     *
     * @param packageScanMap 传入一个全限定类名Map
     * @throws ClassNotFoundException 异常
     */
    private static void filter(Map<String, String> packageScanMap) throws ClassNotFoundException {
        Set<Map.Entry<String, String>> beanNames = packageScanMap.entrySet();
        Iterator<Map.Entry<String, String>> iterator = beanNames.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> beanName = iterator.next();
            String id = beanName.getKey();
            String className = beanName.getValue();
            Class<?> beanClass = Class.forName(className);
            // 如果该类上面有@MyBean注解，则进行实例化
            if (beanClass.isAnnotationPresent(MY_BEAN_CLASS)) {
                MyBean myBean = beanClass.getAnnotation(MY_BEAN_CLASS);
                String key = myBean.value();
                if (key != null && key.length() > 0) {
                    id = key;
                }
                beanNameMap.put(id, className);
            }
        }
    }


}
