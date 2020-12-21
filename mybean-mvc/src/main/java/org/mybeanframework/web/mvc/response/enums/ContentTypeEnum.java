package org.mybeanframework.web.mvc.response.enums;

/**
 * 请求头content-type的可选值
 *
 * @author herenpeng
 * @since 2020-12-16 13:56
 */
public enum ContentTypeEnum {

    /**
     * content-type: text/html;charset=UTF-8
     */
    TEXT_HTML_UTF_8("text/html;charset=UTF-8"),
    /**
     * content-type: application/json;charset=UTF-8
     */
    APPLICATION_JSON_UTF_8("application/json;charset=UTF-8");

    private String value;

    public String getValue() {
        return value;
    }

    ContentTypeEnum(String value) {
        this.value = value;
    }
}
