package org.mybeanframework.common.constant;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Collection;
import java.util.Map;

/**
 * 常用类的字节码对象常量
 *
 * @author herenpeng
 * @since 2020-12-23 13:38
 */
public class ClassConst {

    public static final Class<ServletRequest> SERVLET_REQUEST_CLASS = ServletRequest.class;
    public static final Class<ServletResponse> SERVLET_RESPONSE_CLASS = ServletResponse.class;
    public static final Class<String> STRING_CLASS = String.class;
    public static final Class<Byte> BYTE_CLASS = Byte.class;
    public static final Class<Short> SHORT_CLASS = Short.class;
    public static final Class<Integer> INTEGER_CLASS = Integer.class;
    public static final Class<Long> LONG_CLASS = Long.class;
    public static final Class<Float> FLOAT_CLASS = Float.class;
    public static final Class<Double> DOUBLE_CLASS = Double.class;
    public static final Class<Boolean> BOOLEAN_CLASS = Boolean.class;
    public static final Class<Character> CHARACTER_CLASS = Character.class;
    public static final Class<Map> MAP_CLASS = Map.class;
    public static final Class<Collection> COLLECTION_CLASS = Collection.class;




}
