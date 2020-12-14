package org.mybeanframework.core.context.support;

import org.mybeanframework.core.bean.FactoryBuilder;
import org.mybeanframework.core.context.AbstractApplication;

/**
 * Application接口的对外实现类之一，继承AbstractApplication
 * 使用properties配置文件的启动类
 *
 * @author herenpeng
 * @since 2020-2-5 9:08
 */
public class PropertiesApplication extends AbstractApplication {

    /**
     * 无参构造，默认加载application.properties文件，并根据配置文件和注解给beanCore赋值
     */
    public PropertiesApplication() {
        inputStream = PropertiesApplication.class.getClassLoader().getResourceAsStream(DEFAULT_CONFIGURATION_FILE);
        beanCore = FactoryBuilder.produce(inputStream);
    }

    /**
     * 有参构造，加载指定的配置文件，并根据配置文件和注解给beanCore赋值
     *
     * @param fileName 指定的配置文件名
     */
    public PropertiesApplication(String fileName) {
        inputStream = PropertiesApplication.class.getClassLoader().getResourceAsStream(fileName);
        beanCore = FactoryBuilder.produce(inputStream);
    }

}
