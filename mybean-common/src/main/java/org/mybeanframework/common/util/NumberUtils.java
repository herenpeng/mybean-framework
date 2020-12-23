package org.mybeanframework.common.util;

/**
 * 处理整型的工具类
 *
 * @author herenpeng
 * @since 2020-12-23 14:45
 */
public class NumberUtils {

    /**
     * 判断一个纯数字的字符串是否在Integer的范围之内
     *
     * @param numberString 纯数字的字符串
     * @return 符合纯数字，并且在Integer的范围之内，返回true，其他情况返回false
     */
    public static boolean inIntegerRange(String numberString) {
        if (StringUtils.isNumber(numberString)) {
            Long longValue = Long.valueOf(numberString);
            return inIntegerRange(longValue);
        }
        return false;
    }

    /**
     * 判断Long类型的数据范围是否在Integer范围之内
     *
     * @param longValue Long类型的数据
     * @return 在Integer的范围之内，返回true，其他情况返回false
     */
    public static boolean inIntegerRange(Long longValue) {
        if (longValue > Integer.MIN_VALUE && longValue < Integer.MAX_VALUE) {
            return true;
        }
        return false;
    }


}
