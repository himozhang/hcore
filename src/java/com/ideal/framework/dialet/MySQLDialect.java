package com.ideal.framework.dialet;


/**
 * @author badqiu
 */
public class MySQLDialect extends Dialect{

	public boolean supportsLimitOffset(){
		return true;
	}
	
    public boolean supportsLimit() {   
        return true;   
    }  
    
    /**
     * 格式化日期为数据库可以识别的语句
     * @param pattern 默认为 %Y%m%d
     */
    public String getDateForMatSQLString(String name) {
    	String pattern = "%Y/%m/%d";
    	return "DATE_FORMAT("+name+",'"+pattern+"') ";
    }
    
	public String getLimitString(String sql, int offset,String offsetPlaceholder, int limit, String limitPlaceholder) {
        if (offset > 0) {   
        	return sql + " limit "+offsetPlaceholder+","+limitPlaceholder; 
        } else {   
            return sql + " limit "+limitPlaceholder;
        }  
	}   
  
}
