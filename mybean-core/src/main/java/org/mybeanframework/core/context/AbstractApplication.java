package org.mybeanframework.core.context;

import org.mybeanframework.core.Application;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Application的抽象实现
 *
 * @author herenpeng
 * @since 2020-2-5 9:34
 */
public abstract class AbstractApplication implements Application {

    /**
     * MyBean核心容器，唯一
     */
    protected Map<String, Object> beanCore = new HashMap<>();

    /**
     * 默认加载的配置文件
     */
    protected String DEFAULT_CONFIGURATION_FILE = "application.properties";

    /**
     * 声明一个输入流
     */
    protected InputStream inputStream = null;

    @Override
    public <T> T getBean(String name) {
        return (T) beanCore.get(name);
    }

    @Override
    public <T> T getBean(String name, Class<T> objClass) {
        Object obj = beanCore.get(name);
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
