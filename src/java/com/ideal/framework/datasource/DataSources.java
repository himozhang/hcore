package com.ideal.framework.datasource;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;  

/** 
 * @ClassName:DataSources.java
 * @CreateTime 2015-9-7 下午10:18:45
 * @author:himo
 * @mail:zhangyao0905@gmail.com
 * @Description:配置于applicationContext 中，<br/> 
 * 				线程局部变量ThreadLocal contextHolder 保存当前需要的数据源类型，<br/>
 * 				当 DataSourceSwitch.setDataSourceType(DataSourceInstances.XXX) <br/>
 * 				保存当前需要的数据源类型的时候，DataSources 会从当前线程中查找线程变量的数据源类型，<br/>
 * 				从而决定使用何种数据源  
 */
public class DataSources extends AbstractRoutingDataSource{  
  
	 protected Logger logger = LoggerFactory.getLogger(getClass());
	 
    @Override  
    protected Object determineCurrentLookupKey() {  
    	logger.debug(" DataSourceType : "+DataSourceSwitch.getDataSourceType());
        return DataSourceSwitch.getDataSourceType();  
    }  
  
}  
  
