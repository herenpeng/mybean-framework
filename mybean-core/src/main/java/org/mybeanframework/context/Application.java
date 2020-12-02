package org.mybeanframework.context;

/**
 * 启动类的接口
 *
 * @author herenpeng
 */
public interface Application {

    /**
     * 返回name对应的实例对象
     *
     * @param name 配置文件中的name，或者是@MyBean注解的类名首字母小写
     * @param <T>  泛型
     * @return 返回一个泛型的实例对象
     */
    <T> T getBean(String name);

    /**
     * 返回name对应的实例对象
     *
     * @param name     配置文件中的name，或者是@MyBean注解的类名首字母小写
     * @param objClass 返回实例对象的类的Class对象
     * @param <T>      泛型
     * @return 返回一个泛型的实例对象
     */
    <T> T getBean(String name, Class<T> objClass);

    /**
     * 关闭输入流
     */
    void close();


}
