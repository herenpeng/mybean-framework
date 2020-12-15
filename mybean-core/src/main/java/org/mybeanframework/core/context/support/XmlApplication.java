package org.mybeanframework.core.context.support;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.mybeanframework.core.context.AbstractApplication;
import org.mybeanframework.core.util.MyBeanUtils;
import org.mybeanframework.core.util.PackageScanUtils;
import org.mybeanframework.core.util.XmlHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author herenpeng
 * @since 2020-12-15 9:30
 */
public class XmlApplication extends AbstractApplication {

    /**
     * 声明一个输入流
     */
    protected InputStream inputStream;

    /**
     * 默认加载的配置文件
     */
    protected static final String DEFAULT_CONFIGURATION_FILE = "application.xml";

    public XmlApplication() {
        inputStream = XmlApplication.class.getClassLoader().getResourceAsStream(DEFAULT_CONFIGURATION_FILE);
        initBeanCore();
    }

    @Override
    protected void initBeanNameMap() {
        try {
            // 获取解析器对象
            SAXReader reader = new SAXReader();
            // 解析XML
            Document document = reader.read(inputStream);
            List<Element> beanList = XmlHelper.getBean(document);
            for (Element bean : beanList) {
                String beanId = XmlHelper.getBeanId(bean);
                String beanClass = XmlHelper.getBeanClass(bean);
                beanNameMap.put(beanId, beanClass);
            }
            Element packageScan = XmlHelper.getPackageScan(document);
            if (packageScan != null) {
                String packageScanRange = XmlHelper.getPackageScanRange(packageScan);
                // 获取所有被@MyBean注解的Bean的ID和对应的全限定类名
                MyBeanUtils.getBeanName(packageScanRange);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
