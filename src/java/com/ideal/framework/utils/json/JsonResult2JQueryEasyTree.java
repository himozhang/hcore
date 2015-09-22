package com.ideal.framework.utils.json;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSON;
import net.sf.json.JSONArray;

/**
 * 操作是否成功的JSON对象表示。
 * 
 * @author cscz89@126.com
 */
public class JsonResult2JQueryEasyTree {

	/** 记录LIST **/
	private List<Object> nodes = new ArrayList<Object>();

	/**
	 * 添加数据
	 * 
	 * @param obj
	 */
	public void addItem(Object obj) {
		this.nodes.add(obj);
	}

	/**
	 * 移除数据
	 * 
	 * @param obj
	 */
	public void removeItem(Object obj) {
		this.nodes.remove(obj);
	}

	/**
	 * 获得成功的操作结果,直接以JSON表示
	 * 
	 * @param message
	 * @return
	 */
	public JSON successAsJson() {
		return this.toJson();
	}

	public JSON toJson() {
		return JSONArray.fromObject(nodes);
	}

}
