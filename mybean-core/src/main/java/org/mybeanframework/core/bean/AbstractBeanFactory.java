package org.mybeanframework.core.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * MyBean框架Bean工厂抽象类，其中包含了Bean的核心容器
 *
 * @author herenpeng
 * @since 2020-12-14 22:38
 */
public abstract class AbstractBeanFactory {

    /**
     * MyBean核心Bean容器，唯一
     */
    protected static final Map<String, Object> beanCore = new HashMap<>();

    /**
     * 被MyBean框架管理的Bean实例名称和全限定类型Map，Key为BeanId，value为BeanFullClassName
     */
    protected static Map<String, String> beanNameMap = new HashMap<>();

}
