package com.mybean.test;

import org.mybeanframework.annotation.PackageScan;
import org.mybeanframework.context.Application;
import org.mybeanframework.context.support.AnnotationApplication;
import org.mybeanframework.context.support.PropertiesApplication;
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
        Application application = new AnnotationApplication(this.getClass());
        Student student = application.getBean("student");
        student.study();
    }


    @Test
    public void test3() {
        Application application = new AnnotationApplication(this.getClass());
        Person person = application.getBean("person");
        person.getStudent().study();
    }

}
