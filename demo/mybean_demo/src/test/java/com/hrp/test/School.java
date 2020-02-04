package com.hrp.test;

import com.mybean.annotation.GetBean;
import com.mybean.annotation.MyBean;

/**
 * @author hrp
 * @Date 2020/2/4 15:08
 * 学校类，里面有学生属性和上课方法，当上课的时候，调用学生的学习方法
 */
public class School {

    @GetBean
    private Student student;

    public void attendClass() {
        student.study();
    }
}
