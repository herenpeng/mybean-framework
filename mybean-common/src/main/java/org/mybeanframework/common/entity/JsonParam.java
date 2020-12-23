package org.mybeanframework.common.entity;

/**
 * Json格式化的参数，JsonHelper
 *
 * @author herenpeng
 * @since 2020-12-23 15:58
 */
public class JsonParam {
    /**
     * 类字节码对象，如果该属性为null，说明需要Json格式化的对象不是JavaBean，
     * 而是基本数据类型，或String类型，或Map和Collection类型
     */
    private Class<?> beanClass;


}
