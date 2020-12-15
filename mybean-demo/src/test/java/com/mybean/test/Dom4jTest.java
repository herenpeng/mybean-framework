package com.mybean.test;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import org.mybeanframework.core.context.support.PropertiesApplication;

import java.io.InputStream;

/**
 * @author herenpeng
 * @since 2020-12-15 9:39
 */
public class Dom4jTest {

    @Test
    public void test01() throws DocumentException {
        InputStream is = Dom4jTest.class.getClassLoader().getResourceAsStream("application.xml");
        // 获取解析器对象
        SAXReader reader = new SAXReader();
        // 解析XML
        Document document = reader.read(is);
        Element rootElement = document.getRootElement();
        Element beanElement = rootElement.element("beans");
        Element element = beanElement.element("bean");
        // String text = element.getText();
        String id = element.attributeValue("id");
        System.out.println(id);

    }

}
