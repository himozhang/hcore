package com.ideal.core.role.service;

import java.util.Map;

import com.ideal.core.role.entity.Role;
import com.ideal.framework.annotation.DataSourceAnno;
import com.ideal.framework.basecore.baseService.IBaseService;
import com.ideal.framework.datasource.DataSourceInstances;
import com.ideal.framework.page.PageView;
import com.ideal.framework.utils.json.JsonDataGrid;



public interface IRoleService extends IBaseService<Role>{
	
	@DataSourceAnno(ds=DataSourceInstances.MYSQL)
	public JsonDataGrid getPageViewByMapperHelperSaveMYSQL(PageView<Role> page,Map<String, Object> map,Class DTO_Clazz) ; 
	
}
