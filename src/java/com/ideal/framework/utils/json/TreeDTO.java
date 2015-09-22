package com.ideal.framework.utils.json;

import java.util.HashMap;
import java.util.Map;

import com.ideal.framework.constants.GlobalConstants;
import com.ideal.framework.utils.java.ReflectionUtils;

/** 
* 2015-4-10 下午03:40:07
* author:himo
* mail:zhangyao0905@gmail.com
* descript:将对象转换成Easyi-ui能认识的TREE JSON对象
*/ 
public class TreeDTO {
	private Map<String,Object> attributes=new HashMap<String, Object>();
	private boolean haschild = false;
	private boolean check = false;
	
	private Object obj;
	 
	
	public String getName() {
		return String.valueOf(ReflectionUtils.getFieldValue(this.obj, "name"));
	}
	
	public String getId() {
		return String.valueOf(ReflectionUtils.getFieldValue(this.obj, "id"));
	}
	
	public String getText(){
		String stateStr = "";
		if(ReflectionUtils.isFieldInBaseEntity(this.obj, "state")){
			String state = (String)ReflectionUtils.getFieldValue(this.obj, "state");
			if(GlobalConstants.YES.equals(state)){
				stateStr = " <span style='color:red;'>["+GlobalConstants.getName(GlobalConstants.YES)+"]</span>";
			}
		}
		return String.valueOf(ReflectionUtils.getFieldValue(this.obj, "name"))+stateStr;
	}
	
	public String getIconCls() {
		//如果有下级菜单，返回关闭状态的文件夹图标，否者显示文件图标
		if (!haschild) {
			return "menu_icon";
		} else{
			return "tree-folder";
		}
	}
	 
	public String getState() {
		//如果有下级菜单，返回关闭状态的文件夹图标，否者显示文件图标
		if (haschild) {
			return "closed";
		} else {
			return "open";
		}
	}
	
	public Map<String,Object> getAttributes(){
		return attributes;
	}
	
	public void addAttributes(String key,String value){
		attributes.put(key, value);
	}
	
	/**
	 * 将对象转换成Easyi-ui能认识的TREE JSON对象
	 * @param Object obj 实体属性对象
	 * @param boolean haschild 是否有子类
	 * */
	public TreeDTO(Object obj,boolean haschild) {
		this.obj = obj;
		this.haschild = haschild;
	}
	
	public void setChecked(boolean check){
		this.check = check;
	}

	public boolean getChecked(){
		return check;
	}
}
