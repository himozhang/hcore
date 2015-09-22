/**  
* @Title: EntityTable.java
* @Package com.ideal.framework.mybatis
* @Description: TODO
* @author himo.zhang ideal_tom@163.com
* @date 2015-9-20 下午6:54:03
*/
package com.ideal.framework.mybatis.vo;

import javax.persistence.Table;

/**
 * @ClassName: EntityTable
 * @Description: TODO
 * @author himo.zhang ideal_tom@163.com
 * @date 2015-9-20 下午6:54:03
 */
/**
 * 实体对应表的配置信息
 */
public  class EntityTable {
   

	private String name;
    private String catalog;
    private String schema;

    public void setTable(Table table){
        this.name = table.name();
        this.catalog = table.catalog();
        this.schema = table.schema();
    }

    public String getName() {
        return name;
    }

    public String getCatalog() {
        return catalog;
    }

    public String getSchema() {
        return schema;
    }

    public String getPrefix() {
        if (catalog != null && catalog.length() > 0) {
            return catalog;
        }
        if (schema != null && schema.length() > 0) {
            return catalog;
        }
        return "";
    }
    
    /**
   	 * @param name the name to set
   	 */
   	public void setName(String name) {
   		this.name = name;
   	}

   	/**
   	 * @param catalog the catalog to set
   	 */
   	public void setCatalog(String catalog) {
   		this.catalog = catalog;
   	}

   	/**
   	 * @param schema the schema to set
   	 */
   	public void setSchema(String schema) {
   		this.schema = schema;
   	}
   	
   	
   	
}
