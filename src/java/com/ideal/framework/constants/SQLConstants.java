package com.ideal.framework.constants;


/**
 * 系统中SQL查询语句常量资源文件的关键字定义类
 * @author zhangyao0905@gmail.com
 * @date 2015-02-12
 */
public class SQLConstants {
	
	
	/**
	 * SQL 参数 MAP对象中的实体 KEY
	 * @return 字符串 “entity”
	 * */
	public static String ENTITY = "entity";
	
	/**
	 * Update语句中使用的Set关键字
	 * @return 字符串 “set”
	 * */
	public static String SET = "set";
	/**
	 * SQL语句中使用的Where关键字
	 * @return 字符串 “where”
	 * */
	public static String WHERE = "where";
	
	/**
	 * SQL语句中使用的ID关键字
	 * @return 字符串 “id”
	 * */
	public static String ID = "id";
	
	/**
	 * MAP对象中执行SQL方法所对应的key
	 * @value 字符串 "excuteSql"
	 * */
	public static String EXCUTE_SQL = "excute_Sql";
	/**
	 * SQL 参数 MAP对象中的where对应的key
	 * @value WHERE = "where_Key"
	 * */
	public static String WHERE_KEY = "where_Key";
	/**
	 * SQL 参数 MAP对象中的orderby对应的key
	 * @value ORDERBY = "order_By_Key"
	 * */
	public static String ORDER_BY_KEY = "order_By_Key";
	
	/**
	 * SQL 参数 MAP对象中分页使用的 KEY
	 * @return 字符串 “pageView”
	 * */
	public static String PAGE_VIEW = "pageView";
	
	/**
	 * EQ(=)
	 * */
	public static String EQ = " = ";
	/**
	 * LIKE(模糊全匹配)
	 * */
	public static String LIKE = " like ";
	/**
	 * NOT_LIKE(模糊全匹配)
	 * */
	public static String NOT_LIKE = " not like ";
	/**
	 * LT(<)
	 * */
	public static String LT = " < ";
	/**
	 * GT(>)
	 * */
	public static String GT = " > ";
	/**
	 *  LE(<=)
	 * */
	public static String LE = " <= ";
	/**
	 * GE(>=)
	 * */
	public static String GE = " >= ";
	/**
	 * BETWEEN(BETWEEN)
	 * */
	public static String BETWEEN = " BETWEEN ";
	/**
	 * NOT_BETWEEN(BETWEEN)
	 * */
	public static String NOT_BETWEEN = " NOT BETWEEN ";
	/**
	 * AND(AND)
	 * */
	public static String AND = " AND ";
	/**
	 * ISNULL( is null)
	 * */
	public static String ISNULL = " is null ";
	/**
	 * ISNOTNULL( is not null)
	 * */
	public static String ISNOTNULL = " is not null ";
	/**
	 * NE(<>)
	 * */
	public static String NE = " <> ";
	/**
	 * OR(OR)
	 * */
	public static String OR = " OR ";
	/**
	 * IN(IN)
	 * */
	public static String IN = " IN ";
	/**
	 * NOT_IN(IN)
	 * */
	public static String NOT_IN = " NOT IN ";
	
	
	/**
	 * 降序排列
	 * */
	public static String DESC = " desc ";
	/**
	 * 升序排列
	 * */
	public static String ASC = " asc ";
	
	/**
	 *  create_time asc排序
	 * */
	public static String OrderBy_CreateTime_ASC = " create_time asc ";
	/**
	 * create_time desc排序
	 * */
	public static String OrderBy_CreateTime_DESC = " create_time desc ";
	/**
	 * modify_time asc排序
	 * */
	public static String OrderBy_ModifyTime_ASC = " modify_time asc ";
	/**
	 * modify_time desc 排序
	 * */
	public static String OrderBy_ModifyTime_DESC = " modify_time desc ";
	
	/**
	 * sort asc,modify_time desc 排序
	 * */
	public static String OrderBy_SROT_ASC_AND_ModifyTime_DESC = " sort asc,modify_time desc ";
	
	/**
	 * sort asc排序
	 * */
	public static String OrderBy_Sort_ASC = " sort asc ";
	/**
	 * sort desc 排序
	 * */
	public static String OrderBy_Sort_DESC = " sort desc ";
	 
}
