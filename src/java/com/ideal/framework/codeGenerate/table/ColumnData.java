package com.ideal.framework.codeGenerate.table;

import com.ideal.framework.utils.java.BeanUtil;
import com.ideal.framework.utils.string.EmptyUtil;

/** 
* @ClassName:ColumnData.java
* @CreateTime 2015-4-23 下午04:50:17
* @author:himo
* @mail:zhangyao0905@gmail.com
* @Description:存放数据库表字段列属性
*/ 
public class ColumnData {
	private String columnName;
	private String columnType;
	private String columnComment;
	private String columnTypeLength;
	private String javaFieldName;
	private String javaFieldFullType;
	private String javaFieldSortType;
	
	 
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	 
	public String getColumnComment() {
		return columnComment;
	}
	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}
	 
	/**
	 * @return the columnType
	 */
	public String getColumnType() {
		return columnType;
	}
	/**
	 * @param columnType the columnType to set
	 */
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	/**
	 * @return the columnTypeLength
	 */
	public String getColumnTypeLength() {
		return columnTypeLength;
	}
	/**
	 * @param columnTypeLength the columnTypeLength to set
	 */
	public void setColumnTypeLength(String columnTypeLength) {
		this.columnTypeLength = columnTypeLength;
	}
	/**
	 * @return the javaFieldName
	 */
	public String getJavaFieldName() {
		return javaFieldName;
	}
	/**
	 * @param javaFieldName the javaFieldName to set
	 */
	public void setJavaFieldName(String columnName) {
		this.javaFieldName = columnName != null? BeanUtil.underlineToCamelhump(columnName): "";
	}
	/**
	 * @return the javaFieldFullType
	 */
	public String getJavaFieldFullType() {
		return javaFieldFullType;
	}
	/**
	 * @param javaFieldFullType the javaFieldFullType to set
	 */
	public void setJavaFieldFullType(String javaFieldFullType) {
		this.javaFieldFullType = javaFieldFullType;
	}
	/**
	 * @return the javaFieldSortType
	 */
	public String getJavaFieldSortType() {
		return javaFieldSortType;
	}
	/**
	 * @param javaFieldSortType the javaFieldSortType to set
	 */
	public void setJavaFieldSortType(String javaFieldFullType) {
		if(!EmptyUtil.isEmpty(javaFieldFullType)){
			String[] types = javaFieldFullType.split("\\.");
			this.javaFieldSortType =   types[types.length-1];
		}else{
			this.javaFieldSortType = "";
		}
	}
 
	 
}