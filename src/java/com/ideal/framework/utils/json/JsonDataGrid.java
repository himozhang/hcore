/**
 * eip
 * cn.sh.ideal.core.util.JSONOperateResult.java
 */
package com.ideal.framework.utils.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.json.JSON;
import net.sf.json.JSONObject;


/**
 * 提供给DataGrid控件的JSON对象
 * 
 * @author himo.zhang
 */
public class JsonDataGrid extends JsonResult {

	
	
	/** 记录集合* */
	private List<Object> rows = new ArrayList<Object>();
	/** 总记录* */
	private long total = 0;
	// 总页数
	private long pageCount = 0;
	// 当前页
	private int pageIndex = 0;
	private String state;
	private String msg;
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public long getPageCount() {
		return pageCount;
	}

	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	/**
	 * 添加数据集合
	 * 
	 * @param items
	 */
	public void addAll(Collection<Object> items) {
		this.rows.addAll(items);
	}

	/**
	 * 添加数据
	 * 
	 * @param obj
	 */
	public void addItem(Object obj) {
		this.rows.add(obj);
	}

	/**
	 * 移除数据
	 * 
	 * @param obj
	 */
	public void removeItem(Object obj) {
		this.rows.remove(obj);
	}

	/**
	 * 获得成功的操作结果,直接以JSON表示
	 * 
	 * @param message
	 * @return
	 */
	public JSON successAsJson(String message) {
		this.setState("ok");
		this.setMsg(message);
		return this.toJson();
	}

	public JSON toJson() {
		return JSONObject.fromObject(this);
	}
	
	/**
	 * 返回String格式后的数据
	 * @return string
	 */
	public String toJsonString() {
		return this.toJson().toString();
	}

	public List<Object> getRows() {
		return rows;
	}

	public void setRows(List<Object> rows) {
		this.rows = rows;
	}

	public long getTotal() {
		return total;
	}

	/** 总记录* */
	public void setTotal(long total) {
		this.total = total;
	}
	
	 
	
}
