package com.ideal.framework.utils.java;
 
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
 
/**
 * JAVA类获取项目WebRoot目录
 *@author www.zuidaima.com
 */
public class JavaPathUtil {
    //传入classes可以得到WEB-INF的路径，传入WEB-INF可得到WebRoot路径
    public static void main(String[] args) {
    	JavaPathUtil pathUtil = new JavaPathUtil();
        System.out.println("The Path is:" + pathUtil.getWebInfPath("classes"));
    }
     
    private static JavaPathUtil instance=null;
     
    public static JavaPathUtil getInstance(){
        if(instance==null){
            instance=new JavaPathUtil();
        }
        return instance;
    }
     
    public String getWebInfPath(String index4Str) {
        URL url = getClass().getProtectionDomain().getCodeSource()
                .getLocation();
        String path = url.toString();
        int index = path.indexOf(index4Str);
        if (index == -1) {
            index = path.indexOf("classes");
        }
 
        if (index == -1) {
            index = path.indexOf("bin");
        }
 
        path = path.substring(0, index);
        if (path.startsWith("zip")) {// 当class文件在war中时，此时返回zip:D:/...这样的路径
            path = path.substring(4);
        } else if (path.startsWith("file")) {// 当class文件在class文件中时，此时返回file:/D:/...这样的路径
            path = path.substring(6);
        } else if (path.startsWith("jar")) {// 当class文件在jar文件里面时，此时返回jar:file:/D:/...这样的路径
            path = path.substring(10);
        }
        try {
            path = URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
 
        return path;
    }
}