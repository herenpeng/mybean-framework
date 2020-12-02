package com.mybean.controller;

import org.mybeanframework.core.annotation.MyBean;

/**
 * @author herenpeng
 * @since 2020-12-02 16:06
 */
@MyBean
public class UserController {

    public void page() {
        System.out.println("进入页面了==========");
    }

}
