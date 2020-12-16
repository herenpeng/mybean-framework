package org.mybeanframework.web.mvc.request;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 请求解析器的包装类，其中包含类和方法
 *
 * @author herenpeng
 * @since 2020-12-15 15:26
 */
public class BeanAndMethod {

    private Object bean;

    private Method method;

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BeanAndMethod that = (BeanAndMethod) o;
        return Objects.equals(bean, that.bean) &&
                Objects.equals(method, that.method);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bean, method);
    }

    public BeanAndMethod(Object bean, Method method) {
        this.bean = bean;
        this.method = method;
    }

    public BeanAndMethod() {
    }
}
