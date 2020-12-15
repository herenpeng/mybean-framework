package org.mybeanframework.web.mvc.request;

import org.mybeanframework.core.bean.BeanFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author herenpeng
 * @since 2020-12-15 15:50
 */
public abstract class AbstractRequestResolver extends BeanFactory {

    /**
     * 定义一个请求解析器容器
     */
    public static final Map<String, BeanAndMethod> requestResolver = new HashMap<>();

}
