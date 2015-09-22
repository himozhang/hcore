package com.ideal.framework.cache.vo;

/**
 * 缓存对应枚举，每一个枚举需要在ehcache配置类中声明
 * */
public enum CacheName {
	
	/**
	 * 数据字典缓存
	 * */
	DATADICTCACHE("DataDictCache"),
	/**
	 * 数据字典缓存
	 * */
	MENU_CACHE("Menu_Cache"),
	/**
	 * URL对应的角色缓存
	 * */
	URL_ROLE_CACHE("URL_Role_Cache"),
	/**
	 * 角色对应的菜单缓存
	 * */
	USER_MENU_CACHE("User_Menu_Cache"),
	/**
	 * 用户所对应的角色缓存
	 * */
	USER_ROLE_CACHE("User_Role_Cache"),
	/**
	 * java实例化对象缓存
	 * */
	CLASS_INSTANCE_CACHE("Class_Instance_Cache");

	
	
	public String name;  
	
	private CacheName( String name ) {  
		this.name = name;  
    }  
	
	public static void main(String[] args){
		 
	}
}
