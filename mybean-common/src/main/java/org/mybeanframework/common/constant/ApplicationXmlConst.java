package org.mybeanframework.common.constant;

import org.dom4j.Document;

/**
 * application.xml配置文件常量
 *
 * @author herenpeng
 * @since 2020-12-24 9:13
 */
public class ApplicationXmlConst {

    /**
     * 定义一个私有的document对象
     */
    protected static Document document = null;

    public static void initDocument(Document document) {
        if (ApplicationXmlConst.document == null) {
            ApplicationXmlConst.document = document;
        }
    }

    /**
     * 定义xml文件的root节点
     */
    protected static final String XML_ROOT = "mybean";

    /**
     * 定义xml文件的beans节点，所有的bean配置都必须配置在该节点下面
     */
    protected static final String XML_BEANS = "beans";

    /**
     * 定义xml文件的bean节点，每一个bean节点对应一个bean实例
     */
    protected static final String XML_BEAN = "bean";

    /**
     * 定义bean节点的id属性，该节点用于指定bean节点的ID
     */
    protected static final String XML_BEAN_ID = "id";

    /**
     * 定义bean节点的class属性，用于指定bean节点的全限定类型
     */
    protected static final String XML_BEAN_CLASS = "class";

    /**
     * 定义xml文件的set节点，对应bean中的属性引入
     */
    protected static final String XML_SET = "set";

    /**
     * 定义set节点的name属性，代表了属性名称
     */
    protected static final String XML_SET_NAME = "name";

    /**
     * 定义了set节点的ref属性，代表属性引入的bean名称
     */
    protected static final String XML_SET_REF = "ref";

    /**
     * 定义包扫描节点
     */
    protected static final String XML_PACKAGE_SCAN = "package-scan";

    /**
     * 定义包扫描节点的值，即包扫描的范围
     */
    protected static final String XML_PACKAGE_SCAN_RANGE = "range";

    /**
     * 数据源节点
     */
    protected static final String XML_DATA_SOURCE = "data-source";

    /**
     * 数据源驱动名称
     */
    protected static final String XML_DRIVER_NAME = "driver-name";
    /**
     * 数据源链接
     */
    protected static final String XML_URL = "url";
    /**
     * 数据源用户名
     */
    protected static final String XML_USERNAME = "username";
    /**
     * 数据源密码
     */
    protected static final String XML_PASSWORD = "password";
}
