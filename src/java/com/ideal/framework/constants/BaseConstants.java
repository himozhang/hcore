/**  
* @Title: IConstants.java
* @Package com.ideal.framework.constants
* @Description: TODO
* @author himo.zhang ideal_tom@163.com
* @date 2015-9-22 上午6:13:37
*/
package com.ideal.framework.constants;

import java.util.ResourceBundle;

/**
 * @ClassName: IConstants
 * @Description: 返回ResourceBundle对象
 * @author himo.zhang ideal_tom@163.com
 * @date 2015-9-22 上午6:13:37
 */
public class BaseConstants {
	
	//资源文件
	private ResourceBundle app_res ;
	
	public BaseConstants(String resourceName){
		this.app_res = ResourceBundle.getBundle(resourceName);
	}
	
	/**
	 * 获得资源文件中的配置项
	 * */
	public String getConfig(String key) {
		return app_res.getString(key);
	}
	

}
