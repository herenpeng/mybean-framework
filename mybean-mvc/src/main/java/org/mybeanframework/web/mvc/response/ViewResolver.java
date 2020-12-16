package org.mybeanframework.web.mvc.response;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * 视图解析器
 *
 * @author herenpeng
 * @since 2020-12-15 14:32
 */
public class ViewResolver {

    /**
     * 常量，默认的视图解析前缀
     */
    private static final String DEFAULT_VIEW_PREFIX = "static/";

    public static void resolver(Object object, HttpServletResponse response) {
        try {
            if (object instanceof String) {
                String viewName = (String) object;
                ServletOutputStream out = response.getOutputStream();
                InputStream is = ViewResolver.class.getClassLoader().getResourceAsStream(DEFAULT_VIEW_PREFIX + viewName);
                byte[] bytes = new byte[1024];
                int len;
                while ((len = is.read(bytes)) != -1) {
                    out.write(bytes, 0, len);
                }
                out.flush();
                out.close();
                is.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
