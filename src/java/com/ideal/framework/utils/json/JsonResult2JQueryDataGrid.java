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
 * 操作是否成功的JSON对象表示。
 * 
 * @author shenlei@ideal.sh.cn
 */
public class JsonResult2JQueryDataGrid extends JsonResult {
	
	/**记录LIST**/
	private List rows = new ArrayList();
	/**总记录**/
	private long total=0;
	//总页数
	private long pageCount =0;
	//当前页
	private int pageIndex = 0;
	private String stat;
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

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
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
	 * @param items
	 */
	public void addAll(Collection items){
		this.rows.addAll(items);
	}	

	/**
	 * 添加数据
	 * @param obj
	 */
	public void addItem(Object obj){
		this.rows.add(obj);
	}
	
	/**
	 * 移除数据
	 * @param obj
	 */
	public void removeItem(Object obj){
		this.rows.remove(obj);
	}
	
	/**
	 * 获得成功的操作结果,直接以JSON表示
	 * @param message
	 * @return
	 */
	public JSON successAsJson() {
		this.setStat("ok");
		return this.toJson();
	}	
	 

	public JSON toJson() {
		return JSONObject.fromObject(this);
	}



	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
	public long getTotal() {
		return total;
	}
	/**总记录**/
	public void setTotal(long total) {
		this.total = total;
	}
}
