package com.ideal.core.user.entity;

import java.util.List;

import javax.persistence.Table;
import javax.persistence.Transient;

import com.ideal.core.role.entity.Role;
import com.ideal.framework.basecore.baseEntity.IdEntity;

@Table(name = "IC_USER")
@SuppressWarnings("serial")
public class User extends IdEntity{
	
	//登录名
    private String loginName;
    //密码
    private String password;
    
    //用户角色集合
    @Transient
    private List<Role> roles;  
	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}
	/**
	 * @param loginName the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the roles
	 */
	public List<Role> getRoles() {
		return roles;
	}
	/**
	 * @param roles the roles to set
	 */
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	
}
