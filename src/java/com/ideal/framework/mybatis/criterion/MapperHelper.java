package com.ideal.framework.mybatis.criterion;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ideal.framework.constants.SQLConstants;
import com.ideal.framework.mybatis.MyBatisCRUDTemplates;
import com.ideal.framework.utils.java.BeanUtil;
import com.ideal.framework.utils.string.EmptyUtil;
import com.ideal.framework.utils.string.StringUtils;

 

/** 
* 2015-4-8 下午05:48:21
* author:himo
* mail:zhangyao0905@gmail.com
* descript:mapper查询条件构造帮助类，需要结合mapper.xml文件中的相关解析SQL配合
*/ 
public class MapperHelper {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	
	/**
	 * 返回最终构建好的SQL规则Map对象，传递给service使用
	 * */
	private LinkedHashMap<String,Object> sqlParamsCriterionMapResult = new LinkedHashMap<String,Object>();
	/**
	 * sqlParamsCriterionCollection集合，每一个sqlParamsCriterionCollection之间使用“OR”连接
	 * */
	private LinkedList<SqlParamsCriterionCollection> sqlParamsCriterionCollectionList = new LinkedList<SqlParamsCriterionCollection>();
	
	//动态SQL
	private StringBuffer dynamicSql = new StringBuffer();
	//动态SQL对应值
	private LinkedHashMap<Integer,Object> dynamicSqlParamsValuesMap = new LinkedHashMap<Integer,Object>();
	//排序规则
	private String orderBy = "";
	
	/**
	 * 参数集合
	 * @return paramList
	 * */
	public static String PARAM_LIST_KEY = "paramList";
	/**
	 * 动态SQL
	 * @return dynamicSql
	 * */
	public static String DYNAMIC_SQL_KEY = "dynamicSql";
	
	
//	/**
//	 * 返回干净SqlParamsCriterionCollection对象
//	 * */
//	public  SqlParamsCriterionCollection createSqlParamsCriterionCollection(){
//		return SqlParamsCriterionCollection.getSqlParamsCriterionCollectionInit();
//	}
//	
//	
//	/**
//	 * @param key 查询对象
//	 * @param criterion 查询逻辑判断条件
//	 * 每一个addSqlParamsCriterion之间的关系为AND
//	 * */
//	public  void addSqlParamsCriterion(String key,String criterion){
//		this.sqlParamsCriterionCollectionList.add(SqlParamsCriterionCollection.getSqlParamsCriterionCollectionInit(key, criterion));
//	}
//	
//	/**
//	 * @param key 查询对象
//	 * @param criterion 查询逻辑判断条件
//	 * @param value 查询值
//	 * 每一个addSqlParamsCriterion之间的关系为AND
//	 * */
//	public  void addSqlParamsCriterion(String key,String criterion,String value){
//		this.sqlParamsCriterionCollectionList.add( SqlParamsCriterionCollection.getSqlParamsCriterionCollectionInit(key, criterion, value));
//	}
//	/**
//	 * @param key 查询对象
//	 * @param criterion 查询逻辑判断条件
//	 * @param value 查询值
//	 * @param secondValue 查询第二个值
//	 * 每一个addSqlParamsCriterion之间的关系为AND
//	 * */
//	public  void addSqlParamsCriterion(String key,String criterion,String value,String secondValue){
//		this.sqlParamsCriterionCollectionList.add( SqlParamsCriterionCollection.getSqlParamsCriterionCollectionInit(key, criterion, value, secondValue));
//	}
	
	
	/**
	 * 验证sqlParamsCriterionCollectionList是否存在对象,用于mybaties循环时,判断是否需要进行循环操作
	 * */
	 public boolean isValid() {
         return this.sqlParamsCriterionCollectionList.size() > 0;
     }
	
	/**
	 *sqlParamsCriterionCollection集合，每一个sqlParamsCriterionCollection之间使用“or”连接
	 *@param SqlParamsCriterionCollection SQL规则集合对象，SqlParamsCriterionCollection中每一个SqlParamsCriterion之间使用“AND”连接
	 * */
	public void addSqlParamsCriterionCollection(SqlParamsCriterionCollection sqlParamsCriterionCollection){
		this.sqlParamsCriterionCollectionList.add(sqlParamsCriterionCollection);
	}
	/**
	 * 传递给Service使用的SQL条件封装后的最终Map对象
	 * @return MyBaties使用的查询Map对象HashMap<String,Object>
	 * */
	public HashMap<String,Object> getSqlParamsCriterionMapResult(){
		this.sqlParamsCriterionMapResult.put(SQLConstants.ORDER_BY_KEY,orderBy);
		this.sqlParamsCriterionMapResult.put(MapperHelper.PARAM_LIST_KEY, this.sqlParamsCriterionCollectionList);
		this.sqlParamsCriterionMapResult.put(MapperHelper.DYNAMIC_SQL_KEY, this.getDynamicSql());
		return sqlParamsCriterionMapResult;
	}
	
	/**
	 * 将HelperParams格式化为 name=#{name}标准sql语句，并将条件值存入map
	 * */
	public HashMap<String,Object> formatMapperHelperParams2String(){
		return MapperHelper.formatMapperHelperParams2String(getSqlParamsCriterionMapResult());
	}
	
	/**
	 * 返回干净MapperHelper对象
	 * */
	public static MapperHelper getMapperHelperInstance(){
		return new MapperHelper();
	}
	
	/**
	 * @param orderBy 排序条件
	 * */
	public static MapperHelper getMapperHelperInstance(String orderBys){
		return new MapperHelper(orderBys);
	}
	
	private MapperHelper(){}
	
	private MapperHelper(String orderBy){
		this.orderBy = orderBy;
	}
	
	/**
	 * @param orderBy 排序条件
	 * */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * @param orderBy 排序条件
	 * */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
	/**
	 * 动态SQL
	 * @param sql SQL语句
	 * @see "id=? and name=? and date begin ? and ? "
	 * */
	public void addDynamicSql(String sql) {
		if(!EmptyUtil.isEmpty(sql)){
			if(StringUtils.leftTrim(sql).startsWith("and")){
				sql = sql.replaceFirst("and","");
			}
			this.dynamicSql.append(" and ("+sql+")");
		}
		
	}
	 
	/**
	 * 动态SQL值
	 * @param value SQL语句所对应的参数值
	 * @see <b>如果动态SQL为： name=? and date begin ? and ? </b><br/>
	 * @see <b>addDynamicSqlValueMap调用方式为：</b> <br/>
	 * 		addDynamicSqlValueMap（name对应的值）; <br/>
	 *      addDynamicSqlValueMap（date对应的值1）;<br/>
	 *      addDynamicSqlValueMap（date对应的值2）;
	 * */
	public void addDynamicSqlValueMap(Object value) {
		int num = this.dynamicSqlParamsValuesMap.size()+1;
		this.dynamicSqlParamsValuesMap.put(num, MapperHelper.transactSQLInjection(value));
	}
	/**
	 * 动态SQL值
	 * @param Map<Integer,Object> valuesMap
	 * */
	public void addDynamicSqlValueMap(LinkedHashMap<Integer,Object> sqlParamsCriterionMap) {
		for(Integer num :sqlParamsCriterionMap.keySet()){
			Object value = sqlParamsCriterionMap.get(num);
			int size = this.dynamicSqlParamsValuesMap.size()+1;
			this.dynamicSqlParamsValuesMap.put(size,value);
		}
	}
	
	/**
	 * 获得动态SQL
	 * */
	public String getDynamicSql() {
		if(!EmptyUtil.isEmpty(this.dynamicSql)){
			String dynamicSql = StringUtils.paraseQ(this.dynamicSql.toString(),this.dynamicSqlParamsValuesMap);
			if(!EmptyUtil.isEmpty(dynamicSql)){
				logger.debug("------>  getDynamicSql : "+dynamicSql);
			}
			return dynamicSql;
		}
		return "";
	}
	
	/**
	 * 替换所有的特殊字符,防止SQL注入
	 * */
	 public static Object transactSQLInjection(Object obj){
		 if(obj instanceof String){
			// return String.valueOf(obj).replaceAll(".*([';]+|(--)+).*", " ");
			 return String.valueOf(obj).replaceAll("([';])+|(--)+","");
		 }else{
			 return obj;
		 }
     }
	 
	/**
	 * 将HelperParams格式化为 name=#{name}标准sql语句，并将条件值存入map
	 * */
	public static HashMap<String,Object> formatMapperHelperParams2String(Object obj){
		Map<String,Object> params = (HashMap<String,Object>) obj;
//		Object entity = params.get("entity");
		HashMap<String,Object> paramsMap = new HashMap<String,Object>();
		List<SqlParamsCriterionCollection> SqlParamsCriterionCollectionList =(List<SqlParamsCriterionCollection>) params.get(PARAM_LIST_KEY);
		StringBuffer first = new StringBuffer();
		//每一个SqlParamsCriterionList之间的关系为OR
		for(SqlParamsCriterionCollection sqlParamsCriterionCollection : SqlParamsCriterionCollectionList){
			List<SqlParamsCriterion> sqlParamsCriterionList = sqlParamsCriterionCollection.getsqlParamsCriterionList();
			StringBuffer second=new StringBuffer();
			//每一个criterion之间的关系为and
			for(SqlParamsCriterion criterion :sqlParamsCriterionList){
				String key = BeanUtil.camelhumpToUnderline(criterion.getKey());
				if(criterion.isNoValue()){
					second.append(SQLConstants.AND).append(key).append(criterion.getCondition());
				}else if(criterion.isSingleValue()){
					second.append(SQLConstants.AND).append(key).append(criterion.getCondition()).append("#{"+criterion.getKey()+"}");
					paramsMap.put(criterion.getKey(), transactSQLInjection(criterion.getValue()));
				}else if(criterion.isBetweenValue()){
					second.append(SQLConstants.AND).append(key).append(criterion.getCondition()).append("#{"+criterion.getKey()+"_1}").append(SQLConstants.AND).append("#{"+criterion.getKey()+"_2}");
					paramsMap.put(criterion.getKey()+"_1", transactSQLInjection(criterion.getValue()));
					paramsMap.put(criterion.getKey()+"_2", transactSQLInjection(criterion.getSecondValue()));
				}else if(criterion.isListValue()){
					second.append(SQLConstants.AND).append(key).append(criterion.getCondition());
					List<Object> listValues = (List<Object>)criterion.getValue();
					StringBuffer listValue=new StringBuffer("(");
					for(int i=0;i<listValues.size();i++){
						Object value = listValues.get(i);
						listValue.append(",#{").append(criterion.getKey()+i).append("}");
						paramsMap.put(criterion.getKey()+i,transactSQLInjection(value));
					}
					String listValueStr = listValue.toString().replaceFirst(",","")+")";
					second.append(listValueStr);
				}
			}
			
			String secondStr = second.toString().replaceFirst(SQLConstants.AND,"");
			if(!EmptyUtil.isEmpty(secondStr)){
				secondStr = "("+secondStr+")";
			}
			first.append(SQLConstants.OR+secondStr);
		}
		String firstStr = first.toString().replaceFirst(SQLConstants.OR,"");
		String dynamicSql = String.valueOf(params.get(DYNAMIC_SQL_KEY));
		if(EmptyUtil.isEmpty(firstStr)){
			firstStr = " 1=1 ";
		}
		String where = firstStr+dynamicSql;
		paramsMap.put(SQLConstants.WHERE_KEY, where);
		return paramsMap;
	}

}
