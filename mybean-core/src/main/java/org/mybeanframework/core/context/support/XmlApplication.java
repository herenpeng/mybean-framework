package org.mybeanframework.core.context.support;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.mybeanframework.common.util.XmlHelper;
import org.mybeanframework.core.context.AbstractApplication;
import org.mybeanframework.core.util.MyBeanUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
            XmlHelper.initDocument(document);
            List<Element> beanList = XmlHelper.getBean();
            if (beanList != null && beanList.size() > 0) {
                for (Element bean : beanList) {
                    String beanId = XmlHelper.getBeanId(bean);
                    String beanClass = XmlHelper.getBeanClass(bean);
                    beanNameMap.put(beanId, beanClass);
                }
            }
            Element packageScan = XmlHelper.getPackageScan();
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

    @Override
    protected void setBean() {
        annotationSetBean();
        try {
            // 解析xml配置文件的内容
            Set<Map.Entry<String, Object>> entries = beanCore.entrySet();
            for (Map.Entry<String, Object> entry : entries) {
                Object beanObject = entry.getValue();
                Class<?> beanClass = beanObject.getClass();
                String beanId = entry.getKey();
                Element beanElement = XmlHelper.getBean(beanId);
                if (beanElement != null) {
                    List<Element> setElementList = XmlHelper.getSet(beanElement);
                    for (Element setBean : setElementList) {
                        String setName = XmlHelper.getSetName(setBean);
                        Field field = beanClass.getDeclaredField(setName);
                        field.setAccessible(true);
                        if (field.get(beanObject) == null) {
                            String setRef = XmlHelper.getSetRef(setBean);
                            Object setBeanObject = beanCore.get(setRef);
                            field.set(beanObject, setBeanObject);
                        }
                    }
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
