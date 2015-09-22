package com.ideal.framework.utils.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JsonListResult extends JsonResult {

	/**记录**/
	private List items = new ArrayList();
	
	
	public List getItems() {
		return items;
	}

	public void setItems(List items) {
		this.items = items;
	}
	
	/**
	 * 添加数据
	 * @param obj
	 */
	public void addItem(Object obj){
		this.items.add(obj);
	}
	
	/**
	 * 移除数据
	 * @param obj
	 */
	public void removeItem(Object obj){
		this.items.remove(obj);
	}
	
	/**
	 * 添加数据集合
	 * @param items
	 */
	public void addAll(Collection items){
		this.items.addAll(items);
	}	
	
	
	
	

}
