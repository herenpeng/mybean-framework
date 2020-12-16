package org.mybeanframework.web.mvc.response;

import org.mybeanframework.web.mvc.response.enums.ContentTypeEnum;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 数据解析器
 *
 * @author herenpeng
 * @since 2020-12-16 13:42
 */
public class DataResolver {

    public static void resolver(Object object, HttpServletResponse response) {
        try {
            if (object instanceof String) {
                response.setContentType(ContentTypeEnum.TEXT_HTML_UTF_8.getValue());
                String data = (String) object;
                ServletOutputStream out = response.getOutputStream();
                out.write(data.getBytes("UTF-8"));
                out.flush();
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
