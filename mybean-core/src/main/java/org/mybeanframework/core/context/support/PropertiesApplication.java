package org.mybeanframework.core.context.support;

import org.mybeanframework.core.context.AbstractApplication;
import org.mybeanframework.core.util.MyBeanUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

/**
 * Application接口的对外实现类之一，继承AbstractApplication
 * 使用properties配置文件的启动类
 *
 * @author herenpeng
 * @since 2020-2-5 9:08
 */
public class PropertiesApplication extends AbstractApplication {

    /**
     * 声明一个输入流
     */
    private InputStream inputStream;

    /**
     * 默认加载的配置文件
     */
    private static final String DEFAULT_CONFIGURATION_FILE = "application.properties";

    /**
     * 注解包扫描name
     */
    private static final String PACKAGE_SCAN = "package.scan";

    /**
     * 无参构造，默认加载application.properties文件，并根据配置文件和注解给beanCore赋值
     */
    public PropertiesApplication() {
        inputStream = PropertiesApplication.class.getClassLoader().getResourceAsStream(DEFAULT_CONFIGURATION_FILE);
        initBeanCore();
    }

    /**
     * 有参构造，加载指定的配置文件，并根据配置文件和注解给beanCore赋值
     *
     * @param configFileName 指定的配置文件名
     */
    public PropertiesApplication(String configFileName) {
        inputStream = PropertiesApplication.class.getClassLoader().getResourceAsStream(configFileName);
        initBeanCore();
    }

    @Override
    protected void initBeanNameMap() {
        try {
            Properties properties = new Properties();
            properties.load(inputStream);
            Set<String> propertyNameSet = properties.stringPropertyNames();
            for (String propertyName : propertyNameSet) {
                if (PACKAGE_SCAN.equals(propertyName)) {
                    // 获取包扫描范围
                    String packageScanRange = properties.getProperty(propertyName);
                    // 获取所有被@MyBean注解的Bean的ID和对应的全限定类名
                    MyBeanUtils.getBeanName(packageScanRange);
                } else {
                    beanNameMap.put(propertyName, properties.getProperty(propertyName));
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void setBean() {
        annotationSetBean();
    }


}
