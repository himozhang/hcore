package com.ideal.core.role.service.imp;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ideal.core.role.dao.RoleDao;
import com.ideal.core.role.entity.Role;
import com.ideal.core.role.service.IRoleService;
import com.ideal.framework.annotation.ChooseDataSource;
import com.ideal.framework.basecore.baseDao.BaseMapper;
import com.ideal.framework.basecore.baseService.impl.BaseServiceImpl;
import com.ideal.framework.datasource.DataSourceInstances;
import com.ideal.framework.page.PageView;
import com.ideal.framework.utils.json.JsonDataGrid;


@Transactional
@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements IRoleService {

	@Autowired
	private RoleDao roleDao;
	
	/* (非 Javadoc)
	* <p>Title: getMapper</p>
	* <p>Description: </p>
	* @return
	* @see com.ideal.framework.basecore.service.impl.BaseServiceImpl#getMapper()
	*/
	@Override
	public BaseMapper<Role> getMapper() {
		return roleDao;
	}

	/* (非 Javadoc)
	* <p>Title: getPageViewByMapperHelperMYSQL</p>
	* <p>Description: </p>
	* @param page
	* @param map
	* @param DTO_Clazz
	* @return
	* @see com.ideal.core.role.service.IRoleService#getPageViewByMapperHelperMYSQL(com.ideal.framework.page.PageView, java.util.Map, java.lang.Class)
	*/
	@Override
	@ChooseDataSource(DataSourceInstances.MYSQL)
	public JsonDataGrid getPageViewByMapperHelperSaveMYSQL(PageView<Role> page,
			Map<String, Object> map, Class DTO_Clazz) {
		return this.getPageViewByMapperHelper(page, map, DTO_Clazz);
	}
 
}
