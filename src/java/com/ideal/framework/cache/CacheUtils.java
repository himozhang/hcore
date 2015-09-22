/**  
* @Title: CacheUtils.java
* @Package com.ideal.framework.cache
* @Description: TODO
* @author himo.zhang ideal_tom@163.com
* @date 2015-9-21 下午10:05:53
*/
package com.ideal.framework.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ehcache.CacheException;

import com.ideal.framework.cache.impl.EhCacheUtilsImpl;
import com.ideal.framework.cache.impl.RediesCacheUtilsImpl;
import com.ideal.framework.cache.vo.CacheEntity;
import com.ideal.framework.cache.vo.CacheName;
import com.ideal.framework.constants.AppConstants;
import com.ideal.framework.constants.GlobalConstants;
import com.ideal.framework.utils.string.EmptyUtil;

/**
 * @ClassName: CacheUtils
 * @Description: TODO
 * @author himo.zhang ideal_tom@163.com
 * @date 2015-9-21 下午10:05:53
 */
public class CacheUtils {
	
	/** 日志工具类 */
	private static  Logger logger = LoggerFactory.getLogger(CacheUtils.class);
	
	private static  ICacheUtils cacheUtils = null;
	
	@SuppressWarnings("unused")
	private static ICacheUtils getCacheUtilsInstance(){
		if(EmptyUtil.isEmpty(cacheUtils)){
			logger.debug("缓存方言: "+AppConstants.CACHE_DIALECT);
			if(GlobalConstants.CACHE_DIALECT_EHCACHE.equals(AppConstants.CACHE_DIALECT)){
				cacheUtils = new EhCacheUtilsImpl();
			}else if(GlobalConstants.CACHE_DIALECT_REDIS.equals(AppConstants.CACHE_DIALECT)){
				cacheUtils = new RediesCacheUtilsImpl();
			}
		}
		
		return cacheUtils;
	}
	
	 /**
     * @Description: 按缓存名称与KEY取的对象
     * @param cacheName
     * @param key
     * @return
     */
	public static Object get(CacheName cacheName,String key){
		return getCacheUtilsInstance().get(cacheName, key);
	}
	
	/**
     * @Description:  将Cache放入CacheManager中
     * @param cacheEntity
     */
	public static void put(CacheName cacheName,String key,Object value){
		getCacheUtilsInstance().put(cacheName, key, value);
	}
	 /**
     * @Description:  将Cache放入CacheManager中
     * @param cacheEntity
     */
	public static void put(CacheEntity cacheEntity){
		getCacheUtilsInstance().put(cacheEntity);
	}
	 /**
	 * @Description: 更新缓存的内容
	 * @param CacheName cacheName
	 * @param String key
	 * @param Object value
	 */
	public static void updateByKey(CacheName cacheName,String key,Object value){
		getCacheUtilsInstance().updateByKey(cacheName, key, value);
	}
	/**
	 * @Description: 更新缓存的内容
	 * @param cacheName
	 */
	public static void updateByCacheName(CacheName cacheName){
		getCacheUtilsInstance().updateByCacheName(cacheName);
	}
	/**
	 * @Description: 更新所有缓存的内容
	 */
	public static void updateAll(){
		getCacheUtilsInstance().updateAll();
	}
	/**
     * @Description: 刷新Cache
     * @param cache
     * @throws IllegalStateException
     * @throws CacheException
     */
	public static void flushCache(CacheName cacheName){
		getCacheUtilsInstance().flushCache(cacheName);
	}
	/**
     * @Description: 停止缓存管理器
     */
	public static void shutdown(){
		getCacheUtilsInstance().shutdown();
	}
	

}
