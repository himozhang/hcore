package com.ideal.core.role.entity;

import com.ideal.framework.basecore.baseDto.BaseDTO;


/** 
* @ClassName:RoleDTO.java
* @CreateTime 2015-4-25 下午10:14:10
* @author:himo
* @mail:zhangyao0905@gmail.com
* @Description:角色数据传输类
*/ 
public class RoleDTO extends BaseDTO<Role>{
	
	/**
	 * @param entity
	 */
	public RoleDTO(Role entity) {
		super(entity);
	}

	public String getName() {
		return this.entity.getName();
	}
	
}
