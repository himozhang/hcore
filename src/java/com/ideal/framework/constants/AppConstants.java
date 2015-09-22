package com.ideal.framework.constants;


/**
 * 应用全局变量
 * */
public class AppConstants {

	private static BaseConstants baseConstants = new BaseConstants("application");
	 
	
	public static String CACHE_DIALECT = baseConstants.getConfig("cacheDialect");
	
	
	public static String getConfig(String key) {
		return baseConstants.getConfig(key);
	}
	
	
	public static void main(String[] args){
		System.out.println(getConfig("cacheDialect"));
	}
}
