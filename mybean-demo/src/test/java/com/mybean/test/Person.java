package com.mybean.test;

import org.mybeanframework.core.annotation.MyBean;
import org.mybeanframework.core.annotation.SetBean;

/**
 * @author herenpeng
 * @since 2020-11-30 23:04
 */
@MyBean
public class Person {

    @SetBean
    private Student student;

    public Student getStudent() {
        return student;
    }
}
