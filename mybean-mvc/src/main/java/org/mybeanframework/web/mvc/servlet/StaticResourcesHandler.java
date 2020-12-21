package org.mybeanframework.web.mvc.servlet;

import org.mybeanframework.web.mvc.response.ViewResolver;

import javax.servlet.http.HttpServletResponse;
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

    /**
     * 文件后缀分割符号
     */
    public static final String FILE_SUFFIX_SEPARATOR = ".";
    /**
     * 静态资源路径标识
     */
    public static final String STATIC_RESOURCES_FLAG = "@";

    static {
        staticResourcesSuffixList.add(".html");
        staticResourcesSuffixList.add(".css");
        staticResourcesSuffixList.add(".js");
        staticResourcesSuffixList.add(".ico");
    }

    /**
     * 判断传入的后缀名是否为静态资源
     *
     * @param uri 请求路径
     * @return 如果是静态资源，返回true，否则返回false
     */
    public static boolean isStaticResources(String uri) {
        return uri.contains(FILE_SUFFIX_SEPARATOR) &&
                staticResourcesSuffixList.contains(uri.substring(uri.lastIndexOf(FILE_SUFFIX_SEPARATOR)));
    }

    public static void handler(String uri, HttpServletResponse response) {
        if (uri.contains(STATIC_RESOURCES_FLAG)) {
            String viewName = uri.substring(uri.indexOf(STATIC_RESOURCES_FLAG) + 1);
            ViewResolver.resolver(viewName, response);
        } else {
            ViewResolver.resolver(uri, response);
        }
    }


}
