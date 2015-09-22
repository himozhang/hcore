package com.ideal.framework.utils.json;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ideal.framework.page.PageView;
import com.ideal.framework.utils.java.ReflectionUtils;

/** 
 * @ClassName:JsonDataGridReturn.java
 * @CreateTime 2015-9-7 下午05:21:53
 * @author:himo
 * @mail:zhangyao0905@gmail.com
 * @Description:返回JsonDataGrid封装后的对象
 */
public class JsonDataGridReturn<T> {
	
	private   Logger logger = LoggerFactory.getLogger(JsonDataGrid.class);

	/**
	 * 获取转化成datagrid控件JSON数据的对象
	 * @param PageView
	 * @param   DTO对象
	 * @throws NoSuchMethodException 
	 */
	public JsonDataGrid getDataGridJson(List<T> list,T tobj,Class DTO_Clazz) {
		JsonDataGrid data = new JsonDataGrid();
		int size = list.size();
		for(int i=0;i<size;i++){
			T obj = list.get(i);
			try {
				//DTO构造对象,getDeclaredConstructor : 可以返回指定参数的构造函数 
				Constructor dto = DTO_Clazz.getDeclaredConstructor(tobj.getClass());
				//设置属性访问权限
				dto.setAccessible(true); 
				//根据DTO对象生成相应的VIEW对象，传入每个记录所对应的的实体
				Object view = dto.newInstance(obj);
				//将格式化后的VIEW装入JsonDataGrid
				data.addItem(view);
			}catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		logger.debug("JsonDataGrid.toString() : "+data.toJsonString());
		return data;
	}
	
	/**
	 * 获取转化成datagrid控件JSON数据的对象
	 * @param PageView
	 * @param   DTO对象
	 * @throws NoSuchMethodException 
	 */
	public JsonDataGrid getDataGridJson(PageView<T> page,T tobj,Class DTO_Clazz) {
		JsonDataGrid data = new JsonDataGrid();
		List<T> list = page.getRecords();
		int size = list.size();
		for(int i=0;i<size;i++){
			T obj = list.get(i);
			try {
				//DTO构造对象,getDeclaredConstructor : 可以返回指定参数的构造函数 
				Constructor dto = DTO_Clazz.getDeclaredConstructor(tobj.getClass());
				//设置属性访问权限
				dto.setAccessible(true); 
				//根据DTO对象生成相应的VIEW对象，传入每个记录所对应的的实体
				Object view = dto.newInstance(obj);
				
				//判断是否还有上一页，并且for循环是否是第一个对象
				if(i==0&&page.getPageNow()==1){
					ReflectionUtils.setFieldValue(view, "isfirst", "true");
				}
				//判断是否还有下一页，并且for循环是否是最后一个对象
				if((i+1)==size&&page.getPageCount()==page.getPageNow()){
					ReflectionUtils.setFieldValue(view, "islast", "true");
				}
				
				//将格式化后的VIEW装入JsonDataGrid
				data.addItem(view);
			}catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		//设置总记录数
		data.setTotal(page.getRowCount());
		//设置总页数
		data.setPageCount(page.getPageCount());
		//设置当前页码
		data.setPageIndex(page.getPageNow());
		
		logger.debug("JsonDataGrid.toString() : "+data.toJsonString());
		return data;
	}
 
	
	
}
