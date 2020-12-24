package org.mybeanframework.core.util;

import org.dom4j.Document;
import org.dom4j.Element;
import org.mybeanframework.core.constant.ApplicationXmlConst;

import java.util.List;

/**
 * XML文件的解析工具类，主要用于解析application.xml配置文件
 *
 * @author herenpeng
 * @since 2020-12-15 9:50
 */
public class ApplicationXmlHelper extends ApplicationXmlConst {

    /**
     * 定义一个私有的document对象
     */
    private static Document document = null;

    public static void initDocument(Document document) {
        if (ApplicationXmlHelper.document == null) {
            ApplicationXmlHelper.document = document;
        }
    }

    /**
     * 获取所有的bean属性
     *
     * @return bean节点集合
     */
    public static List<Element> getBean() {
        Element rootElement = document.getRootElement();
        Element beansElement = rootElement.element(XML_BEANS);
        if (beansElement != null) {
            List<Element> beanElements = beansElement.elements(XML_BEAN);
            return beanElements;
        }
        return null;
    }

    /**
     * 通过beanId查找对应的bean节点
     *
     * @param beanId beanId
     * @return bean节点
     */
    public static Element getBean(String beanId) {
        List<Element> beanElements = getBean();
        if(beanElements != null && beanElements.size() > 0) {
            for (Element bean : beanElements) {
                String xmlBeanId = bean.attributeValue(XML_BEAN_ID);
                if (beanId.equals(xmlBeanId)) {
                    return bean;
                }
            }
        }
        return null;
    }

    /**
     * 获取bean的ID属性
     *
     * @param bean bean节点
     * @return id属性
     */
    public static String getBeanId(Element bean) {
        return bean.attributeValue(XML_BEAN_ID);
    }

    /**
     * 获取bean的class属性
     *
     * @param bean bean节点
     * @return class属性
     */
    public static String getBeanClass(Element bean) {
        return bean.attributeValue(XML_BEAN_CLASS);
    }

    /**
     * 获取bean节点下面的set节点
     *
     * @param beanElement bean节点
     * @return set节点集合
     */
    public static List<Element> getSet(Element beanElement) {
        List<Element> setElements = beanElement.elements(XML_SET);
        return setElements;
    }

    /**
     * 获取set的name属性
     *
     * @param set set节点
     * @return name属性
     */
    public static String getSetName(Element set) {
        return set.attributeValue(XML_SET_NAME);
    }

    /**
     * 获取set的ref属性
     *
     * @param set set节点
     * @return ref属性
     */
    public static String getSetRef(Element set) {
        return set.attributeValue(XML_SET_REF);
    }

    /**
     * 获取package-scan节点，配置该节点，开启包扫描
     *
     * @return package-scan节点
     */
    public static Element getPackageScan() {
        Element rootElement = document.getRootElement();
        Element packageScanElement = rootElement.element(XML_PACKAGE_SCAN);
        return packageScanElement;
    }

    /**
     * 获取packageScan的range属性
     *
     * @param packageScan packageScan节点
     * @return range属性值
     */
    public static String getPackageScanRange(Element packageScan) {
        return packageScan.attributeValue(XML_PACKAGE_SCAN_RANGE);
    }

}
