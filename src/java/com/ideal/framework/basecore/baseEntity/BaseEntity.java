package com.ideal.framework.basecore.baseEntity;

import java.util.Date;

import com.ideal.framework.constants.GlobalConstants;
import com.ideal.framework.utils.date.DateFormatter;
import com.ideal.framework.utils.string.EmptyUtil;



/** 
 * @ClassName:BaseEntity.java
 * @CreateTime 2015-8-24 下午10:26:43
 * @author:himo
 * @mail:zhangyao0905@gmail.com
 * @Description:creatorId、createTime、modifierId、modifyTime、state、Id
 */
@SuppressWarnings("serial")
public class BaseEntity extends IdEntity {

	/**
	 * 对象产生时间
	 */
	private Date createTime;

	/**
	 * 对象变更时间
	 */
	private Date modifyTime;

	/**
	 * 对象的创建者ID
	 */
	private String creatorId;

	/**
	 * 对象的修改者
	 */
	private String modifierId;

	/**
	 * 状态  1：启用，0：停用
	 * */
	private String state ;
	

	/**
	 * @return the 对象产生时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 创建时间格式化为yyyy-MM-dd
	 * @return String
	 * */
	public String getCreateTimeStr() {
		if (EmptyUtil.isEmpty(this.getCreateTime())) {
			return "";
		}
		return DateFormatter.formatDate(this.getCreateTime());
	}
	
	/**
	 * @param createTime the 对象产生时间 to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the 对象变更时间
	 */
	public Date getModifyTime() {
		return modifyTime;
	}
	
	/**
	 * 修改时间格式化为yyyy-MM-dd
	 * @return String
	 * */
	public String getModifyTimeStr() {
		if (EmptyUtil.isEmpty(this.getModifyTime())) {
			return "";
		}
		return DateFormatter.formatDate(this.getModifyTime());
	}

	/**
	 * @param modifyTime the 对象变更时间 to set
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * @return the 对象的修改者

	 */
	public String getModifierId() {
		return modifierId;
	}

	/**
	 * @param modifier the  对象的修改者 to set
	 */
	public void setModifierId(String modifierId) {
		this.modifierId = modifierId;
	}

	/**
	 * @return the 对象状态
	 */
	public String getState() {
		return state;
	}
	
	/**
	 * @return the 对象状态 Str
	 */
	public String getStateStr() {
		return this.state;
	}

	/**
	 * @param state the 对象状态 to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	
	/**
	 * @return the creatorId
	 */
	public String getCreatorId() {
		return creatorId;
	}

	/**
	 * @param creatorId the creatorId to set
	 */
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}
	
	/**
	 * 保存数据时,初始化基础数据<br>
	 * 信息ID\操作用户ID\插入时间\修改人ID\修改时间S
	 * */
	public void initBaseInfo(){
//		String userid = SpringSecurityUtils.getCurrentUserId();
//		if(EmptyUtil.isEmpty(userid)){
//			userid = "";
//		}
		//判断是否存在ID,如果不存在,则为新增信息
		if(EmptyUtil.isEmpty(this.getId())){
			this.initID();
			this.setCreateTime(new Date());
//			this.setCreatorId(userid);
		}
		
		if(EmptyUtil.isEmpty(this.getState())){
			this.setState(GlobalConstants.YES);
		}
		
//		this.setModifierId(userid);
		this.setModifyTime(new Date());
	}
}
