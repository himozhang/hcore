package com.ideal.framework.cache;

import com.ideal.framework.cache.vo.CacheEntity;
import com.ideal.framework.cache.vo.CacheName;

import net.sf.ehcache.CacheException;


/** 
 * @Description:
 * @CreateTime 2015-9-12 下午06:14:51
 * @author:himo
 * @mail:zhangyao0905@gmail.com
 */
public interface ICacheUtils {

	 /**
     * @Description: 按缓存名称与KEY取的对象
     * @param cacheName
     * @param key
     * @return
     */
	public  Object get(CacheName cacheName,String key);
	
	/**
     * @Description:  将Cache放入CacheManager中
     * @param cacheEntity
     */
	public void put(CacheName cacheName,String key,Object value);
	 /**
     * @Description:  将Cache放入CacheManager中
     * @param cacheEntity
     */
	public void put(CacheEntity cacheEntity);
	 /**
	 * @Description: 更新缓存的内容
	 * @param CacheName cacheName
	 * @param String key
	 * @param Object value
	 */
	public void updateByKey(CacheName cacheName,String key,Object value);
	/**
	 * @Description: 更新缓存的内容
	 * @param cacheName
	 */
	public void updateByCacheName(CacheName cacheName);
	/**
	 * @Description: 更新所有缓存的内容
	 */
	public void updateAll();
	/**
     * @Description: 刷新Cache
     * @param cache
     * @throws IllegalStateException
     * @throws CacheException
     */
	public void flushCache(CacheName cacheName);
	/**
     * @Description: 停止缓存管理器
     */
	public void shutdown();
}
