package com.ideal.framework.mybatis.criterion;

import java.util.ArrayList;
import java.util.List;

/** 
* 2015-4-8 下午05:48:21
* author:himo
* mail:zhangyao0905@gmail.com
* descript:存放一组查询条件，如果有多个当前对象，每个对象间的关系为 OR 查询
*/ 
public class SqlParamsCriterionCollection {
	
	private List<SqlParamsCriterion> sqlParamsCriterionList = new ArrayList<SqlParamsCriterion>();
	
	/**
	 * 验证sqlParamsCriterionList是否存在对象,用于mybaties循环时,判断是否需要进行循环操作
	 * */
	 public boolean isValid() {
         return this.sqlParamsCriterionList.size() > 0;
     }
	
	/**
	 * 返回干净SqlParamsCriterionHelper对象
	 * */
	public static SqlParamsCriterionCollection getSqlParamsCriterionCollectionInit(){
		return new SqlParamsCriterionCollection();
	}
	
	/**
	 * @param key 查询对象
	 * @param criterion 查询逻辑判断条件
	 * */
	public static SqlParamsCriterionCollection getSqlParamsCriterionCollectionInit(String key,String criterion){
		return new SqlParamsCriterionCollection(key, criterion);
	}
	
	/**
	 * @param key 查询对象
	 * @param criterion 查询逻辑判断条件
	 * @param value 查询值
	 * */
	public static SqlParamsCriterionCollection getSqlParamsCriterionCollectionInit(String key,String criterion,String value){
		return new SqlParamsCriterionCollection(key, criterion, value);
	}
	/**
	 * @param key 查询对象
	 * @param criterion 查询逻辑判断条件
	 * @param value 查询值
	 * @param secondValue 查询第二个值
	 * */
	public static SqlParamsCriterionCollection getSqlParamsCriterionCollectionInit(String key,String criterion,String value,String secondValue){
		return new SqlParamsCriterionCollection(key, criterion, value, secondValue);
	}
	
	private SqlParamsCriterionCollection(){}
	
	private SqlParamsCriterionCollection(String key,String criterion){
		this.addParam(key, criterion);
	}
	
	private SqlParamsCriterionCollection(String key,String criterion,String value){
		this.addParam(key, criterion, value);
	}
	
	private SqlParamsCriterionCollection(String key,String criterion,String value,String secondValue){
		this.addParam(key, criterion, value, secondValue);
	}
	
	/**
	 * @return 查询条件<SqlParamsCriterion>  
	 * */
	public List<SqlParamsCriterion> getsqlParamsCriterionList(){
		return this.sqlParamsCriterionList;
	}
	
	/**
	 * @param key 查询对象
	 * @param criterion 查询逻辑判断条件
	 * */
	public  void addParam(String key,String criterion){
		SqlParamsCriterion sqlParamsCriterion = new SqlParamsCriterion(key,criterion);
		sqlParamsCriterionList.add(sqlParamsCriterion);
	}
	/**
	 * @param key 查询对象
	 * @param criterion 查询逻辑判断条件
	 * @param value 查询值
	 * */
	public  void addParam(String key,String criterion,String value){
		SqlParamsCriterion sqlParamsCriterion = new SqlParamsCriterion(key,criterion,value);
		sqlParamsCriterionList.add(sqlParamsCriterion);
	}
	/**
	 * @param key 查询对象
	 * @param criterion 查询逻辑判断条件
	 * @param value 查询值
	 * @param secondValue 查询第二个值
	 * */
	public  void addParam(String key,String criterion,String value,String secondValue){
		SqlParamsCriterion sqlParamsCriterion = new SqlParamsCriterion(key,criterion,value,secondValue);
		sqlParamsCriterionList.add(sqlParamsCriterion);
	}
	/**
	 * @param key 查询对象
	 * @param criterion 查询逻辑判断条件
	 * @param value 查询值
	 * @param secondValue 查询第二个值
	 * */
	public  void addParam(String key,String criterion,Object obj){
		SqlParamsCriterion sqlParamsCriterion = new SqlParamsCriterion(key,criterion,obj);
		sqlParamsCriterionList.add(sqlParamsCriterion);
	}

}
