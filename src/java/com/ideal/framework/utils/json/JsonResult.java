/**
 * eip
 * cn.sh.ideal.core.util.JSONOperateResult.java
 */
package com.ideal.framework.utils.json;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.ideal.framework.utils.string.EmptyUtil;

/**
 * 操作是否成功的JSON对象表示。
 * 
 * @author shenlei@ideal.sh.cn
 */
public class JsonResult {
	
	
	public static String ACCT_STATUS_LOGIN			="login";
	/**
	 * 验证无效
	 * */
	public static String ACCT_STATUS_INVALID		="invalid";
	/**
	 * 验证有效
	 * */
	public static String ACCT_STATUS_VALID			="valid";
	/**
	 * 状态为true
	 * */
	public static String ACCT_STATUS_TRUE			="true";
	/**
	 *状态为false
	 * */
	public static String ACCT_STATUS_FALSE			="false";
	
	/**操作类型**/
	private String type ="KATONG";
	
	/**操作状态**/
	private String stat = "";
	
	/**操作信息**/
	private String msg ="";
	
	/**额外附带信息**/
	private Map data = new HashMap();
	
	/**是否登录**/
	private boolean hasLogined = true;	
	
	/**
	 * @return the hasLogined
	 */
	public boolean isHasLogined() {
		return hasLogined;
	}

	/**
	 * @param hasLogined the hasLogined to set
	 */
	public void setHasLogined(boolean hasLogined) {
		this.hasLogined = hasLogined;
	}

	/**
	 * @return the data
	 */
	public Map getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(Map data) {
		this.data = data;
	}
	
	/**
	 * 获得成功的操作结果,直接以JSON表示
	 * @param message
	 * @return
	 */
	public JSON successAsJson(String message) {
		this.setStat("ok");
		this.setMsg(message);
		return this.toJson();
	}	
	
	/**
	 * 获得失败的操作结果,直接以JSON表示
	 * @param message
	 * @return
	 */
	public JSON failureAsJson(String message) {
		this.setStat("fail");
		this.setMsg(message);
		return this.toJson();
	}	

	
	/**
	 * 操作完成，返回状态信息
	 * @param message
	 * @return
	 */
	public static JsonResult success(String message) {
		JsonResult result = new JsonResult();
		result.setStat("ok");
		result.setMsg(message);
		return result;
	}	
	
	

	/**
	 * 获得成功的操作结果,直接以JSON表示
	 * @param message
	 * @return
	 */
	public static JSON successToJson(String message) {
		JsonResult result = new JsonResult();
		result.setStat("ok");
		result.setMsg(message);
		return result.toJson();
	}
	

	/**
	 * 获得成功的操作结果,直接以JSON表示。并返回额外的数据
	 * @param message
	 * @return
	 */
	public static JSON successToJson(String message, Object obj) {
		JsonResult result = new JsonResult();
		result.setStat("ok");
		result.setMsg(message);
		if(!EmptyUtil.isEmpty(obj)){
			result.data.put("data",obj);
		}
		return result.toJson();
	}
	
	/**
	 * 获得成功的操作结果,直接以JSON表示。并返回额外的数据
	 * @param message
	 * @return
	 */
	public static JSON successToJson(String message, Object key,Object value) {
		JsonResult result = new JsonResult();
		result.setStat("ok");
		result.setMsg(message);
		result.data.put(key,value);
		return result.toJson();
	}
	
	/**
	 * 获得成功的操作结果,直接以JSON表示。并返回额外的数据
	 * @param message
	 * @return
	 */
	public static JSON successToJson(String message, Map data) {
		JsonResult result = new JsonResult();
		result.setStat("ok");
		result.setMsg(message);
		if (data != null) {
			result.data.putAll(data);
		}
		return result.toJson();
	}

	/**
	 * 获得失败的操作结果
	 * 
	 * @param message
	 * @return
	 */
	public static JsonResult failure(String message) {
		JsonResult result = new JsonResult();
		result.setStat("fail");
		result.setMsg(message);
		return result;
	}
		

	/**
	 * 获得失败的操作结果,直接以JSON表示
	 * 
	 * @param message
	 * @return
	 */
	public static JSON failureToJson(String message) {
		JsonResult result = new JsonResult();
		result.setStat("fail");
		result.setMsg(message);
		return result.toJson();
	}

	/**
	 * 获得失败的操作结果,直接以JSON表示。并返回额外的数据
	 * @param message
	 * @return
	 */
	public static JSON failureToJson(String message, Map data) {
		JsonResult result = new JsonResult();
		result.setStat("fail");
		result.setMsg(message);
		result.data.putAll(data);
		return result.toJson();
	}
	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public JSON toJson() {
		return JSONObject.fromObject(this);
	}
	
	public JSON toJson(JsonConfig config){
		return JSONObject.fromObject(this,config);
	}

	public void putData(String name, Object value) {
		this.data.put(name, value);
	}
 
}
