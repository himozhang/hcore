package com.ideal.core.user.entity;

import com.ideal.framework.basecore.baseDto.BaseDTO;

public class UserDTO extends BaseDTO<User>{

	/**
	 * @param entity
	 */
	public UserDTO(User entity) {
		super(entity);
	}
	

	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return this.entity.getLoginName();
	}
	
	/**
	 * @return the password
	 */
	public String getPassword() {
		return this.entity.getPassword();
	}
	 
	 
}
