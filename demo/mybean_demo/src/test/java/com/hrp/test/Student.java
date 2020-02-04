package com.hrp.test;

import com.mybean.annotation.MyBean;

/**
 * @author hrp
 * @Date 2020/2/4 15:07
 * 学生类，里面有一个学习方法
 */
public class Student {

    /**
     * 学习方法，被调用的时候，打印学生正在学习
     */
    public void study() {
        System.out.println("学生正在学习......");
    }

}
