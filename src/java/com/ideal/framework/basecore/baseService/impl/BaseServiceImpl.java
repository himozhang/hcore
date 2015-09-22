package com.ideal.framework.basecore.baseService.impl;

import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.ideal.framework.basecore.baseDao.BaseMapper;
import com.ideal.framework.basecore.baseEntity.BaseEntity;
import com.ideal.framework.basecore.baseEntity.IdEntity;
import com.ideal.framework.basecore.baseService.IBaseService;
import com.ideal.framework.cache.CacheUtils;
import com.ideal.framework.cache.vo.CacheName;
import com.ideal.framework.constants.SQLConstants;
import com.ideal.framework.exception.ServiceException;
import com.ideal.framework.mybatis.criterion.MapperHelper;
import com.ideal.framework.mybatis.criterion.SqlParamsCriterionCollection;
import com.ideal.framework.mybatis.vo.EntityColumn;
import com.ideal.framework.page.PageView;
import com.ideal.framework.utils.json.JsonDataGrid;
import com.ideal.framework.utils.json.JsonDataGridReturn;
import com.ideal.framework.utils.string.EmptyUtil;

/** 
 * @ClassName:BaseServiceImpl.java
 * @CreateTime 2015-8-25 上午09:48:15
 * @author:himo
 * @mail:zhangyao0905@gmail.com
 * @Description:
 */
public abstract class BaseServiceImpl<T> implements IBaseService<T>{

	protected Logger logger = LoggerFactory.getLogger(getClass());
	 
	
	/**
	 * 获得Dao接口对象
	 * */
	public abstract BaseMapper<T> getMapper();
	
	
	/**
	 *Insert前置方法
	 * */
	public T preInsert(T entity){
		logger.debug("come in save前置方法  -> preSave.....");
		return entity;
	}
	/**
	 *Insert后置方法
	 * */
	public void afterInsert(T entity){
		logger.debug("come in save后置方法  -> afterSave.....");
	}
	
	/**
	 * 插入 对象
	 * */
	public int insert(T entity) {
		int i=0;
		try {
			entity = preInsert(entity);
			i= getMapper().insert(entity);
			afterInsert(entity);
		} catch (Exception e) {
			e.printStackTrace();
			new ServiceException("根据保存对象失败！");
		}
		return i;
	}
	
	
	/**
	 *Update前置方法
	 * */
	public T preUpdate(T entity){
		logger.debug("come in Update前置方法  -> preSave.....");
		return entity;
	}
	/**
	 *Update后置方法
	 * */
	public void afterUpdate(T entity){
		logger.debug("come in Update后置方法  -> afterSave.....");
	}
	/**
	 * 修改 对象
	 * */
	public int update(T entity) {
		int i=0;
		try {
			entity = preUpdate(entity);
			i= getMapper().update(entity);
			afterUpdate(entity);
		} catch (Exception e) {
			e.printStackTrace();
			new ServiceException("根据保存对象失败！");
		}
		return i;
	}
	
	 /**
     * 修改数据
     * 使用MyBatisUtils操作<br/>
     * @param Map<String, Object> map <br/>
     * @param String set <br/>
     * @param String where <br/>
     * @code
	 *  Map<String, Object> map = new HashMap<String, Object>();<br/>
	 *	String set = "photo=#{photo}";<br/>
	 *	String where = "id=#{id}";<br/>
	 *	map.put("photo", "/upload/userphoto/2015/4/7/crop_5415c7fe534a63366b48d388f204c328_20150407_133318.jpg");<br/>
	 *	map.put(SQLConstants.ID, "ab0fe284f8a045a4a49089f96f24fce4");<br/>
	 * */
	public int updateBySql(Map<String,Object> params,String set,String where){
		int i=0;
		if(EmptyUtil.isEmpty(params)){
			MapperHelper mh = MapperHelper.getMapperHelperInstance();
			params = mh.getSqlParamsCriterionMapResult();
		}
		try {
			params.put(SQLConstants.ENTITY,getObjInstance());
			params.put(SQLConstants.SET,set);
			params.put(SQLConstants.WHERE,where);
			i= getMapper().updateBySql(params);
		} catch (Exception e) {
			e.printStackTrace();
			new ServiceException("根据保存对象失败！");
		}
		return i;
	}
	
	
	/**
	 *delete前置方法
	 * */
	public void preDelete(String id){
		logger.debug("come in delete前置方法  -> preDelete.....");
	}
	/**
	 *delete后置方法
	 * */
	public void afterDelete(String id){
		logger.debug("come in delete后置方法  -> afterDelete.....");
	}
	
	/**
	 * 删除对象，然后批量更新所有的关联关系
	 * @param delId  需要删除的ID
	 * @param tableAndColumnKeyMap  需要关联更新的table和列名
	 * tableAndColumnKeyMap<"IC_USER_ROLE","USER_ID"> 
	 */
	@Transactional(readOnly = false) 
	public int deleteAndBatchUpdateRefs(String delId,List<EntityColumn> entityColumnList){
		int count = delete(delId);
		if(count>0){
			for (EntityColumn ec : entityColumnList) {
				String sql = " delete from "+ec.getTableName();
				if(!EmptyUtil.isEmpty(ec.getColumn())){
					MapperHelper mh = MapperHelper.getMapperHelperInstance();
					SqlParamsCriterionCollection sqlParamsCriterionCollection = SqlParamsCriterionCollection.getSqlParamsCriterionCollectionInit(ec.getColumn(), SQLConstants.EQ,delId);
					mh.addSqlParamsCriterionCollection(sqlParamsCriterionCollection);
					executeSql(sql, mh.getSqlParamsCriterionMapResult());
				}
	        }
		}
		return count;
	}
	
	
	/**
	 * 执行SQL语句
	 * */
	public int executeSql(String sql,Map<String, Object> params){
		Map<String,Object>  map = new HashMap<String, Object>();
		try {
			map.put(SQLConstants.EXCUTE_SQL,sql);
			map.putAll(MapperHelper.formatMapperHelperParams2String(params));
			return getMapper().executeSql(map);
		} catch (Exception e) {
			e.printStackTrace();
			new ServiceException("执行SQL语句失败！");
		}
		return 0;
	}
	
	/**
	 * 根据ID删除对象
	 * */
	public int delete(String id){
		try {
			preDelete(id);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(SQLConstants.ENTITY,getObjInstance());
			map.put(SQLConstants.ID,id);
			int i = getMapper().delete(map);
			afterDelete(id);
			return i;
		} catch (Exception e) {
			e.printStackTrace();
			new ServiceException("根据id : "+id+"删除对象失败！");
		}
		return 0;
	}
	
	/**
	 * 根据ID集合批量删除对象
	 * */
	public int batchDelete(List<String> ids){
		StringBuffer sql = new StringBuffer(" id in (");
		MapperHelper mh = MapperHelper.getMapperHelperInstance();
		int size = ids.size();
		for(int i=0;i<size;i++){
			sql.append(",?");
			mh.addDynamicSqlValueMap(ids.get(i));
		}
		String sqlStr = sql.toString().replaceFirst(",","")+") ";
		logger.debug(" batchDelete sqlStr :"+sqlStr);
		mh.addDynamicSql(sqlStr);
		return deleteByMapperHelper(mh.getSqlParamsCriterionMapResult());
	}
	
	/**
	 * 根据ID集合批量删除对象
	 * */
	public int batchDelete(String[] ids){
		StringBuffer sql = new StringBuffer(" id in (");
		MapperHelper mh = MapperHelper.getMapperHelperInstance();
		int size = ids.length;
		for(int i=0;i<size;i++){
			sql.append(",?");
			mh.addDynamicSqlValueMap(ids[i]);
		}
		String sqlStr = sql.toString().replaceFirst(",","")+") ";
		logger.debug(" batchDelete sqlStr :"+sqlStr);
		mh.addDynamicSql(sqlStr);
		return deleteByMapperHelper(mh.getSqlParamsCriterionMapResult());
	}
	
	/**
	 * 根据查询条件删除对象
	 * */
	public int deleteByMapperHelper(Map<String, Object> map) {
		if(EmptyUtil.isEmpty(map)){
			MapperHelper mh = MapperHelper.getMapperHelperInstance();
			map = mh.getSqlParamsCriterionMapResult();
		}
		try {
			map.put(SQLConstants.ENTITY,getObjInstance());
			map.putAll(MapperHelper.formatMapperHelperParams2String(map));
			return getMapper().deleteByMapperHelper(map);
		} catch (Exception e) {
			e.printStackTrace();
			new ServiceException("根据MapperHelper删除对象失败！");
		}
		return 0;
	}
	

	/**
	 * 排序
	 * */
	public int exchangeSort(String entitId,int sort){
		
		T entity = getObjInstance();
		
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(SQLConstants.ENTITY,entity);
			map.put(SQLConstants.ID, entitId);
			map.put("sort", sort);
			
//			String userid = SpringSecurityUtils.getCurrentUserId();
//			if(EmptyUtil.isEmpty(userid)){
//				userid = "";
//			}
//			
			if(entity instanceof BaseEntity){
//				map.put("modifier", userid);
				map.put("modify_time", new Date());
			}
			
			return getMapper().exchangeSort(map);
		} catch (Exception e) {
			e.printStackTrace();
			new ServiceException("排序失败！entitId : "+entitId);
		}
		
		return 0;
	}
	
	/**
	 * 根据实体中注入的值查询对象
	 * @param MapperHelper params
	 * @code<br/><br/>
	 * 		MapperHelper mh = MapperHelper.getMapperHelperInstance();<br/>
	 *		SqlParamsCriterionList spcList = mh.createSqlParamsCriterionList();<br/>
	 *		spcList.addParam(SQLConstants.ID, SQLKeyConstants.EQ,id);<br/>
	 *		mh.addSqlParamsCriterionList(spcList);<br/>
	 * */
	public T getByMapperHelper(Map<String,Object> params){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(SQLConstants.ENTITY,getObjInstance());
			map.putAll(MapperHelper.formatMapperHelperParams2String(params));
			return getMapper().getByMapperHelper(map);
		} catch (Exception e) {
			e.printStackTrace();
			new ServiceException("获得对象失败！");
		}
		return null;
	}
	
	/**
	 * 根据实体ID查询对象
	 * */
	public T get(String id){
		try {
			MapperHelper mh = MapperHelper.getMapperHelperInstance();
			SqlParamsCriterionCollection sqlParamsCriterionCollection = SqlParamsCriterionCollection.getSqlParamsCriterionCollectionInit(SQLConstants.ID, SQLConstants.EQ,id);
			mh.addSqlParamsCriterionCollection(sqlParamsCriterionCollection);
			return this.getByMapperHelper(mh.getSqlParamsCriterionMapResult());
		} catch (Exception e) {
			e.printStackTrace();
			new ServiceException("根据ID : "+id+"获得对象失败！");
		}
		return null;
	}
	
	/**
	 * 获得所有记录
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * */
	public List<T> getAll(String orderBys) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(SQLConstants.ENTITY,getObjInstance());
			map.put(SQLConstants.ORDER_BY_KEY,orderBys);
			return getMapper().getListByMapperHelper(map);
		} catch (Exception e) {
			e.printStackTrace();
			new ServiceException("抓取全部对象失败！");
		}
		
		return null;
	}
	
	/**
	 * 根据查询条件获得数据集合
	 * */
	public List<T> getListByMapperHelper(Map<String, Object> map) {
		if(EmptyUtil.isEmpty(map)){
			MapperHelper mh = MapperHelper.getMapperHelperInstance();
			map = mh.getSqlParamsCriterionMapResult();
		}
		try {
			map.putAll( MapperHelper.formatMapperHelperParams2String(map));
			map.put(SQLConstants.ENTITY,getObjInstance());
			List<T> resultList = getMapper().getListByMapperHelper(map);
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
			new ServiceException("获取对象失败！");
		}
		return null;
	}
	
	/**
	 * 分页查询并将结果转换成JsonDataGrid
	 * */
	public JsonDataGrid getPageViewByMapperHelper(PageView<T> pageView,Map<String, Object> map,Class DTO_Clazz) {
		if(EmptyUtil.isEmpty(map)){
			MapperHelper mh = MapperHelper.getMapperHelperInstance();
			map = mh.getSqlParamsCriterionMapResult();
		}
		try {
			map.putAll( MapperHelper.formatMapperHelperParams2String(map));
			map.put(SQLConstants.PAGE_VIEW,pageView);
			map.put(SQLConstants.ENTITY,getObjInstance());
			List<T> resultList = getMapper().getListByMapperHelper(map);
			pageView.setRecords(resultList);
			return this.getDataGridJson(pageView, DTO_Clazz);
		} catch (Exception e) {
			e.printStackTrace();
			new ServiceException("获得对象失败！");
		}
		return null;
	}
	
	
	public PageView<T> getPageViewByMapperHelper(PageView<T> pageView,
			Map<String, Object> map) {
		if(EmptyUtil.isEmpty(map)){
			MapperHelper mh = MapperHelper.getMapperHelperInstance();
			map = mh.getSqlParamsCriterionMapResult();
		}
		try {
			map.putAll(MapperHelper.formatMapperHelperParams2String(map));
			map.put(SQLConstants.PAGE_VIEW,pageView);
			map.put(SQLConstants.ENTITY,getObjInstance());
			List<T> resultList = getMapper().getListByMapperHelper(map);
			pageView.setRecords(resultList);
			return pageView;
		} catch (Exception e) {
			e.printStackTrace();
			new ServiceException("获得对象失败！");
		}
		return null;
	}
	
	
	/**
	 * 获得数据的count
	 * */
	public int getCount(Map<String, Object> map) {
		if(EmptyUtil.isEmpty(map)){
			MapperHelper mh = MapperHelper.getMapperHelperInstance();
			map = mh.getSqlParamsCriterionMapResult();
		}
		try {
			map.put(SQLConstants.ENTITY, getObjInstance());
			map.putAll( MapperHelper.formatMapperHelperParams2String(map));
			int count = getMapper().getCount(map);
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			new ServiceException("获得对象count失败！");
		}
		return 0;
	}
	
	/**
	 * 获得所有
	 * 使用MyBatisUtils操作
	 * */
	public T getByName(String name,String value){
		try {
			MapperHelper mh = MapperHelper.getMapperHelperInstance();
			SqlParamsCriterionCollection sqlParamsCriterionCollection = SqlParamsCriterionCollection.getSqlParamsCriterionCollectionInit("lower("+name+")", SQLConstants.EQ,value.toLowerCase());
			mh.addSqlParamsCriterionCollection(sqlParamsCriterionCollection);
			return this.getByMapperHelper(mh.getSqlParamsCriterionMapResult());
		} catch (Exception e) {
			e.printStackTrace();
			new ServiceException("获得对象"+name+"失败！");
		}
		return null;
	}
	
	/**
	 * 获得下一个排序号
	 * */
	@Transactional
	public int getNextSortNum() {
		try {
			int no = 1;
			Object sortNum = getMapper().getNextSortNum(getObjInstance());
			if(!EmptyUtil.isEmpty(sortNum)){
				no = ((BigDecimal)sortNum).intValue()+1;
			}
			logger.debug("获得下一个排序号为："+no);
			return no;
		} catch (Exception e) {
			e.printStackTrace();
			new ServiceException("获得对象下一个排序号失败！");
		}
		return 0; 
	}
	
	/**
	 * 验证code是否存在
	 * @param id 信息ID
	 * @param code 信息code
	 * */
	@Transactional(readOnly = true)
	public boolean validCode(String id,String value) {
		return validByName(id,"code",value);
	}
	
	/**
	 * 验证 数据库列明为 name值是否存在
	 * @param id 信息ID
	 * @param name 
	 * @param value 
	 * */
	@Transactional(readOnly = true)
	public boolean validByName(String id,String name,String value) {
		try {
			T result = this.getByName(name,value);
			if (EmptyUtil.isEmpty(result)) {
				return true;
			}else if(!EmptyUtil.isEmpty(id)&&((IdEntity)result).getId().equals(id)){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			new ServiceException("验证编码失败！");
		} 
		return false;
	}
	
	
	/**
	 * 将结果集格式化为DTO对象集合，并返回JsonDataGrid
	 * */
	public JsonDataGrid getDataGridJson(List<T> list,Class DTO_Clazz){
		T tobj = this.getObjInstance();
		JsonDataGridReturn<T> jdr = new JsonDataGridReturn<T>();
		JsonDataGrid data = jdr.getDataGridJson(list,tobj, DTO_Clazz);
		return data;
	}
	
	/**
	 * 将结果集格式化为DTO对象集合，并返回JsonDataGrid
	 * */
	public JsonDataGrid getDataGridJson(PageView<T> page,Class DTO_Clazz) {
		T tobj = this.getObjInstance();
		JsonDataGridReturn<T> jdr = new JsonDataGridReturn<T>();
		JsonDataGrid data = jdr.getDataGridJson(page, tobj,DTO_Clazz);
		return data;
	}
	
	/**
	 * 根据T类型 new一个对象
	 * */
	public T getObjInstance(){
		//在父类中得到子类声明的父类的泛型信息  
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		Class<T> clazz = (Class) pt.getActualTypeArguments()[0];
		String classSimpleName = clazz.getSimpleName();
		T obj = (T)CacheUtils.get(CacheName.CLASS_INSTANCE_CACHE, classSimpleName);
		if(EmptyUtil.isEmpty(obj)){
			try {
				obj = clazz.newInstance();
				CacheUtils.put(CacheName.CLASS_INSTANCE_CACHE, classSimpleName, obj);
				logger.debug("getObjInstance hashCode :"+obj.hashCode()+" className: "+obj.getClass().getName());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return obj;
	}
	
	
	
}
