package com.ideal.core.role.dao;

import java.util.List;

import com.ideal.core.role.entity.Role;
import com.ideal.framework.basecore.baseDao.BaseMapper;


public interface RoleDao  extends BaseMapper<Role>{

	public List<Role> getRolesByUserId(String id);
	
}
