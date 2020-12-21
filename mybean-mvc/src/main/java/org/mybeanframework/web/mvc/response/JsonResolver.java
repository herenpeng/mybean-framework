package org.mybeanframework.web.mvc.response;

import org.mybeanframework.web.mvc.response.enums.CharSetEnum;
import org.mybeanframework.web.mvc.response.enums.ContentTypeEnum;
import org.mybeanframework.web.mvc.util.JsonUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Json格式数据解析器
 *
 * @author herenpeng
 * @since 2020-12-21 11:39
 */
public class JsonResolver {

    /**
     * 解析Json格式数据的方法
     *
     * @param object   被解析的对象
     * @param response HttpServletResponse对象
     */
    public static void resolver(Object object, HttpServletResponse response) {
        try {
            response.setContentType(ContentTypeEnum.APPLICATION_JSON_UTF_8.getValue());
            String json = JsonUtils.toJson(object);
            ServletOutputStream out = response.getOutputStream();
            out.write(json.getBytes(CharSetEnum.UTF_8.getValue()));
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
