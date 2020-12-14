package org.mybeanframework.core.util;

import org.mybeanframework.core.annotation.PackageScan;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * 注解@PackageScan的工具类，用于处理@PackageScan注解相关的问题，返回包扫描下面的所有类的ID和全限定类型
 * 包扫描工具类，有两个受保护的静态方法，只允许同个包下的类访问
 * getBeanNameMap(String packageScanRange)：
 * 返回该包下所有的.class文件的首字母小写类名和全限定类名
 * getBeanNameMap(Class packageScanClass)：
 * 返回这个类上的@PackageScan的属性的包下所有的.class文件的首字母小写类名和全限定类名
 *
 * @author herenpeng
 */
public final class PackageScanUtils {

    /**
     * 包之间间隔的符号
     */
    private static final String PACKAGE_SEPARATOR = ".";
    /**
     * .class文件的后缀名
     */
    private static final String CLASS_FILE_SUFFIX = ".class";
    /**
     * url中的间隔符号
     */
    private static final String URL_SEPARATOR = "/";
    /**
     * 常量：PackageScan.class
     */
    private static final Class<PackageScan> PACKAGE_SCAN_CLASS = PackageScan.class;
    /**
     * 字符串file
     */
    private static final String FILE = "file";
    /**
     * 全限定类名Set集合
     */
    private static final Set<String> fullClassNameSet = new HashSet<>();

    /**
     * 包扫描下的所有类的ID和全限定类名，
     */
    public static final Map<String, String> packageScanMap = new HashMap<>();

    /**
     * 扫描包路径，返回一个key为类名首字符小写，value为全限定类名的Map
     * 访问权限为protected，只允许同个包下的类访问
     *
     * @param packageScanRange 包扫描的范围
     * @return 返回一个key为类名首字符小写，value为全限定类名的Map
     * @throws IOException IO异常
     */
    protected static Map<String, String> getPackageScanMap(String packageScanRange) throws IOException {
        Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(packageScanRange.replace(PACKAGE_SEPARATOR, URL_SEPARATOR));
        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            if (FILE.equals(url.getProtocol())) {
                File file = new File(url.getPath());
                getFullClassNameByFile(file, packageScanRange);
                // 将全限定类名的Set集合转换为一个Map集合，其中key为类名首字符小写，value为全限定类名的Map
                for (String fullClassName : fullClassNameSet) {
                    String className = fullClassName.substring(fullClassName.lastIndexOf(PACKAGE_SEPARATOR) + 1);
                    packageScanMap.put(toLowerFirstChar(className), fullClassName);
                }
            }
        }
        return packageScanMap;
    }

    /**
     * [重载方法]输入一个类的字节码对象，取出该类上的@PackageScan注解的属性值，
     * 扫描该属性值的包，返回一个key为类名首字符小写，value为全限定类名的Map
     * 访问权限为protected，只允许同个包下的类访问
     *
     * @param packageScanClass 被@PackageScan注解的类的字节码对象
     * @return 返回一个key为类名首字符小写，value为全限定类名的Map
     * @throws IOException IO异常
     */
    protected static Map<String, String> getPackageScanMap(Class<?> packageScanClass) throws IOException {
        PackageScan packageScan = packageScanClass.getAnnotation(PACKAGE_SCAN_CLASS);
        if (packageScan != null) {
            String packageScanRange = packageScan.value();
            if (packageScanRange == null || packageScanRange.length() == 0) {
                packageScanRange = packageScanClass.getPackage().getName();
            }
            return getPackageScanMap(packageScanRange);
        }
        return null;
    }


    /**
     * 扫描该文件或文件夹下的所有的.class文件，并将得到的所有全限定类名放入fullClassNameSet集合中
     *
     * @param file             需要扫描的包的文件
     * @param packageScanRange 包扫描范围
     */
    private static void getFullClassNameByFile(File file, String packageScanRange) {
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            String path = file.getPath();
            if (path.endsWith(CLASS_FILE_SUFFIX)) {
                String className = path.substring(path.lastIndexOf(packageScanRange.replace(PACKAGE_SEPARATOR, File.separator)), path.lastIndexOf(CLASS_FILE_SUFFIX));
                fullClassNameSet.add(className.replace(File.separator, PACKAGE_SEPARATOR));
            }
        } else {
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                for (File classFile : files) {
                    getFullClassNameByFile(classFile, packageScanRange);
                }
            }
        }
    }

    /**
     * 将类名转换为类名首字母小写的方法
     *
     * @param className 类名
     * @return 返回类名首字母小写
     */
    private static String toLowerFirstChar(String className) {
        if (Character.isLowerCase(className.charAt(0))) {
            return className;
        } else {
            char[] chars = className.toCharArray();
            chars[0] += 32;
            return String.valueOf(chars);
        }
    }

}
