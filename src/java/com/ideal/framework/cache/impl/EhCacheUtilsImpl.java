package com.ideal.framework.cache.impl;

import com.ideal.framework.cache.ICacheUtils;
import com.ideal.framework.cache.ehcache.EhCacheUtils;
import com.ideal.framework.cache.vo.CacheEntity;
import com.ideal.framework.cache.vo.CacheName;

/** 
 * @Description:
 * @CreateTime 2015-9-12 下午06:18:57
 * @author:himo
 * @mail:zhangyao0905@gmail.com
 */
public class EhCacheUtilsImpl implements ICacheUtils{

	/* (non-Javadoc)
	 * @see com.ideal.framework.cache.ICacheUtils#get(java.lang.String)
	 */
	@Override
	public Object get(CacheName cacheName,String key) {
		return EhCacheUtils.get(cacheName, key);
	}

	/* (non-Javadoc)
	 * @see com.ideal.framework.cache.ICacheUtils#put(com.ideal.framework.cache.ehcache.CacheName, java.lang.String, java.lang.Object)
	 */
	@Override
	public void put(CacheName cacheName, String key, Object value) {
		EhCacheUtils.put(cacheName, key, value);
	}

	/* (non-Javadoc)
	 * @see com.ideal.framework.cache.ICacheUtils#put(com.ideal.framework.cache.ehcache.CacheEntity)
	 */
	@Override
	public void put(CacheEntity cacheEntity) {
		EhCacheUtils.put(cacheEntity);
	}

	/* (non-Javadoc)
	 * @see com.ideal.framework.cache.ICacheUtils#updateByKey(java.lang.String)
	 */
	@Override
	public void updateByKey(CacheName cacheName,String key,Object value) {
		EhCacheUtils.updateByKey(cacheName, key, value);
	}

	/* (non-Javadoc)
	 * @see com.ideal.framework.cache.ICacheUtils#flushCache(com.ideal.framework.cache.ehcache.CacheName)
	 */
	@Override
	public void flushCache(CacheName cacheName) {
		// TODO Auto-generated method stub
		EhCacheUtils.flushCache(cacheName);
	}

	/* (non-Javadoc)
	 * @see com.ideal.framework.cache.ICacheUtils#shutdown()
	 */
	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		EhCacheUtils.shutdown();
	}

	/* (non-Javadoc)
	 * @see com.ideal.framework.cache.ICacheUtils#updateAll()
	 */
	@Override
	public void updateAll() {
		// TODO Auto-generated method stub
		EhCacheUtils.updateAll();
	}

	/* (non-Javadoc)
	 * @see com.ideal.framework.cache.ICacheUtils#updateByCacheName(com.ideal.framework.cache.ehcache.CacheName)
	 */
	@Override
	public void updateByCacheName(CacheName cacheName) {
		// TODO Auto-generated method stub
		EhCacheUtils.updateByCacheName(cacheName);
	}

}
