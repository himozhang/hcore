package com.ideal.framework.codeGenerate.table;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.ideal.framework.utils.java.BeanUtil;

/** 
 * @ClassName:EntityObj.java
 * @CreateTime 2015-4-23 下午05:31:13
 * @author:himo
 * @mail:zhangyao0905@gmail.com
 * @Description:存放数据库表字段列集合
 */
public class ColumnDataConnections {
	
	private List<ColumnData> columnDataList = new ArrayList<ColumnData>();
	private Set<String> javaFieldFullTypeSet=new HashSet<String>();  
	private List<String> parentColumnDataList = new ArrayList<String>();
	
	/**
	 * 获得所有的数据库列字段名
	 * */
	public String getAllColumn(){
		StringBuilder sb = new StringBuilder();
		
		for(String parnetC :parentColumnDataList){
			sb.append(","+StringUtils.upperCase(parnetC));
		}
		for(ColumnData columnData:columnDataList){
			sb.append(","+columnData.getColumnName());
		}
		return sb.toString().replaceFirst(",","");
	}
	
	/**
	 * @return the columnDataList
	 */
	public List<ColumnData> getColumnDataList() {
		return columnDataList;
	}

	/**
	 * @param columnDataList the columnDataList to set
	 */
	public void addColumnData(ColumnData columnData) {
		this.columnDataList.add(columnData);
	}

	/**
	 * @return the javaFieldFullTypeSet
	 */
	public Set getJavaFieldFullTypeSet() {
		return javaFieldFullTypeSet;
	}

	/**
	 * @param javaFieldFullTypeSet the javaFieldFullTypeSet to set
	 */
	public void addJavaFieldFullTypeSet(String javaFieldFullType) {
		this.javaFieldFullTypeSet.add(javaFieldFullType);
	}

	
	public static void main(String[] args){
		Set  set=new HashSet();  
		set.add("1");
		set.add("1");
		set.add("1");
		for( Iterator   it = set.iterator();  it.hasNext(); )  
		{               
		    System.out.println("value="+it.next().toString());              
		}   
	}

	/**
	 * @return the parentColumnDataList
	 */
	public List<String> getParentColumnDataList() {
		return parentColumnDataList;
	}

	/**
	 * @param parentColumnDataList the parentColumnDataList to set
	 */
	public void setParentColumnDataList(Class clazz) {
		for(Field field:clazz.getDeclaredFields()){
			if(!"serialVersionUID".equals(field.getName())){
				this.parentColumnDataList.add(BeanUtil.camelhumpToUnderline(field.getName()));
			}
		 }
		 for(Field field:clazz.getSuperclass().getDeclaredFields()){
			if(!"serialVersionUID".equals(field.getName())){
				this.parentColumnDataList.add(BeanUtil.camelhumpToUnderline(field.getName()));
			}
		 }
	}
}
