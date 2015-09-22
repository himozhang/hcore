package com.ideal.framework.cache.vo;

import java.io.Serializable;


/**
 * 缓存实体
 * */
public class CacheEntity implements Serializable {

	private static final long serialVersionUID = -4909059168078660373L;

	/**
	 * 缓存名称
	 */
	private CacheName cacheName;
	
	/**
	 * key值
	 */
	private String key;
	
	/**
	 * 对象值
	 */
	private Object cacheObject;

	public CacheEntity(){
		
	}
	
	public CacheEntity(CacheName cacheName, String key, Object cacheObject){
		this.cacheName = cacheName;
		this.key = key;
		this.cacheObject = cacheObject;
	}
	
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	public CacheName getCacheName() {
		return cacheName;
	}

	public void setCacheName(CacheName cacheName) {
		this.cacheName = cacheName;
	}

	/**
	 * @return the cacheObject
	 */
	public Object getCacheObject() {
		return cacheObject;
	}

	/**
	 * @param cacheObject the cacheObject to set
	 */
	public void setCacheObject(Object cacheObject) {
		this.cacheObject = cacheObject;
	}
}
