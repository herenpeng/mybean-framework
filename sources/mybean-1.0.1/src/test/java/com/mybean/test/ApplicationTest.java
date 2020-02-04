package com.mybean.test;

import com.mybean.context.Application;
import com.mybean.context.support.PropertiesApplication;
import org.junit.Test;

public class ApplicationTest {

    @Test
    public void test(){
        Application application = new PropertiesApplication();
        Student student = application.getBean("student");
        student.study();
    }










}
