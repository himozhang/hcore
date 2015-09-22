package com.ideal.core.user.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ideal.core.user.entity.User;
import com.ideal.core.user.entity.UserDTO;
import com.ideal.core.user.service.IUserService;
import com.ideal.framework.basecore.baseController.BaseController;
import com.ideal.framework.constants.SQLConstants;
import com.ideal.framework.datasource.DataSourceInstances;
import com.ideal.framework.datasource.DataSourceSwitch;
import com.ideal.framework.mybatis.criterion.MapperHelper;
import com.ideal.framework.mybatis.criterion.SqlParamsCriterionCollection;
import com.ideal.framework.page.PageView;


@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
	
	 @Autowired
	 private IUserService userService;
	 
	 private PageView<User> pageView = new PageView<User>(1,10);
 
	@RequestMapping("/toRemoteUserPage")
	public String  toRemoteUserPage(){
		try {
			return "RemoteUser";
		} catch (Exception e) {
			return "error";
		}
	}
	
	@RequestMapping("/getremoteUserData")
	@ResponseBody
	public JSON  getremoteUserData() throws IOException{
		DataSourceSwitch.setDataSourceType(DataSourceInstances.MYSQL);
		MapperHelper mh = MapperHelper.getMapperHelperInstance();
		SqlParamsCriterionCollection sqlParamsCriterionCollection = SqlParamsCriterionCollection.getSqlParamsCriterionCollectionInit();
		sqlParamsCriterionCollection.addParam("id", SQLConstants.EQ,"40284e7140ed630f0140ed8e61d2020f");
		mh.addSqlParamsCriterionCollection(sqlParamsCriterionCollection);
		userService.getPageViewByMapperHelper(pageView, mh.getSqlParamsCriterionMapResult(), UserDTO.class);
		System.out.println("pageView.getRowCount(): "+pageView.getRecords().get(0).getRoles().get(0).getName());
		return null;
	}
}
