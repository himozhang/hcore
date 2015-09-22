package com.ideal.framework.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ideal.framework.constants.JDBCConstants;
import com.ideal.framework.dialet.Dialect;
import com.ideal.framework.interceptor.PageInterceptorPlugin;

/** 
 * @ClassName:DataSourceSwitch.java
 * @CreateTime 2015-9-7 下午10:18:26
 * @author:himo
 * @mail:zhangyao0905@gmail.com
 * @Description:
 * @example :   DataSourceSwitch.setDataSourceType(DataSourceInstances.ORACLE);  <br/>
        		model.addObject("test2", "这是一个测试,获取数据连接ORACLE:"+testMapper.test());<br/>  
        		DataSourceSwitch.setDataSourceType(DataSourceInstances.MYSQL);  <br/>
        		model.addObject("test3", "这是一个测试,获取数据连接MYSQL:"+testMapper.test()); <br/>
 */

public class DataSourceSwitch{  
    private static final ThreadLocal contextHolder=new ThreadLocal();  
      
    protected Logger logger = LoggerFactory.getLogger(getClass());
    
    public static void setDataSourceType(String dataSourceType){ 
    	 Dialect dialectObj = null;
    		try {
    			if(DataSourceInstances.MYSQL.equals(dataSourceType)){
    				dialectObj = (Dialect) Class.forName(JDBCConstants.DIALECT_MYSQL).getDeclaredConstructor().newInstance();
    			}else if(DataSourceInstances.ORACLE.equals(dataSourceType)){
    				dialectObj = (Dialect) Class.forName(JDBCConstants.DIALECT_ORACLE).getDeclaredConstructor().newInstance();
    	    	}
			} catch (Exception e) {
				e.printStackTrace();
			}
    	PageInterceptorPlugin.changeDialect(dialectObj);
        contextHolder.set(dataSourceType);  
    }  
      
    public static String getDataSourceType(){  
        return (String) contextHolder.get();  
    }  
      
    public static void clearDataSourceType(){  
        contextHolder.remove();  
    }  
}  

