package com.ideal.framework.cache.impl;

import com.ideal.framework.cache.ICacheUtils;
import com.ideal.framework.cache.vo.CacheEntity;
import com.ideal.framework.cache.vo.CacheName;

/** 
 * @Description:
 * @CreateTime 2015-9-12 下午06:24:23
 * @author:himo
 * @mail:zhangyao0905@gmail.com
 */
public class RediesCacheUtilsImpl implements ICacheUtils{

	/* (non-Javadoc)
	 * @see com.ideal.framework.cache.ICacheUtils#get(com.ideal.framework.cache.ehcache.CacheName, java.lang.String)
	 */
	@Override
	public Object get(CacheName cacheName, String key) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.ideal.framework.cache.ICacheUtils#put(com.ideal.framework.cache.ehcache.CacheName, java.lang.String, java.lang.Object)
	 */
	@Override
	public void put(CacheName cacheName, String key, Object value) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.ideal.framework.cache.ICacheUtils#put(com.ideal.framework.cache.ehcache.CacheEntity)
	 */
	@Override
	public void put(CacheEntity cacheEntity) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.ideal.framework.cache.ICacheUtils#updateByKey(com.ideal.framework.cache.ehcache.CacheName, java.lang.String, java.lang.Object)
	 */
	@Override
	public void updateByKey(CacheName cacheName, String key, Object value) {
		// TODO Auto-generated method stub
		
	}

	/* (非 Javadoc) 
	 * <p>Title:updateByCacheName</p> 
	 * <p>Description: </p> 
	 * @param cacheName 
	 * @see com.ideal.framework.cache.ICacheUtils#updateByCacheName(com.ideal.framework.cache.CacheName) 
	 */ 
	@Override
	public void updateByCacheName(CacheName cacheName) {
		// TODO Auto-generated method stub
		
	}

	/* (非 Javadoc) 
	 * <p>Title:updateAll</p> 
	 * <p>Description: </p>  
	 * @see com.ideal.framework.cache.ICacheUtils#updateAll() 
	 */ 
	@Override
	public void updateAll() {
		// TODO Auto-generated method stub
		
	}

	/* (非 Javadoc) 
	 * <p>Title:flushCache</p> 
	 * <p>Description: </p> 
	 * @param cacheName 
	 * @see com.ideal.framework.cache.ICacheUtils#flushCache(com.ideal.framework.cache.CacheName) 
	 */ 
	@Override
	public void flushCache(CacheName cacheName) {
		// TODO Auto-generated method stub
		
	}

	/* (非 Javadoc) 
	 * <p>Title:shutdown</p> 
	 * <p>Description: </p>  
	 * @see com.ideal.framework.cache.ICacheUtils#shutdown() 
	 */ 
	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}

}
