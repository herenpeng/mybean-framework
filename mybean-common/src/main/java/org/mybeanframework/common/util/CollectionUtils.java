package org.mybeanframework.common.util;

import java.util.Collection;

/**
 * 集合工具类
 *
 * @author herenpeng
 * @since 2020-12-24 9:34
 */
public class CollectionUtils {

    /**
     * 判断Collection对象是否为空
     *
     * @param collection Collection对象
     * @return 为null返回true，否则返回false，
     */
    public static boolean isEmpty(Collection collection) {
        if (collection == null || collection.size() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断Collection对象是否为不空
     *
     * @param collection Collection对象
     * @return 为null返回false，否则返回true
     */
    public static boolean isNotEmpty(Collection collection) {
        return !isEmpty(collection);
    }

}
