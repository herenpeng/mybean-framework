package com.hrp.test;

import com.mybean.context.Application;
import com.mybean.context.support.PropertiesApplication;
import org.junit.Test;

/**
 * @author hrp
 * @Date 2020/2/4 15:10
 *
 * 学校的测试类，测试Student和School两个类是否完成IOC和DI功能
 */
public class SchoolTest {

    @Test
    public void test01(){
        //初始化核心容器
        Application application = new PropertiesApplication();
        //从核心容器中获取Bean的实例对象
        School school = application.getBean("school");
        //调用对象的方法
        school.attendClass();
    }
}
