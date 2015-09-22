package com.ideal.framework.basecore.baseDto;

import com.ideal.framework.basecore.baseEntity.IdEntity;


/** 
 * @ClassName:BaseDTO.java
 * @CreateTime 2015-4-23 下午09:59:52
 * @author:himo
 * @mail:zhangyao0905@gmail.com
 * @Description:
 */
public abstract class BaseDTO<T> {
	
	private String isfirst;
	private String islast;
	
	public T entity;
	
	public BaseDTO(T entity) {
		this.entity = entity;
	}
	
	public String getId() {
		if(entity instanceof IdEntity){
			return ((IdEntity)this.entity).getId();
		}else{
			return "";
		}
	}
	
	public String getIsfirst() {
		return isfirst;
	}

	public void setIsfirst(String isfirst) {
		this.isfirst = isfirst;
	}

	public String getIslast() {
		return islast;
	}

	public void setIslast(String islast) {
		this.islast = islast;
	}

}
