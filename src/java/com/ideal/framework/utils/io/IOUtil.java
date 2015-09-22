package com.ideal.framework.utils.io;


import java.io.Closeable;
import java.io.IOException;
 

/** 
* @ClassName:IOUtil.java
* @CreateTime 2015-5-24 上午11:47:15
* @author:himo
* @mail:zhangyao0905@gmail.com
* @Description:IO流工具类
*/ 
public class IOUtil {
    /**
     * 关闭一个或多个流对象
     * 
     * @param closeables
     *            可关闭的流对象列表
     * @throws IOException
     */
    private static void close(Closeable... closeables) throws IOException {
        if (closeables != null) {
            for (Closeable closeable : closeables) {
                if (closeable != null) {
                    closeable.close();
                }
            }
        }
    }
 
    /**
     * 关闭一个或多个流对象
     * 
     * @param closeables
     *            可关闭的流对象列表
     */
    public static void closeQuietly(Closeable... closeables) {
        try {
            close(closeables);
        } catch (IOException e) {
            // do nothing
        }
    }
 
}