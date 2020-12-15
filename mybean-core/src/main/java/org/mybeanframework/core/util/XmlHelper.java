package org.mybeanframework.core.util;

import org.dom4j.Document;
import org.dom4j.Element;

import java.util.List;

/**
 * XML文件的解析工具类，主要用于解析application.xml配置文件
 *
 * @author herenpeng
 * @since 2020-12-15 9:50
 */
public class XmlHelper {

    /**
     * 定义xml文件的root节点
     */
    public static final String XML_ROOT = "mybean";

    /**
     * 定义xml文件的beans节点，所有的bean配置都必须配置在该节点下面
     */
    public static final String XML_BEANS = "beans";

    /**
     * 定义xml文件的bean节点，每一个bean节点对应一个bean实例
     */
    public static final String XML_BEAN = "bean";

    /**
     * 定义bean节点的id属性，该节点用于指定bean节点的ID
     */
    public static final String XML_BEAN_ID = "id";

    /**
     * 定义bean节点的class属性，用于指定bean节点的全限定类型
     */
    public static final String XML_BEAN_CLASS = "class";

    /**
     * 定义xml文件的set节点，对应bean中的属性引入
     */
    public static final String XML_SET = "set";

    /**
     * 定义set节点的name属性，代表了属性名称
     */
    public static final String XML_SET_NAME = "name";

    /**
     * 定义了set节点的ref属性，代表属性引入的bean名称
     */
    public static final String XML_SET_REF = "ref";

    /**
     * 定义包扫描节点
     */
    public static final String XML_PACKAGE_SCAN = "package-scan";

    /**
     * 定义包扫描节点的值，即包扫描的范围
     */
    public static final String XML_PACKAGE_SCAN_RANGE = "range";

    /**
     * 定义一个私有的document对象
     */
    private static Document document = null;

    public static void initDocument(Document document) {
        if (XmlHelper.document == null) {
            XmlHelper.document = document;
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
        List<Element> beanElements = beansElement.elements(XML_BEAN);
        return beanElements;
    }

    /**
     * 通过beanId查找对应的bean节点
     *
     * @param beanId beanId
     * @return bean节点
     */
    public static Element getBean(String beanId) {
        List<Element> beanElements = getBean();
        for (Element bean : beanElements) {
            String xmlBeanId = bean.attributeValue(XML_BEAN_ID);
            if (beanId.equals(xmlBeanId)) {
                return bean;
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
