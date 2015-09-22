package com.ideal.framework.dialet;

import javax.xml.bind.PropertyException;

import com.ideal.framework.constants.JDBCConstants;
import com.ideal.framework.utils.string.EmptyUtil;

/**
 * 类似hibernate的Dialect,但只精简出分页部分
 * @author badqiu
 */
public class Dialect {
	
	private static Dialect dialectObj;
	
    public boolean supportsLimit(){
    	return false;
    }

    public boolean supportsLimitOffset() {
    	return supportsLimit();
    }
    
    /**
     * 获得数据库方言对象
     * */
    public static Dialect getDialectInstance(){
    	if(EmptyUtil.isEmpty(dialectObj)){
    		if (EmptyUtil.isEmpty(JDBCConstants.DIALECT_DEFAULT)) {
    			try {
    				throw new PropertyException("dialect property is not found!");
    			} catch (PropertyException e) {
    				e.printStackTrace();
    			}
    		} else{
    			System.out.println("数据库方言  -->Dialect : "+JDBCConstants.DIALECT_DEFAULT);
        		try {
            		dialectObj = (Dialect) Class.forName(JDBCConstants.DIALECT_DEFAULT).getDeclaredConstructor().newInstance();
        		} catch (Exception e) {
        			throw new RuntimeException(JDBCConstants.DIALECT_DEFAULT + ", init fail!\n" + e);
        		}
    		}
    	}
    	return dialectObj;
    }
    
    /**
     * 格式化日期为数据库可以识别的语句
     */
    public String getDateForMatSQLString(String name) {
    	String pattern = "%Y/%m/%d";
    	return "DATE_FORMAT("+name+",'"+pattern+"') ";
    }
    
    /**
     * 将sql变成分页sql语句,直接使用offset,limit的值作为占位符.</br>
     * 源代码为: getLimitString(sql,offset,String.valueOf(offset),limit,String.valueOf(limit))
     */
    public String getLimitString(String sql, int offset, int limit) {
    	return getLimitString(sql,offset,Integer.toString(offset),limit,Integer.toString(limit));
    }
    
    /**
     * 将sql变成分页sql语句,提供将offset及limit使用占位符(placeholder)替换.
     * <pre>
     * 如mysql
     * dialect.getLimitString("select * from user", 12, ":offset",0,":limit") 将返回
     * select * from user limit :offset,:limit
     * </pre>
     * @return 包含占位符的分页sql
     */
    public String getLimitString(String sql, int offset,String offsetPlaceholder, int limit,String limitPlaceholder) {
    	throw new UnsupportedOperationException("paged queries not supported");
    }
    
}
