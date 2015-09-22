package com.ideal.framework.basecore.baseEntity;

import javax.persistence.MappedSuperclass;

import com.ideal.framework.utils.string.EmptyUtil;
import com.ideal.framework.utils.string.StringUUID;


/**
 * 统一定义id的entity基类.
 * 基类统一定义id的属性名称、数据类型、列名映射及生成策略. 
 * 子类可重载getId()函数重定义id的列名映射和生成策略.
 * @return String Id
 * @author himo.zhang
 */
// JPA 基类的标识
@MappedSuperclass
public abstract class IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 对象唯一标志
	 */
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * @Description: 以UUID的方式生成主键
	 * @return String : 主键值
	 */
	public static synchronized String generaterID() {
		return StringUUID.getUUID();
	}
	
	/**
	 * @Description: 以UUID的方式生成主键
	 * @return String : 主键值
	 */
	public  String generaterPKey() {
		return StringUUID.getUUID();
	}
	
	public void initID(){
		//判断是否存在ID,如果不存在,则为新增信息
		if(EmptyUtil.isEmpty(this.getId())){
			this.setId(BaseEntity.generaterID());
		}
	}
	
	
	public static void main(String[] args){
		try {
			System.out.println("DomainObject.generaterPrimaryKey() : "+IdEntity.generaterID());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
