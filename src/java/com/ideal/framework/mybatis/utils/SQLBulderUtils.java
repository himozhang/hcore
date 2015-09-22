/**  
* @Title: SQLBulderUtils.java
* @Package com.ideal.framework.mybatis.utils
* @Description: TODO
* @author himo.zhang ideal_tom@163.com
* @date 2015-9-21 上午8:16:18
*/
package com.ideal.framework.mybatis.utils;

import java.util.HashMap;
import java.util.Map;

import com.ideal.framework.constants.SQLConstants;
import com.ideal.framework.mybatis.vo.EntityHelper;

/**
 * @ClassName: SQLBulderUtils
 * @Description: TODO
 * @author himo.zhang ideal_tom@163.com
 * @date 2015-9-21 上午8:16:18
 */
public class SQLBulderUtils {
	
	private Map<String,Object> params = null;
	
	
	/**
	 * 返回参数中传递过来的实体对象
	 * */
	@SuppressWarnings("unchecked")
	public Object getEntity(Object obj){
		Object entity = getByKey(obj,SQLConstants.ENTITY);
		return entity;
	}
	
	/**
	 * 根据键获取map中存放的值
	 * */
	@SuppressWarnings("unchecked")
	public Object getByKey(Object obj,String key_name){
		params = (HashMap<String,Object>) obj;
		Object value = params.get(key_name);
		return value;
	}
	
	/**
	 * 返回需要查询的tableName
	 * */
	public String getTableName(Object obj){
		Object entity = getEntity(obj);
		return EntityHelper.getEntityTable(entity).getName();
	}
	
	/**
	 * 返回参数中传递过来的Where语句
	 * */
	public String getWhere(Object obj){
		String where = (String)getByKey(obj,SQLConstants.WHERE_KEY);
		return where;
	}
	
	/**
	 * 返回参数中传递过来的Set语句
	 * */
	public String getSet(Object obj){
		String set = (String)getByKey(obj,SQLConstants.SET);
		return set;
	}
	
	/**
	 * 返回参数中传递过来的OrderBy语句
	 * */
	public String getOrderBy(Object obj){
		String orderBy = (String)getByKey(obj,SQLConstants.ORDER_BY_KEY);
		return orderBy;
	}

}
