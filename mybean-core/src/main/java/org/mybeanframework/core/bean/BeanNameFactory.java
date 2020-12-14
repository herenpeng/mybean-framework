package org.mybeanframework.core.bean;

import org.mybeanframework.core.util.MyBeanUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 主要用于解析properties文件和类上的@MyBean注解
 *
 * @author herenpeng
 */
public class BeanNameFactory {

    /**
     * 注解包扫描name
     */
    private static final String PACKAGE_SCAN = "package.scan";


    /**
     * BeanNameFactory的生产方法，将配置文件的信息和类上的@MyBean注解，
     * 全部转换为key为类名首字母小写，value为全限定类名的Map
     *
     * @param inputStream 一个输入流
     * @return 返回一个key为类名首字母小写，value为全限定类名
     */
    protected static Map<String, String> propertyProduce(InputStream inputStream) {
        // 全限定类名的Map集合，其中key为类名首字母小写，value为全限定类名
        Map<String, String> beanNameMap = new HashMap<>(16);
        try {
            Properties properties = new Properties();
            properties.load(inputStream);
            Set<String> propertyNameSet = properties.stringPropertyNames();
            for (String propertyName : propertyNameSet) {
                if (PACKAGE_SCAN.equals(propertyName)) {
                    // 获取包扫描范围
                    String packageScanRange = properties.getProperty(propertyName);
                    // 获取所有被@MyBean注解的Bean的ID和对应的全限定类名
                    Map<String, String> beanName = MyBeanUtils.filter(packageScanRange);
                    beanNameMap.putAll(beanName);
                } else {
                    beanNameMap.put(propertyName, properties.getProperty(propertyName));
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return beanNameMap;
    }


}
