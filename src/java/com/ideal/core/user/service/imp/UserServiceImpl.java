package com.ideal.core.user.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ideal.core.user.dao.UserDao;
import com.ideal.core.user.entity.User;
import com.ideal.core.user.service.IUserService;
import com.ideal.framework.basecore.baseDao.BaseMapper;
import com.ideal.framework.basecore.baseService.impl.BaseServiceImpl;


@Transactional
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements IUserService {

	@Autowired
	private UserDao userDao;
	
	/* (非 Javadoc)
	* <p>Title: getMapper</p>
	* <p>Description: </p>
	* @return
	* @see com.ideal.framework.basecore.service.impl.BaseServiceImpl#getMapper()
	*/
	@Override
	public BaseMapper<User> getMapper() {
		return userDao;
	}
 
}
