package com.ideal.framework.constants;

import java.util.HashMap;
import java.util.Map;

 

/**
 * 全局配置类
 * @author ThinkGem
 * @version 2014-06-25
 */
public class FileConstants {

	/**
	 * 文件缓冲区大小 1M
	 * */
	public static final int BUFFER_SIZE = 1*1024 * 1024;
	/**
	 * 处理文件最大大小
	 * */ 
	public static final float fileLimitLength = -1; 
	 
	/** 
	 * 所有字段关联的中文名称 
	 * */
	private final static Map NAME_MAP = new HashMap();

	static {
		NAME_MAP.put("", "显示");
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
