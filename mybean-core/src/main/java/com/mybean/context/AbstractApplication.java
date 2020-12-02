package com.mybean.context;

import com.mybean.core.CoreBeanMap;

import java.io.IOException;

/**
 * Application的抽象实现，同时继承了CoreBeanMap
 * 使用的是类的适配器模式的设计思想，将接口和抽象类适配
 *
 * @author hrp
 * 2020/2/5 9:34
 */
public abstract class AbstractApplication extends CoreBeanMap implements Application {

    @Override
    public <T> T getBean(String name) {
        return (T) beanCoreContainer.get(name);
    }

    @Override
    public <T> T getBean(String name, Class<T> objClass) {
        Object obj = beanCoreContainer.get(name);
        return objClass.isInstance(obj) ? objClass.cast(obj) : null;
    }

    @Override
    public void close() {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
