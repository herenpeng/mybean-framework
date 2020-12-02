package org.mybeanframework.core.context.support;

import org.mybeanframework.core.bean.FactoryBuilder;
import org.mybeanframework.core.context.AbstractApplication;

/**
 * Application接口的对外实现类之一，继承AbstractApplication
 * 使用注解开发的启动类
 *
 * @author herenpeng
 * @since 2020-2-5 9:08
 */
public class AnnotationApplication extends AbstractApplication {

    /**
     * 单参数构造方法，传入一个类的字节码对象，初始化核心容器，实现全注解开发的bena管理
     *
     * @param classObject 一个类的字节码对象
     */
    public AnnotationApplication(Class<?> classObject) {
        beanCoreContainer = FactoryBuilder.produce(classObject);
    }

    /**
     * 单参数构造方法，传入一个包路径，扫描该路径下的所有类，并初始化核心容器
     * 主要用于监听器内初始化核心容器
     *
     * @param packageScanName 一个包路径
     */
    public AnnotationApplication(String packageScanName) {
        beanCoreContainer = FactoryBuilder.produce(packageScanName);
    }

    /**
     * 无参构造
     */
    private AnnotationApplication() {}


}
