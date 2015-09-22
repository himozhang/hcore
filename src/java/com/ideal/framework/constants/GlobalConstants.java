/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.ideal.framework.constants;

import java.util.HashMap;
import java.util.Map;

 

/**
 * 全局配置类
 * @author ThinkGem
 * @version 2014-06-25
 */
public class GlobalConstants {
	
	/**
	 * 显示:1/隐藏:0
	 */
	public static final String SHOW = "1";
	/**
	 * 显示:1/隐藏:0
	 */
	public static final String HIDE = "0";

	/**
	 * 是:1/否:0
	 */
	public static final String YES = "1";
	/**
	 * 是:1/否:0
	 */
	public static final String NO = "0";
	
	/**
	 * 对:true/错:false
	 */
	public static final String TRUE = "true";
	/**
	 * 对:true/错:false
	 */
	public static final String FALSE = "false";
	
	/**
	 * 缓存方言:EhCache
	 */
	public static final String CACHE_DIALECT_EHCACHE = "ehcache";
	
	/**
	 * 缓存方言:Redis
	 */
	public static final String CACHE_DIALECT_REDIS = "redis";
	
	 
	/** 
	 * 所有字段关联的中文名称 
	 * */
	private final static Map NAME_MAP = new HashMap();

	static {
		NAME_MAP.put(SHOW, "显示");
		NAME_MAP.put(HIDE, "隐藏");
		NAME_MAP.put(YES, "是");
		NAME_MAP.put(NO, "否");
		NAME_MAP.put(TRUE, "对");
		NAME_MAP.put(FALSE, "错");
	}
 
	/**
	 * 所有字段关联的中文名称
	 * */
	public static String getName(String key) {
		return (String) NAME_MAP.get(key);
	}

	public static void main(String[] args){
//		System.out.println(getConst("YES"));
//		System.out.println(getConfig("productName"));
		System.out.println(getName("BMAdmin"));
	}
	 
	
}
