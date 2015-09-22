package com.ideal.framework.ldap;

import java.util.List;

import com.ideal.framework.exception.LDAPOperationException;

public interface ILDAPComponent<T> {

	/**
	 * 获取单个实体信息
	 */
	public abstract T queryObject(String uid) throws LDAPOperationException;
	
	/**
	 * 获取所有实体信息
	 * 使用传入实体被标注searchFilter注解的值作为检索条件
	 * 被注解的属性中的值作为检索值，若属性值为空，则检索值为"*"，即目录服务中所有带该属性的对象
	 * 例如：
	 * 				@SearchFilter("uid")
	 *					private String searchFilter =  "abc";
	 *	即检索条件为   uid=abc
	 *				@SearchFilter("mail")
	 *					private String searchFilter =  "";
	 * 即检索条件为   mail=*
	 * 				@SearchFilter("ou")
	 *					private String searchFilter =  "abc*";
	 * 即检索条件为   ou=abc*
	 * 该值可以通过setter方法传入
	 */
	public abstract List<T> queryObjects(String searchFilter) throws LDAPOperationException;
	
	/**
	 * 处理同queryObject
	 * @param searchFilte 检索条件
	 * @param length   获取数据对象个数
	 */
	public abstract List<T> queryObjects(String searchFilte,int length) throws LDAPOperationException;
	
	/**
	 * @SearchFilter("uid")
	 * 		uid=*
	 */
	public abstract List<T> queryObjects() throws LDAPOperationException ;
	
	/**
	 * @SearchFilter("uid")
	 * 		uid=*
	 * @param length 对象个数
	 */
	public abstract List<T> queryObjects(int length) throws LDAPOperationException;
	
	/**
	 * @param T
	 * 添加一个Context对象
	 */
	public abstract boolean addObject(T t) throws LDAPOperationException;
	
	/**
	 * @param T 
	 * 修改一个Context对象
	 */
	public abstract boolean modifyObject(T t) throws LDAPOperationException;
	
	/**
	 * @param T 
	 *  删除一个Context对象
	 */
	public abstract boolean deleteObject(T t) throws LDAPOperationException ;
}