package org.mybeanframework.web.mvc.servlet;

import java.util.ArrayList;
import java.util.List;

/**
 * 静态资源处理器
 *
 * @author herenpeng
 * @since 2020-12-21 15:22
 */
public class StaticResourcesHandler {

    /**
     * 静态文件资源后缀名集合
     */
    private static final List<String> staticResourcesSuffixList = new ArrayList<>();

    static {
        staticResourcesSuffixList.add(".html");
        staticResourcesSuffixList.add(".css");
        staticResourcesSuffixList.add(".js");
        staticResourcesSuffixList.add(".ico");
    }

    /**
     * 判断传入的后缀名是否为静态资源
     *
     * @param requestSuffix 请求后缀名
     * @return 如果是静态资源，返回true，否则返回false
     */
    public static boolean isStaticResources(String requestSuffix) {
        return staticResourcesSuffixList.contains(requestSuffix);
    }


}
