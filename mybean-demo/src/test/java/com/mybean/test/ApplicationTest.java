package com.mybean.test;

import com.mybean.annotation.MyBean;
import com.mybean.annotation.PackageScan;
import com.mybean.annotation.SetBean;
import com.mybean.context.Application;
import com.mybean.context.support.AnnotationApplication;
import com.mybean.context.support.PropertiesApplication;
import org.junit.Test;

@PackageScan("com.mybean.test")
public class ApplicationTest {

    @Test
    public void test() {
        Application application = new PropertiesApplication();
        Student student = application.getBean("student");
        student.study();
    }

    @Test
    public void test2() {
        System.out.println(this.getClass().getPackage().getName());
    }

    @Test
    public void test3() {
        Application application = new AnnotationApplication(this.getClass());
        Student student = application.getBean("student");
        student.study();
    }


    @Test
    public void test4() {
        Application application = new AnnotationApplication(this.getClass());
        Person person = application.getBean("person");
        person.getStudent().study();
    }

}
