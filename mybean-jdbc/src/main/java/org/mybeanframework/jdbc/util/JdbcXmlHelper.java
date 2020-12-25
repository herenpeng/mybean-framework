package org.mybeanframework.jdbc.util;

import org.dom4j.Element;
import org.mybeanframework.common.constant.ApplicationXmlConst;

/**
 * 解析JDBC配置文件的XML工具类
 *
 * @author herenpeng
 * @since 2020-12-25 13:35
 */
public class JdbcXmlHelper extends ApplicationXmlConst {

    /**
     * 获取数据源标签
     *
     * @return 数据源标签
     */
    private static Element getDataSource() {
        Element rootElement = document.getRootElement();
        return rootElement.element(XML_DATA_SOURCE);
    }

    /**
     * 获取数据库驱动名称
     *
     * @return 驱动名称
     */
    public static String getDriverName() {
        Element driverNameElement = getDataSource().element(XML_DRIVER_NAME);
        return driverNameElement.getTextTrim();
    }

    /**
     * 获取数据库链接
     *
     * @return 数据库链接
     */
    public static String getUrl() {
        Element driverNameElement = getDataSource().element(XML_URL);
        return driverNameElement.getTextTrim();
    }

    /**
     * 获取数据库用户名
     *
     * @return 数据库用户名
     */
    public static String getUsername() {
        Element driverNameElement = getDataSource().element(XML_USERNAME);
        return driverNameElement.getTextTrim();
    }

    /**
     * 获取数据库密码
     *
     * @return 数据库密码
     */
    public static String getPassword() {
        Element driverNameElement = getDataSource().element(XML_PASSWORD);
        return driverNameElement.getTextTrim();
    }

}
