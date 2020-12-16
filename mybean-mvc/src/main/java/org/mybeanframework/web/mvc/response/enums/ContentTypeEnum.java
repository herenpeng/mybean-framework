package org.mybeanframework.web.mvc.response.enums;

/**
 * @author herenpeng
 * @since 2020-12-16 13:56
 */
public enum ContentTypeEnum {

    TEXT_HTML_UTF_8("text/html;charset=UTF-8");

    private String value;

    public String getValue() {
        return value;
    }

    ContentTypeEnum(String value) {
        this.value = value;
    }
}
