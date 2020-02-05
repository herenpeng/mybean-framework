package com.mybean.core;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 核心容器
 *
 * @author hp
 */
public abstract class CoreBeanMap {

    /**
     * 默认加载的配置文件
     */
    protected String DEFAULT_FILE = "application.properties";

    /**
     * 核心容器，保证唯一
     */
    protected Map<String, Object> beanMap = new HashMap<>();

    /**
     * 声明一个输入流
     */
    protected InputStream inputStream = null;

}
