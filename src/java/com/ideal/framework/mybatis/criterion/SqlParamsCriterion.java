package com.ideal.framework.mybatis.criterion;

import java.util.List;

/** 
* 2015-4-8 下午05:48:21
* author:himo
* mail:zhangyao0905@gmail.com
* descript:存放每一个SQL条件的键和值以及键值之间的逻辑关系
*/ 
public class SqlParamsCriterion {
	
	private String key;//键
	
	private Object value;//值
	
	private String condition;//条件

	private Object secondValue;//第二个值

	private boolean noValue;//是否不存在值

	private boolean singleValue;//是否只有一个值

	private boolean betweenValue;//是否有区间值

	private boolean listValue;//是否是数组值
	
	/**
	 * @return the condition
	 */
	public String getCondition() {
		return condition;
	}

	/**
	 * @param condition the condition to set
	 */
	public void setCondition(String condition) {
		this.condition = condition;
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * @return the secondValue
	 */
	public Object getSecondValue() {
		return secondValue;
	}

	/**
	 * @param secondValue the secondValue to set
	 */
	public void setSecondValue(Object secondValue) {
		this.secondValue = secondValue;
	}

	/**
	 * @return the noValue
	 */
	public boolean isNoValue() {
		return noValue;
	}

	/**
	 * @param noValue the noValue to set
	 */
	public void setNoValue(boolean noValue) {
		this.noValue = noValue;
	}

	/**
	 * @return the singleValue
	 */
	public boolean isSingleValue() {
		return singleValue;
	}

	/**
	 * @param singleValue the singleValue to set
	 */
	public void setSingleValue(boolean singleValue) {
		this.singleValue = singleValue;
	}

	/**
	 * @return the betweenValue
	 */
	public boolean isBetweenValue() {
		return betweenValue;
	}

	/**
	 * @param betweenValue the betweenValue to set
	 */
	public void setBetweenValue(boolean betweenValue) {
		this.betweenValue = betweenValue;
	}

	/**
	 * @return the listValue
	 */
	public boolean isListValue() {
		return listValue;
	}

	/**
	 * @param listValue the listValue to set
	 */
	public void setListValue(boolean listValue) {
		this.listValue = listValue;
	}

	 
	
	/**
	 * @return the 键
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param 键 the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}
	
	public SqlParamsCriterion(String key,String condition){
		this.condition = condition;
		this.key = key;
		this.noValue = true;
	}
	
	public SqlParamsCriterion(String key,String condition,Object value){
		this.condition = condition;
		this.key = key;
		this.value = value;
		 if (value instanceof List<?>) {
             this.listValue = true;
         } else {
             this.singleValue = true;
         }
	}

	public SqlParamsCriterion(String key,String condition, Object value, Object secondValue){
		 this.key= key;
		 this.condition = condition;
         this.value = value;
         this.secondValue = secondValue;
         this.betweenValue = true;
	}
}