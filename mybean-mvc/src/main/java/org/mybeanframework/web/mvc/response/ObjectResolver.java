package org.mybeanframework.web.mvc.response;

import org.mybeanframework.web.mvc.response.enums.CharSetEnum;
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
public class ObjectResolver {

    public static void resolver(Object object, HttpServletResponse response) {
        try {
            response.setContentType(ContentTypeEnum.TEXT_HTML_UTF_8.getValue());
            String text = object.toString();
            ServletOutputStream out = response.getOutputStream();
            out.write(text.getBytes(CharSetEnum.UTF_8.getValue()));
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
