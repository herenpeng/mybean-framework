package com.mybean.listener;

import com.mybean.context.Application;
import com.mybean.context.support.AnnotationApplication;
import com.mybean.context.support.PropertiesApplication;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * mybean框架的核心监听器，如果发生Servlet初始化，那么就加载相关的配置文件
 *
 * @author hrp
 * @date 2020/2/5 10:31
 */
public class MyBeanContextListener implements ServletContextListener {

    /**
     * 类成员变量，框架的核心容器
     */
    private Application application = null;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String mybeanConfigLocation = sce.getServletContext().getInitParameter("mybeanConfigLocation");
        String mybeanPackageScan = sce.getServletContext().getInitParameter("mybeanPackageScan");
        if (mybeanConfigLocation != null && mybeanConfigLocation.length() > 0) {
            application = new PropertiesApplication(mybeanConfigLocation);
        } else if (mybeanPackageScan != null && mybeanPackageScan.length() > 0) {
            application = new AnnotationApplication(mybeanPackageScan);
        } else {
            application = new PropertiesApplication();
        }
        sce.getServletContext().setAttribute("application",application);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if(application != null){
            application.close();
            application = null;
        }
    }

}
