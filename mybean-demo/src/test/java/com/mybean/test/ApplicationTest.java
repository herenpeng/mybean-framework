package com.mybean.test;

import org.mybeanframework.core.annotation.PackageScan;
import org.mybeanframework.core.context.Application;
import org.mybeanframework.core.context.support.AnnotationApplication;
import org.mybeanframework.core.context.support.PropertiesApplication;
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
