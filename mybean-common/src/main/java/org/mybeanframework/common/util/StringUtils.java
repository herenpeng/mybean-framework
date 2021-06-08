package org.mybeanframework.common.util;

import org.mybeanframework.common.constant.RegexConst;
import org.mybeanframework.common.constant.StringConst;

import java.util.Collections;

/**
 * 字符串工具类
 *
 * @author herenpeng
 * @since 2020-12-23 13:47
 */
public class StringUtils extends StringConst {

    /**
     * 判断字符串是否为空字符串
     *
     * @param string 字符串
     * @return 为null或者为""返回true，否则返回false
     */
    public static boolean isEmpty(String string) {
        if (string == null || string.length() == 0 || EMPTY_STRING.equals(string)) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符串是否为不空字符串
     *
     * @param string 字符串
     * @return 为null或者为""返回false，否则返回true
     */
    public static boolean isNotEmpty(String string) {
        return !isEmpty(string);
    }


    public static boolean equalsIgnoreCase(String str1, String str2) {
        if (str1 == null || str2 == null) {
            return str1 == str2;
        }
        return str1.toLowerCase().equals(str2.toLowerCase());
    }


    public static String[] split(String paramsStr, String split) {
        String[] arr = paramsStr.split(split);
        return arr;
    }

    /**
     * 判断是否为布尔类型的字符串
     *
     * @param bool 布尔类型的字符串
     * @return 为"true"或"false"返回true，其他类型返回false
     */
    public static boolean isBooleanString(String bool) {
        return TRUE.equals(bool) || FALSE.equals(bool);
    }

    /**
     * 判断一个字符串是否等于"true"
     *
     * @param bool 字符串
     * @return 等于"true"，返回true，否则返回false
     */
    public static boolean equalsTrue(String bool) {
        return TRUE.equals(bool);
    }

    /**
     * 判断一个字符串是否等于"false"
     *
     * @param bool 字符串
     * @return 等于"false"，返回true，否则返回false
     */
    public static boolean equalsFalse(String bool) {
        return FALSE.equals(bool);
    }

    /**
     * 判断字符串是否为纯数字
     *
     * @param number 字符串
     * @return 字符串为纯数字，返回true，否则返回false
     */
    public static boolean isNumber(String number) {
        return number.matches(RegexConst.NUMBER_REGEX);
    }

    /**
     * 判断字符串是否为小数
     *
     * @param decimal 字符串
     * @return 字符串为小数，返回true，否则返回false
     */
    public static boolean isDecimal(String decimal) {
        return decimal.matches(RegexConst.DECIMAL_REGEX);
    }


}
