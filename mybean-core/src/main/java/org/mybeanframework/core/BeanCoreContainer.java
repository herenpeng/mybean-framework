package org.mybeanframework.core;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * MyBean框架的核心容器
 *
 * @author herenpeng
 */
public abstract class BeanCoreContainer {

    /**
     * 核心容器，保证唯一 container
     */
    protected Map<String, Object> beanCoreContainer = new HashMap<>();

    /**
     * 默认加载的配置文件
     */
    protected String DEFAULT_CONFIGURATION_FILE = "application.properties";

    /**
     * 声明一个输入流
     */
    protected InputStream inputStream = null;

}
