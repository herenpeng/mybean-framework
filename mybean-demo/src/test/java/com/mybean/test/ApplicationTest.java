package com.mybean.test;

import org.mybeanframework.core.annotation.PackageScan;
import org.mybeanframework.core.Application;
import org.mybeanframework.core.context.support.AnnotationApplication;
import org.mybeanframework.core.context.support.PropertiesApplication;
import org.junit.Test;
import org.mybeanframework.core.context.support.XmlApplication;

@PackageScan("com.mybean")
public class ApplicationTest {

    @Test
    public void test() {
        Application application = new PropertiesApplication();
        Person person = application.getBean("person");
        person.getStudent().study();
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

    @Test
    public void test4() {
        Application application = new XmlApplication();
        Student student = application.getBean("student");
        student.study();
        Person person = application.getBean("person");
        person.getStudent().study();
    }

}
