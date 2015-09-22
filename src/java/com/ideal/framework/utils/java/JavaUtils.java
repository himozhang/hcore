/**
* @包名   framework
* @文件名 JavaUtils.java
* @作者   zhangyao
* @创建日期 2014-10-14
*/ 
package com.ideal.framework.utils.java;

import java.net.URL;

/**
 * @author zhangyao
 * JAVA工具类
 */
public class JavaUtils {
	
	 /**
     * 得到类的路径，例如E:/workspace/JavaGUI/bin/com/util
     * @return
     * @throws java.lang.Exception
     */
    public static String getClassPath(Class clazz) throws Exception {
        try {
            String strClassName = clazz.getName();
            String strPackageName = "";
            if (clazz.getPackage().getName() != null) {
                strPackageName = clazz.getPackage().getName();
            }
            String strClassFileName = "";
            if (!"".equals(strPackageName)) {
                strClassFileName = strClassName.substring(strPackageName.length() + 1,
                        strClassName.length());
            } else {
                strClassFileName = strClassName;
            }
            URL url = null;
            url = clazz.getResource(strClassFileName + ".class");
            String strURL = url.toString();
            strURL = strURL.substring(strURL.indexOf('/') + 1, strURL
                    .lastIndexOf('/'));
            //返回当前类的路径，并且处理路径中的空格，因为在路径中出现的空格如果不处理的话，
            //在访问时就会从空格处断开，那么也就取不到完整的信息了，这个问题在web开发中尤其要注意
            String classpath = strURL.replaceAll("%20", " ");
            System.out.println("JavaUtils.getClassPath classpath: "+classpath);
            return classpath;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
    
}
