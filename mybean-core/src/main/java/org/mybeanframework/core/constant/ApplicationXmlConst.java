package org.mybeanframework.core.constant;

/**
 * application.xml配置文件常量
 *
 * @author herenpeng
 * @since 2020-12-24 9:13
 */
public class ApplicationXmlConst {

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

}
