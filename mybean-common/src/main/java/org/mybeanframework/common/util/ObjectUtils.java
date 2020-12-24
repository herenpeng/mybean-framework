package org.mybeanframework.common.util;

/**
 * Object类型工具类
 *
 * @author herenpeng
 * @since 2020-12-23 15:53
 */
public class ObjectUtils {

    /**
     * 判断Java对象是否为空
     *
     * @param object Java对象
     * @return 为null返回true，否则返回false，
     * 如果对象是字符串，则按照StringUtils.isEmpty(string)方法进行判断
     */
    public static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        }
        if (object instanceof String) {
            String string = (String) object;
            return StringUtils.isEmpty(string);
        }
        return false;
    }

    /**
     * 判断Java对象是否为不空
     *
     * @param object Java对象
     * @return 为null返回false，否则返回true
     */
    public static boolean isNotEmpty(Object object) {
        return !isEmpty(object);
    }

}
