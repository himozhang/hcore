package com.ideal.framework.cache.ehcache;

import java.lang.reflect.Method;
import java.util.Date;

import org.apache.log4j.Logger;

import com.ideal.framework.cache.vo.CacheEntity;
import com.ideal.framework.cache.vo.CacheName;
import com.ideal.framework.spring.SpringContextHolder;


/**
 * 缓存执行类
 * */
@SuppressWarnings({"rawtypes","unused","unchecked"})
public class CacheBuilder {

//	public CacheService cacheService;
	
	 
	
	private static Logger log = Logger.getLogger(CacheBuilder.class);
	
	 

	/**
	 * @Description: 自动载入
	 */
	private void init(){
		log.debug("come in CacheBuilder init........");
		Date date = new Date();
	}
	
	 
	
	/**
	 * @Description: 按CODE载入数据字典
	 * @param code
	 */
	private void updateCache(CacheName cacheName,String key,Object value){
		CacheEntity updateCache = new CacheEntity(cacheName,key,value);
		EhCacheUtils.put(updateCache);
//		if(EmptyUtil.isEmpty(key)){
//			 if(CacheName.ROLECACHE.equals(cacheName)){
//				 putAllRole();
//			 }else{
//				 init();
//			 }
//		}else{
//			if(CacheName.MENUCACHE.equals(cacheName)){
//				 putMenuByCode(key);
//			 }else{
//				 log.error("未匹配CacheName: "+cacheName.name);
//			 }
//		}
	}
	
	
	private void refreshAll(){
		init();
	}
	
	/**
	 * @Description: 更新所有Cache
	 */
	public static void updateAll(){
		log.info("开始更新全量缓存...");
		Date date = new Date();
		Class clazz = SpringContextHolder.getBean("cacheBuilder").getClass();
		try {
			Method method = clazz.getDeclaredMethod("refreshAll");
			method.setAccessible(true);
			method.invoke(SpringContextHolder.getBean("cacheBuilder"));
		} catch (Exception e) {
			log.error("putDataDictByCode failed :  \nException Info: " + e.toString());
		}
		log.info("缓存全量更新完成.  -> 此次操作消耗了["+(new Date().getTime() - date.getTime())+"]毫秒！");
	}
	
	/**
	 * @Description: 更新缓存的内容
	 * @param cacheName
	 * @param key
	 */
	protected static void updateByKey(CacheName cacheName,String key,Object value){
		log.info("开始更新缓存<" + cacheName.name+" : "+key + "> ..." );
		Date date = new Date();
		Class clazz = SpringContextHolder.getBean("cacheBuilder").getClass();
		try {
			Method method = clazz.getDeclaredMethod("updateCache",new Class[]{CacheName.class,String.class,Object.class});
			method.setAccessible(true);
			method.invoke(SpringContextHolder.getBean("cacheBuilder"),new Object[]{cacheName,key,value});
		} catch (Exception e) {
			log.error("updateCache failed : " + key + "\nException Info: " + e.toString());
		}
		log.info("缓存<" + key + ">更新完成. -> 此次操作消耗了["+(new Date().getTime() - date.getTime())+"]毫秒！");
	}
	
	/**
	 * @Description: 更新缓存的内容
	 * @param cacheName
	 */
	protected static void updateByKey(CacheName cacheName){
		log.info("开始更新缓存<" + cacheName.name+ "> ..." );
		Date date = new Date();
		Class clazz = SpringContextHolder.getBean("cacheBuilder").getClass();
		try {
			Method method = clazz.getDeclaredMethod("updateCache",new Class[]{CacheName.class,String.class});
			method.setAccessible(true);
			method.invoke(SpringContextHolder.getBean("cacheBuilder"),new Object[]{cacheName,null});
		} catch (Exception e) {
			log.error("updateCache failed : " + cacheName.name + "\nException Info: " + e.toString());
			log.info("缓存<" + cacheName.name + ">更新失败. "+ "\nException Info: " + e.toString()+"-> 此次操作消耗了["+(new Date().getTime() - date.getTime())+"]毫秒！");
		}
		log.info("缓存<" + cacheName.name + ">更新完成. -> 此次操作消耗了["+(new Date().getTime() - date.getTime())+"]毫秒！");
	}

	 
}
