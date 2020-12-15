package org.mybeanframework.core.context;

import org.mybeanframework.core.Application;
import org.mybeanframework.core.bean.BeanFactory;

/**
 * Application的抽象实现，实现了两个getBean方法，定义了MyBean框架的核心容器
 *
 * @author herenpeng
 * @since 2020-2-5 9:34
 */
public abstract class AbstractApplication extends BeanFactory implements Application {

    @Override
    public <T> T getBean(String name) {
        return (T) beanCore.get(name);
    }

    @Override
    public <T> T getBean(String name, Class<T> objClass) {
        Object obj = beanCore.get(name);
        return objClass.isInstance(obj) ? objClass.cast(obj) : null;
    }

    /**
     * 初始化MyBean框架的核心
     */
    protected void initBeanCore() {
        initBeanNameMap();
        // 通过Bean的ID和全限定类名实例化Bean
        instanceBean();
        // 依赖注入，最后返回已完成依赖注入的BeanMap
        setBean();
    }

    /**
     * 初始化beanNameMap，需要各个实现类自己实现该方法
     */
    protected abstract void initBeanNameMap();


}
