package com.ideal.framework.cache.ehcache;

import java.util.Date;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ideal.framework.cache.vo.CacheEntity;
import com.ideal.framework.cache.vo.CacheName;

/**
 * @ClassName: CacheBuilder
 * @Description: Cache工具类
 */
@Component
public class EhCacheUtils {
	
	final static Log log = LogFactory.getLog(EhCacheUtils.class);  
	  
    private static CacheManager manager;  
    
	  @Autowired(required = true)
	  public void setCacheManager(CacheManager manager) {
		  Date date = new Date();
		  log.debug("init CacheManager....setCacheManager.");
		  EhCacheUtils.manager = manager;
		  log.debug("init CacheManager finish --> 此次操作消耗了["+(new Date().getTime() - date.getTime())+"]毫秒！");
	  }
    
    
    /**
     * @Description:  将Cache放入CacheManager中
     * @param cacheEntity
     */
    public synchronized static void put(CacheName cacheName,String key,Object value){
    	 
    	CacheEntity cache = new CacheEntity(cacheName,key,value);
		EhCacheUtils.put(cache);
		
    }
    
    /**
     * @Description:  将Cache放入CacheManager中
     * @param cacheEntity
     */
    public synchronized static void put(CacheEntity cacheEntity){
    	 
		 Cache cache = getCache(cacheEntity.getCacheName());  
         if(cache != null){  
	         try {  
	        	 cache.remove(cacheEntity.getKey());  
	        	 Element elem = new Element(cacheEntity.getKey(), cacheEntity.getCacheObject());  
	        	 cache.put(elem);  
	        	 if (log.isDebugEnabled()){
	        		 log.debug("put cache("+cacheEntity.getCacheName()+") of "+cacheEntity.getKey()+" done.");  
	        	 }
	         } catch (Exception e) {  
	        	 log.error("put cache("+cacheEntity.getCacheName()+") of "+cacheEntity.getKey()+" failed.", e);  
	         }  
         }
    }
    
    /**
     * @Description: 按缓存名称与KEY取的对象
     * @param cacheName
     * @param key
     * @return
     */
    public static Object get(CacheName cacheName, String key){ 
    	 
		Cache cache = getCache(cacheName);  
        if(cache != null){  
            try {  
                Element elem = cache.get(key);  
                if(elem != null && !cache.isExpired(elem)){
                	log.debug("缓存返回 : "+elem.toString());
                	return elem.getObjectValue();  
                }  
            } catch (Exception e) {  
                log.error("Get cache("+cacheName+") of "+key+" failed.", e);  
            }  
        }  
        log.info("Element is null ("+cacheName+") or "+key+" undefined.");  
        return null;  
    }  
    
    /**
	 * @Description: 更新缓存的内容
	 * @param CacheName cacheName
	 * @param String key
	 * @param Object value
	 */
    public synchronized static void updateByKey(CacheName cacheName,String key,Object value){
    	 
    	CacheBuilder.updateByKey(cacheName, key,value);
    }
    
    /**
	 * @Description: 更新缓存的内容
	 * @param cacheName
	 */
    public synchronized static void updateByCacheName(CacheName cacheName){
    	CacheBuilder.updateByKey(cacheName);
    }
    
    /**
	 * @Description: 更新所有缓存的内容
	 */
    public synchronized static void updateAll(){
    	CacheBuilder.updateAll();
    }
    
    /**
     * @Description: 
     * @param cacheName
     * @return
     * @throws IllegalStateException
     */
    private static Cache getCache(CacheName cacheName) throws IllegalStateException {  
        return manager.getCache(cacheName.name);  
    }  
    
    /**
     * @Description: 刷新Cache
     * @param cache
     * @throws IllegalStateException
     * @throws CacheException
     */
    public static void flushCache(CacheName cacheName) throws IllegalStateException, CacheException{  
    	Cache cache = getCache(cacheName);  
        if(cache != null){
        	cache.flush();
        }
    }
    
    /**
     * @Description: 停止缓存管理器
     */
    public static void shutdown(){  
        if(manager != null)  {
            manager.shutdown();
        }
    }
    
}
