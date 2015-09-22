package com.ideal.core.role.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ideal.core.role.entity.Role;
import com.ideal.core.role.entity.RoleDTO;
import com.ideal.core.role.service.IRoleService;
import com.ideal.framework.basecore.baseController.BaseController;
import com.ideal.framework.page.PageView;


@Controller
@RequestMapping("/role")
public class RoleController extends BaseController{
	
	 @Autowired
	 private IRoleService roleService;
	 
	 private PageView<Role> pageView = new PageView<Role>(1,10);

	@RequestMapping("/getremoteUserData")
	@ResponseBody
	public JSON  getremoteUserData() throws IOException{
	 	roleService.getPageViewByMapperHelperSaveMYSQL(pageView, null, RoleDTO.class);
		 return null;
	}
	 
}
