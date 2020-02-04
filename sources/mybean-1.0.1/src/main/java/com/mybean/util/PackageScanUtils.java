package com.mybean.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * 包扫描工具类，使用静态方法getBeanNameMap
 * 返回该包下所有的.class文件的首字母小写类名和全限定类名
 *
 * @author hrp
 */
public class PackageScanUtils {

    /**
     * 系统默认的分隔符字符，windows下为\
     */
    private final static String SYSTEM_SEPARATOR = File.separator;
    /**
     * 包之间间隔的符号
     */
    private final static String PACKAGE_SEPARATOR = ".";
    /**
     * .class文件的后缀名
     */
    private final static String SUFFIX = ".class";
    /**
     * url中的间隔符号
     */
    private final static String REPLACEMENT = "/";

    /**
     * 扫描包路径，返回一个key为类名首字符小写，value为全限定类名的Map
     *
     * @param packageName 包扫描的路径
     * @return 返回一个key为类名首字符小写，value为全限定类名的Map
     * @throws IOException IO异常
     */
    public static Map<String, String> getBeanNameMap(String packageName) throws IOException {
        Map<String, String> beanNameMap = null;
        Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(packageName.replace(PACKAGE_SEPARATOR, REPLACEMENT));
        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            if ("file".equals(url.getProtocol())) {
                File file = new File(url.getPath());
                getClassNameByFile(file,packageName);
                beanNameMap = getBeanNameMap(fullClassNameSet);
            }
        }
        return beanNameMap;
    }


    /**
     * 全限定类名Set集合
     */
    private static Set<String> fullClassNameSet = new HashSet<>();

    /**
     * 扫描该文件或文件夹下的所有的.class文件，并将得到的所有全限定类名放入fullClassNameSet集合中
     *
     * @param file 需要扫描的包的文件
     */
    private static void getClassNameByFile(File file,String packageName) {
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            String path = file.getPath();
            if (path.endsWith(SUFFIX)) {
                String className = path.substring(path.lastIndexOf(packageName.replace(PACKAGE_SEPARATOR, SYSTEM_SEPARATOR)), path.lastIndexOf(SUFFIX));
                fullClassNameSet.add(className.replace(SYSTEM_SEPARATOR, PACKAGE_SEPARATOR));
            }
        } else {
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                for (File oneFile : files) {
                    getClassNameByFile(oneFile,packageName);
                }
            }
        }
    }

    /**
     * 将全限定类名的Set集合转换为一个Map集合，其中key为类名首字符小写，value为全限定类名的Map
     *
     * @param fullClassNameSet 全限定类名Set集合
     * @return 返回一个key为类名首字符小写，value为全限定类名的Map
     */
    private static Map<String, String> getBeanNameMap(Set<String> fullClassNameSet) {
        Map<String, String> beanNameMap = new HashMap<>(16);
        for (String fullClassName : fullClassNameSet) {
            String className = fullClassName.substring(fullClassName.lastIndexOf(PACKAGE_SEPARATOR) + 1);
            beanNameMap.put(toLowerFirstChar(className), fullClassName);
        }
        return beanNameMap;
    }

    /**
     * 将类名转换为类名首字母小写的方法
     *
     * @param string 类名
     * @return 返回类名首字母小写
     */
    private static String toLowerFirstChar(String string) {
        if (Character.isLowerCase(string.charAt(0))) {
            return string;
        } else {
            char[] chars = string.toCharArray();
            chars[0] += 32;
            return String.valueOf(chars);
        }
    }


}
