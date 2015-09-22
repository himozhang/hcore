/**  
* @Title: EntityColumn.java
* @Package com.ideal.framework.mybatis
* @Description: TODO
* @author himo.zhang ideal_tom@163.com
* @date 2015-9-20 下午6:55:38
*/
package com.ideal.framework.mybatis.vo;

/**
 * @ClassName: EntityColumn
 * @Description: 实体字段对应数据库列的信息
 * @author himo.zhang ideal_tom@163.com
 * @date 2015-9-20 下午6:55:38
 */
public  class EntityColumn {
	
	//实体对应的属性名
    private String property;
    //实体对应的数据库列名
    private String column;
    //列所对应的值，可以是实际值，也可以是值所对应的参数名,例如  #{username}
    private Object value;
    //实体类名
  	private String entityClassName;
  	//实体所对应的数据库表名
  	private String tableName;
    //实体对应属性的java数据类型
	private Class<?> javaType;
	

	 /**
	  * 列所对应的值，可以是实际值，也可以是值所对应的参数名,例如  #{username}
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}
		
	/**
	 * 实体对应的属性名
	 * */
    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    /**
     * 实体对应的数据库列名
     * */
    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    /**
     * 实体对应属性的java数据类型
     * */
    public Class<?> getJavaType() {
        return javaType;
    }

    public void setJavaType(Class<?> javaType) {
        this.javaType = javaType;
    }

	/**
	 * @return the entityClassName
	 */
	public String getEntityClassName() {
		return entityClassName;
	}

	/**
	 * @param entityClassName the entityClassName to set
	 */
	public void setEntityClassName(String entityClassName) {
		this.entityClassName = entityClassName;
	}

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}
