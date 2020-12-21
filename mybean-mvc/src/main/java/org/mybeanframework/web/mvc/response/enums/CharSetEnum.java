package org.mybeanframework.web.mvc.response.enums;

/**
 * 字符集枚举类型
 *
 * @author herenpeng
 * @since 2020-12-16 14:11
 */
public enum CharSetEnum {

    /**
     * UTF-8字符集
     */
    UTF_8("UTF-8");

    private String value;

    public String getValue() {
        return value;
    }

    CharSetEnum(String value) {
        this.value = value;
    }
}
