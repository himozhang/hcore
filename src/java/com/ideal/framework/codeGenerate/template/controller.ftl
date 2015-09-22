package ${packageName}.${moduleName}.${className?lower_case}.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ${packageName}.${moduleName}.${className?lower_case}.entity.${ClassName};
import ${packageName}.${moduleName}.${className?lower_case}.entity.${ClassName}DTO;
import ${packageName}.${moduleName}.${className?lower_case}.service.I${ClassName}Service;

import com.ricore.framework.constants.SQLConstants;
import com.ricore.framework.controller.impl.CRUDController;
import com.ricore.framework.exception.ControllerException;
import com.ricore.framework.mybatis.mapper.MapperHelper;
import com.ricore.framework.mybatis.mapper.SqlParamsCriterionList;
import com.ricore.framework.mybatis.page.PageView;
import com.ricore.framework.service.IBaseService;
import com.ricore.framework.utils.json.JsonDataGrid;
import com.ricore.framework.utils.json.JsonResult2JQueryEasyTree;
import com.ricore.framework.utils.json.TreeDTO;
import com.ricore.framework.utils.string.EmptyUtil;

/** 
* @ClassName:${ClassName}Controller.java
* @CreateTime ${CreateTime}
* @author:${author}
* @mail:${mail}
* @Description:${Description}-Controller - 由代码生成器生成
*/ 
@Controller
@RequestMapping(value="/${className?lower_case}")
public class ${ClassName}Controller extends CRUDController<${ClassName}>{

	@Autowired
	private I${ClassName}Service ${ClassName?uncap_first}Service;
	private PageView<${ClassName}> pageView = new PageView<${ClassName}>(1,10);
	
	 /**
	 * 返回到对应模块首页
	 * @return ModelAndView
	 * */
	@RequestMapping("/")
	public  ModelAndView index(HttpServletRequest request) {
		return this.perIndex(request);
	}
	
	@RequestMapping("/tree")
	@ResponseBody
	public JSON tree(HttpServletRequest request) throws Exception {
		String id = request.getParameter("id");
		String treeSearchKey = request.getParameter("treeSearchKey");
		logger.debug(" 查询结构树   come in tree.....treeSearchKey : "+treeSearchKey);
		//第一次加载，则加载出根节点下的第一级信息
		MapperHelper mh = MapperHelper.getMapperHelperInstance();
		mh.setOrderBy(getOrderBy());
		SqlParamsCriterionList spcList = mh.createSqlParamsCriterionList();
		
		//根据treeSearchKey 查询tree
		if(!EmptyUtil.isEmpty(treeSearchKey)){
			mh.addDynamicSql(" name like ? or code like ?");
			mh.addDynamicSqlValueMap("%"+treeSearchKey+"%");
			mh.addDynamicSqlValueMap("%"+treeSearchKey+"%");
		}else{
			//判断是否有ID，如果ID为空，则查询PID为空的，否则查询pid=id的数据
			if(EmptyUtil.isEmpty(id)){
				logger.debug("加载根目录");
				spcList.addParam("pid", SQLConstants.ISNULL);
			}else{
				logger.debug("加载下级目录，父级ID为："+id);
				spcList.addParam("pid", SQLConstants.EQ,id);
			}
		}
		
		//将sqlparamsList加入到mapperHelp中
		mh.addSqlParamsCriterionList(spcList);
		//获得mapperHelp中的SQL查询参数放入service中
		List<${ClassName}> ${className}List = ${ClassName?uncap_first}Service.getListByMapperHelper(mh.getSqlParamsMap());
		
		JsonResult2JQueryEasyTree data = new JsonResult2JQueryEasyTree();
		JSONObject obj = new JSONObject();
		JSONArray ja = new JSONArray();//存放一级数据字典，在初始化tree时，使用
		for (${ClassName} ${className} : ${className}List) {
			
			//如果superId为空，则将节点追加到根节点，在讲根节点放入JsonResult2JQueryEasyTree
			if(!EmptyUtil.isEmpty(treeSearchKey)){
				TreeDTO view = new TreeDTO(${className},false);
			//	view.addAttributes("code", ${className}.getCode());
				ja.add(view);
			}else{
				TreeDTO view = new TreeDTO(${className},!EmptyUtil.isEmpty(${className}.getChild${ClassName}s()));
			//	view.addAttributes("code", ${className}.getCode());
				if(EmptyUtil.isEmpty(id)){
					ja.add(view);
				}else{
					data.addItem(view);
				}
			}
		}
		//如果superId为空，则将节点追加到根节点，在讲根节点放入JsonResult2JQueryEasyTree
		if(EmptyUtil.isEmpty(id)){
			obj.put("id","");
			obj.put("text","根节点");
			obj.put("children", ja);
			data.addItem(obj);
		}
		
		return data.toJson();
	}
	
	@RequestMapping("/load")
	@ResponseBody
	public JSON load(HttpServletRequest request) throws Exception {
		String pid = request.getParameter("pid");
		String searchKey = request.getParameter("searchKey");
		
		this.pageConfig(pageView,request);
		//第一次加载，则加载出根组织节点下的第一级组织信息
		MapperHelper mh = MapperHelper.getMapperHelperInstance();
		mh.setOrderBy(getOrderBy(request));
		SqlParamsCriterionList spcList = mh.createSqlParamsCriterionList();
		if(EmptyUtil.isEmpty(pid)){
			logger.debug("加载数据字典根目录");
			spcList.addParam("pid", SQLConstants.ISNULL);
		}else{
			logger.debug("加载下级数据字典目录，父级ID为："+pid);
			spcList.addParam("pid", SQLConstants.EQ,pid);
		}
		
		//根据searchKey 查询tree
		if(!EmptyUtil.isEmpty(searchKey)){
			mh.addDynamicSql(" name like ? or code like ?");
			mh.addDynamicSqlValueMap("%"+searchKey+"%");
			mh.addDynamicSqlValueMap("%"+searchKey+"%");
		}
		
		mh.addSqlParamsCriterionList(spcList);
		
		JsonDataGrid data = ${ClassName?uncap_first}Service.getPageViewByMapperHelper(pageView,mh.getSqlParamsMap(),${ClassName}DTO.class);
		logger.info(data.successAsJson("获取数据成功").toString());
		return data.toJson();
	}
	
	/**
	 * inputByPid前置方法
	 * */
	@Override
	public ModelAndView preInputByPid(String pid,HttpServletRequest request){
		logger.debug("come in inputByPid @Override 前置方法 -> preInputByPid.....");
		ModelAndView modelandview = new ModelAndView();
		
		try {
			${ClassName} parent${ClassName} = ${ClassName?uncap_first}Service.get(pid);
			if(!EmptyUtil.isEmpty(parent${ClassName})){
				${ClassName} ${className} = new ${ClassName}();
				${className}.setParent${ClassName}(parent${ClassName});
				modelandview.addObject(this.EntityKeyName,${className});
			}
		} catch (Exception e) {
			e.printStackTrace();
			new ControllerException("根据pid : "+pid+"获得"+getControllerGenericClassName()+"对象失败！");
		}
		return modelandview;
	}
	 
	
	/**
	 * @return the pageView
	 */
	public PageView<${ClassName}> getPageView() {
		return pageView;
	}

	/**
	 * @param pageView the pageView to set
	 */
	public void setPageView(PageView<${ClassName}> pageView) {
		this.pageView = pageView;
	}
	 

	@Override
	public IBaseService<${ClassName}> getService() {
		return this.${ClassName?uncap_first}Service;
	}
}
