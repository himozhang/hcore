/**  
* @Title: SqlSessionContextHolder.java
* @Package com.ideal.framework.datasource
* @Description: TODO
* @author himo.zhang ideal_tom@163.com
* @date 2015-9-22 下午11:29:47
*/
package com.ideal.framework.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: SqlSessionContextHolder
 * @Description: TODO
 * @author himo.zhang ideal_tom@163.com
 * @date 2015-9-22 下午11:29:47
 */
public class SqlSessionContextHolder {

	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();  
    private static Logger logger = LoggerFactory.getLogger(SqlSessionContextHolder.class);
  
    public static void setSessionFactoryKey(String dataSourceKey) {  
        contextHolder.set(dataSourceKey);  
    }  
  
    public static String getDataSourceKey() {  
        String key = contextHolder.get();  
        logger.info("当前线程Thread:"+Thread.currentThread().getName()+" 当前的数据源 key is "+ key);  
        return key;  
    }  
    
}
