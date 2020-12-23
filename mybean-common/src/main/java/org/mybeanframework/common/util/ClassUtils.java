package org.mybeanframework.common.util;

import org.mybeanframework.common.constant.ClassConst;

/**
 * @author herenpeng
 * @since 2020-12-23 15:13
 */
public class ClassUtils extends ClassConst {

    /**
     * 判断一个字节码对象是否为Byte类型
     *
     * @param clazz 字节码对象
     * @return
     */
    public static boolean isByte(Class<?> clazz) {
        return equals(BYTE_CLASS, clazz);
    }

    /**
     * 判断一个字节码对象是否为Short类型
     *
     * @param clazz 字节码对象
     * @return
     */
    public static boolean isShort(Class<?> clazz) {
        return equals(SHORT_CLASS, clazz);
    }


    /**
     * 判断一个字节码对象是否为Integer类型
     *
     * @param clazz 字节码对象
     * @return
     */
    public static boolean isInteger(Class<?> clazz) {
        return equals(INTEGER_CLASS, clazz);
    }


    /**
     * 判断一个字节码对象是否为Long类型
     *
     * @param clazz 字节码对象
     * @return
     */
    public static boolean isLong(Class<?> clazz) {
        return equals(LONG_CLASS, clazz);
    }

    /**
     * 判断一个字节码对象是否为Float类型
     *
     * @param clazz 字节码对象
     * @return
     */
    public static boolean isFloat(Class<?> clazz) {
        return equals(FLOAT_CLASS, clazz);
    }

    /**
     * 判断一个字节码对象是否为Double类型
     *
     * @param clazz 字节码对象
     * @return
     */
    public static boolean isDouble(Class<?> clazz) {
        return equals(DOUBLE_CLASS, clazz);
    }

    /**
     * 判断一个字节码对象是否为Boolean类型
     *
     * @param clazz 字节码对象
     * @return
     */
    public static boolean isBoolean(Class<?> clazz) {
        return equals(BOOLEAN_CLASS, clazz);
    }


    /**
     * 判断一个字节码对象是否为Character类型
     *
     * @param clazz 字节码对象
     * @return
     */
    public static boolean isCharacter(Class<?> clazz) {
        return equals(CHARACTER_CLASS, clazz);
    }


    /**
     * 判断一个字节码对象是否为String类型
     *
     * @param clazz 字节码对象
     * @return
     */
    public static boolean isString(Class<?> clazz) {
        return equals(STRING_CLASS, clazz);
    }

    /**
     * 判断一个字节码对象是否为Map类型，或者它的子类
     *
     * @param clazz 字节码对象
     * @return
     */
    public static boolean isOrFromMap(Class<?> clazz) {
        if (equals(MAP_CLASS, clazz) || MAP_CLASS.isAssignableFrom(clazz)) {
            return true;
        }
        return false;
    }

    /**
     * 判断一个字节码对象是否为Collection类型，或者它的子类
     *
     * @param clazz 字节码对象
     * @return
     */
    public static boolean isOrFromCollection(Class<?> clazz) {
        if (equals(COLLECTION_CLASS, clazz) || COLLECTION_CLASS.isAssignableFrom(clazz)) {
            return true;
        }
        return false;
    }

    /**
     * 判断一个字节码对象是否为ServletRequest类型或者为ServletRequest的子类
     *
     * @param clazz 字节码对象
     * @return
     */
    public static boolean isOrFromServletRequest(Class<?> clazz) {
        if (equals(SERVLET_REQUEST_CLASS, clazz) || SERVLET_REQUEST_CLASS.isAssignableFrom(clazz)) {
            return true;
        }
        return false;
    }

    /**
     * 判断一个字节码对象是否为ServletResponse类型或者为ServletResponse的子类
     *
     * @param clazz 字节码对象
     * @return
     */
    public static boolean isOrFromServletResponse(Class<?> clazz) {
        if (equals(SERVLET_RESPONSE_CLASS, clazz) || SERVLET_RESPONSE_CLASS.isAssignableFrom(clazz)) {
            return true;
        }
        return false;
    }

    /**
     * 比较两个字节码对象是否相同
     *
     * @param classConst 字节码常量
     * @param clazz      需要比对的字节码对象
     * @return 相同返回true，否则返回false
     */
    public static boolean equals(Class<?> classConst, Class<?> clazz) {
        if (classConst == clazz) {
            return true;
        }
        return false;
    }

}
