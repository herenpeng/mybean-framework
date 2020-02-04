package com.mybean.context.support;

import com.mybean.beans.PublicFactory;
import com.mybean.context.Application;
import com.mybean.core.CoreBeanMap;

import java.io.IOException;

/**
 * Application接口的实现类之一
 * 使用properties配置文件的启动类
 *
 * @author hrp
 */
public class PropertiesApplication extends CoreBeanMap implements Application {

    /**
     * 无参构造，默认加载application.properties文件，并根据配置文件和注解给beanMap赋值
     */
    public PropertiesApplication() {
        inputStream = PropertiesApplication.class.getClassLoader().getResourceAsStream(DEFAULT_FILE);
        beanMap = PublicFactory.produce(inputStream);
    }

    /**
     * 有参构造，加载指定的配置文件，并根据配置文件和注解给beanMap赋值
     *
     * @param fileName 指定的配置文件名
     */
    public PropertiesApplication(String fileName) {
        inputStream = PropertiesApplication.class.getClassLoader().getResourceAsStream(fileName);
        beanMap = PublicFactory.produce(inputStream);
    }

    @Override
    public <T> T getBean(String name) {
        return (T) beanMap.get(name);
    }

    @Override
    public <T> T getBean(String name, Class<T> objClass) {
        Object obj = beanMap.get(name);
        return objClass.isInstance(obj) ? objClass.cast(obj) : null;
    }

    @Override
    public void close() {
        try {
            if(inputStream != null){
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
