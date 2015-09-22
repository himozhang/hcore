package com.ideal.framework.constants;


 
/** 
* @ClassName:JDBCConstants.java
* @CreateTime 2015-4-24 上午10:02:07
* @author:himo
* @mail:zhangyao0905@gmail.com
* @Description:JDBC数据库连接资源属性读取类
*/ 
public class JDBCConstants {
	
	/** Oracle JDBC驱动名 */
	public static String Oracle_JDBC_DRIVER_CLASS_NAME = "oracle.jdbc.driver.OracleDriver";
	/** Mysql JDBC驱动名 */
	public static String MySql_JDBC_DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
	
	
	/** 实例化资源文件绑定对象，并传递需要绑定的资源文件名称*/
	private static BaseConstants baseConstants = new BaseConstants("db");
	 
	
	/** 数据库 ORacle*/
	public static String DB_ORACLE = baseConstants.getConfig("db.oracle");
	/** 数据库 Mysql*/
	public static String DB_MYSQL = baseConstants.getConfig("db.mysql");
	
	
	/** 数据库连接地址*/
	public static String JDBC_URL = baseConstants.getConfig("db.oracle.url");
	/** 数据库连接用户名*/
	public static String USER_NAME = baseConstants.getConfig("db.oracle.username");
	/** 数据库连接密码*/
	public static String PASSWORD = baseConstants.getConfig("db.oracle.password");
	/** 数据库方言配置 默认方言 */
	public static String DIALECT_DEFAULT = baseConstants.getConfig("dialect_default");
	/** 数据库方言配置 oracle */
	public static String DIALECT_ORACLE = baseConstants.getConfig("dialect_oracle");
	/** 数据库方言配置 mysql */
	public static String DIALECT_MYSQL = baseConstants.getConfig("dialect_mysql");
	/** MYSQL数据库  库名 */
	public static String MYSQL_TABLESCHEMA = baseConstants.getConfig("mysql_tableschema");
	
	
	/**
	 * 获得资源文件中的配置项
	 * */
	public static String getConfig(String key) {
		return baseConstants.getConfig(key);
	}
	 
	
	public static void main(String[] args){
		System.out.println(DB_ORACLE);
	}
	 
}
