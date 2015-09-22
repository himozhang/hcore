package com.ideal.core.role.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.ideal.framework.basecore.baseEntity.IdEntity;

@Table(name = "IC_ROLE")
@SuppressWarnings("serial")
public class Role extends IdEntity{
	
	//登录名
    private String name;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
    
}
