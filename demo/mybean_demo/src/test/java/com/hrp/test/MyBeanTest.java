package com.hrp.test;

import com.hrp.controller.UserController;
import com.hrp.pojo.User;
import com.mybean.context.Application;
import com.mybean.context.support.PropertiesApplication;
import org.junit.Test;

import java.util.List;

/**
 * @author hrp
 * @Date 2020/2/4 14:46
 */
public class MyBeanTest {

    @Test
    public void test01(){
        Application application = new PropertiesApplication();
        UserController userController = application.getBean("userController");
        List<User> list = userController.findAll();
        for (User user : list) {
            System.out.println(user);
        }
    }


}
