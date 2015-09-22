package com.ideal.framework.basecore.baseService;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.ideal.framework.mybatis.vo.EntityColumn;
import com.ideal.framework.page.PageView;
import com.ideal.framework.utils.json.JsonDataGrid;

/** 
 * @ClassName:IBaseService.java
 * @CreateTime 2015-8-24 下午10:24:15
 * @author:himo
 * @mail:zhangyao0905@gmail.com
 * @Description:
 */
public interface IBaseService<T> {
	
	/**
	 * 插入 对象
	 * 使用CRUDTemplate操作
	 * */
	@Transactional(readOnly = false)  
	public int insert(T entity);
	
	/**
	 * 删除一条记录
	 * Delete语句从CUDTemplate类中生成
	 * @param obj
	 */
	@Transactional(readOnly = false) 
	public int delete(String id);
	
	/**
	 * 批量删除
	 * Delete语句从CUDTemplate类中生成
	 * @param obj
	 */
	@Transactional(readOnly = false) 
	public int batchDelete(List<String> ids);
	
	/**
	 * 批量删除
	 * Delete语句从CUDTemplate类中生成
	 * @param obj
	 */
	@Transactional(readOnly = false) 
	public int batchDelete(String[] ids);
	
	/**
	 * 删除对象，然后批量更新所有的关联关系
	 * @param delId  需要删除的ID
	 * @param entityColumnList  需要关联更新的table和列名
	 */
	@Transactional(readOnly = false) 
	public int deleteAndBatchUpdateRefs(String delId,List<EntityColumn> entityColumnList);
	
	@Transactional(readOnly = false) 
	public int deleteByMapperHelper(Map<String,Object> params);
	
	@Transactional(readOnly = false)  
	public int update(T entity);
	
	 /**
     * 修改数据
     * 使用CRUDTemplate操作<br/>
     * @param  map <br/>
     * @param  set <br/>
     * @param  where <br/>
     * @code
	 *  Map<String, Object> params = new HashMap<String, Object>();<br/>
	 *	String set = "photo=#{photo}";<br/>
	 *	String where = "id=#{id}";<br/>
	 *	params.put("photo", "/upload/userphoto/2015/4/7/crop_5415c7fe534a63366b48d388f204c328_20150407_133318.jpg");<br/>
	 *	params.put("id", "ab0fe284f8a045a4a49089f96f24fce4");<br/>
	 * */
	@Transactional(readOnly = false)  
	public int updateBySql(Map<String,Object> params,String set,String where);
	 
	
	
	/**
	 * 根据实体中注入的值查询对象
	 * @param  params
	 * @code<br/><br/>
	 * 		MapperHelper mh = MapperHelper.getMapperHelperInstance();<br/>
	 *		SqlParamsCriterionList spcList = mh.createSqlParamsCriterionList();<br/>
	 *		spcList.addParam("id", SQLKeyConstants.EQ,id);<br/>
	 *		mh.addSqlParamsCriterionList(spcList);<br/>
	 * */
	@Transactional(readOnly = true)  
	public T getByMapperHelper(Map<String,Object> params);
	
	/**
	 * 根据实体ID的值获得记录
	 * 使用CRUDTemplate操作
	 * */
	@Transactional(readOnly = true)  
	public T get(String id);
	
	/**
	 * 获得所有
	 * 使用CRUDTemplate操作
	 * */
	@Transactional(readOnly = true)  
	public List<T> getAll(String orderBys);
	
	/**
	 * 获得所有
	 * 使用CRUDTemplate操作
	 * */
	@Transactional(readOnly = true)  
	public T getByName(String name,String value);
	
	/**
	 * 使用MapperHelper对象进行查询
	 * @param  map
	 * @return List 
	 * @code
	 *  MapperHelper mh = MapperHelper.getMapperHelperInstance("sort desc");  <br>
	 *	SqlParamsCriterionList sl1 = MapperHelper.createSqlParamsCriterionList("code", SQLKeyConstants.ISNOTNULL);<br>
	 *	sl1.addParam("code", SQLKeyConstants.LIKE, "%A%");<br>
	 *  <br>
	 *	SqlParamsCriterionList sl2 = MapperHelper.createSqlParamsCriterionList("sort", SQLKeyConstants.EQ,"22");<br>
	 *	mh.addSqlParamsCriterionList(sl1);<br>
	 *	mh.addSqlParamsCriterionList(sl2);<br><br>
	 *  mh.addDynamicSql(" and code like ? and sort = ?");<br>
	 *	mh.addDynamicSqlValueMap(2,"22");<br>
	 *	mh.addDynamicSqlValueMap(1,"%A%");<br>
	 *	roleService.getListByMapperHelper(pageView, mh.getSqlParamsCriterionMap());<br>
	 *
	 *	@resultSql
	 *	select ID, NAME, DETAIL, SORT, CODE, MODIFY_TIME, CREATE_TIME, CREATOR, MODIFIER from IC_ROLE WHERE ( code is not null and code like ? ) or( sort = ? ) order by ?
	 * */
	@Transactional(readOnly = true)  
	public List<T> getListByMapperHelper(Map<String, Object> map);
	/**
	 * 使用MapperHelper对象进行查询
	 * @param  pageView
	 * @param  map
	 * @return List 
	 * @code
	 *  MapperHelper mh = MapperHelper.getMapperHelperInstance("sort desc");  <br>
	 *	SqlParamsCriterionList sl1 = MapperHelper.createSqlParamsCriterionList("code", SQLKeyConstants.ISNOTNULL);<br>
	 *	sl1.addParam("code", SQLKeyConstants.LIKE, "%A%");<br>
	 *  <br>
	 *	SqlParamsCriterionList sl2 = MapperHelper.createSqlParamsCriterionList("sort", SQLKeyConstants.EQ,"22");<br>
	 *	mh.addSqlParamsCriterionList(sl1);<br>
	 *	mh.addSqlParamsCriterionList(sl2);<br><br>
	 *  mh.addDynamicSql(" and code like ? and sort = ?");<br>
	 *	mh.addDynamicSqlValueMap(2,"22");<br>
	 *	mh.addDynamicSqlValueMap(1,"%A%");<br>
	 *	roleService.getListByMapperHelper(pageView, mh.getSqlParamsCriterionMap());<br>
	 *
	 *	@resultSql
	 *	select ID, NAME, DETAIL, SORT, CODE, MODIFY_TIME, CREATE_TIME, CREATOR, MODIFIER from IC_ROLE WHERE ( code is not null and code like ? ) or( sort = ? ) order by ?
	 * */
	@Transactional(readOnly = true)  
	public PageView<T> getPageViewByMapperHelper(PageView<T> pageView,Map<String, Object> map);
	/**
	 * 使用MapperHelper对象进行查询,获取转化成datagrid控件JSON数据的对象
	 * @param PageView
	 * @param   DTO对象
	 * @return JsonDataGrid 
	 * @code
	 *  MapperHelper mh = MapperHelper.getMapperHelperInstance("sort desc");  <br>
	 *	SqlParamsCriterionList sl1 = MapperHelper.createSqlParamsCriterionList("code", SQLKeyConstants.ISNOTNULL);<br>
	 *	sl1.addParam("code", SQLKeyConstants.LIKE, "%A%");<br>
	 *  <br>
	 *	SqlParamsCriterionList sl2 = MapperHelper.createSqlParamsCriterionList("sort", SQLKeyConstants.EQ,"22");<br>
	 *	mh.addSqlParamsCriterionList(sl1);<br>
	 *	mh.addSqlParamsCriterionList(sl2);<br><br>
	 *  mh.addDynamicSql(" and code like ? and sort = ?");<br>
	 *	mh.addDynamicSqlValueMap(2,"22");<br>
	 *	mh.addDynamicSqlValueMap(1,"%A%");<br>
	 *	roleService.getListByMapperHelper(pageView, mh.getSqlParamsCriterionMap());<br>
	 *
	 *	@resultSql
	 *	select ID, NAME, DETAIL, SORT, CODE, MODIFY_TIME, CREATE_TIME, CREATOR, MODIFIER from IC_ROLE WHERE ( code is not null and code like ? ) or( sort = ? ) order by ?
	 * */
	@Transactional(readOnly = true)  
	public JsonDataGrid getPageViewByMapperHelper(PageView<T> page,Map<String, Object> map,Class DTO_Clazz) ;
	
	@Transactional(readOnly = true)  
	public JsonDataGrid getDataGridJson(List<T> list,Class DTO_Clazz) ;
	@Transactional(readOnly = true)  
	public JsonDataGrid getDataGridJson(PageView<T> page,Class DTO_Clazz) ;
	
	/**
	 * 排序
	 * @param entity
	 * 使用CRUDTemplate操作
	 */
	@Transactional(readOnly = false)  
	public int exchangeSort(String entitId,int sort);
	
	/**
	 * 执行SQL语句
	 * @param sql SQL语句
	 * @param params MapperHelper.getSqlParamsMap() 查询条件MAP对象
	 * */
	@Transactional(readOnly = false) 
	public int executeSql(String sql,Map<String, Object> params);
	
	/**
	 * 获得count
	 * 使用CRUDTemplate操作
	 * */
	@Transactional(readOnly = true)  
	public int getCount(Map<String, Object> map);
	
	/**
	 * 验证code是否存在
	 * @param id 信息ID
	 * @param code 信息code
	 * 使用CRUDTemplate操作
	 * */
	@Transactional(readOnly = true)  
	public boolean validCode(String id,String code);
	
	/**
	 * 验证name value 是否存在
	 * @param id 信息ID
	 * @param name 信息name
	 * @param value 信息value
	 * 使用CRUDTemplate操作
	 * */
	@Transactional(readOnly = true)  
	public boolean validByName(String id,String name,String value);
	
	/**
	 * 获得下一个排序号
	 * */
	@Transactional(readOnly = true)  
	public int getNextSortNum();

	
	
}
