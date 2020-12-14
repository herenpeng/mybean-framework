package org.mybeanframework.core.context.support;

import org.mybeanframework.core.context.AbstractApplication;
import org.mybeanframework.core.util.MyBeanUtils;

import java.io.IOException;

/**
 * Application接口的对外实现类之一，继承AbstractApplication
 * 使用注解开发的启动类
 *
 * @author herenpeng
 * @since 2020-2-5 9:08
 */
public class AnnotationApplication extends AbstractApplication {

    /**
     * 单参数构造方法，传入一个包路径，扫描该路径下的所有类，并初始化核心容器
     * 主要用于监听器内初始化核心容器
     *
     * @param packageScanRange 包扫描的范围，类路径
     */
    public AnnotationApplication(String packageScanRange) {
        try {
            // 获取所有被@MyBean注解的Bean的ID和对应的全限定类名
            MyBeanUtils.getBeanName(packageScanRange);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        initBeanCore();
    }

    /**
     * 单参数构造方法，传入一个类的字节码对象，初始化核心容器，实现全注解开发的bean管理
     *
     * @param packageScanClass 被@PanckageScan注解的类的字节码对象
     */
    public AnnotationApplication(Class<?> packageScanClass) {
        try {
            // 获取所有被@MyBean注解的Bean的ID和对应的全限定类名
            MyBeanUtils.getBeanName(packageScanClass);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        initBeanCore();
    }


    /**
     * 无参构造
     */
    private AnnotationApplication() {
    }


    @Override
    protected void initBeanNameMap() {

    }
}
