package com.ideal.framework.codeGenerate.uitls;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.ideal.framework.codeGenerate.table.ColumnData;
import com.ideal.framework.codeGenerate.table.ColumnDataConnections;
import com.ideal.framework.constants.JDBCConstants;
import com.ideal.framework.utils.java.BeanUtil;
import com.ideal.framework.utils.string.PatternUtils;

/** 
 * @ClassName:DBUtils.java
 * @CreateTime 2015-4-23 下午03:47:02
 * @author:himo
 * @mail:zhangyao0905@gmail.com
 * @Description:查询数据库列名等信息
 */
public class DBUtils {
	
	String driveName = JDBCConstants.Oracle_JDBC_DRIVER_CLASS_NAME;
	String url = JDBCConstants.JDBC_URL;
	String username = JDBCConstants.USER_NAME;
	String password = JDBCConstants.PASSWORD;
	
	private Connection getConnection() throws Exception {
		if(PatternUtils.wildMatchForMat("*Oracle*", JDBCConstants.DIALECT_DEFAULT)){
			driveName = JDBCConstants.Oracle_JDBC_DRIVER_CLASS_NAME;
		}else if(PatternUtils.wildMatchForMat("*mysql*", JDBCConstants.DIALECT_DEFAULT)){
			driveName = JDBCConstants.MySql_JDBC_DRIVER_CLASS_NAME;
		}
		Class.forName(driveName);
		return DriverManager.getConnection(url, username, password);
	}
	
	public  ColumnDataConnections getOracleColumnDatas(String table,Class baseClazz){
		String SQLColumns ="select t.column_name,c.DATA_TYPE column_type,c.DATA_LENGTH column_type_length,t.comments column_comment from USER_COL_COMMENTS t,USER_TAB_COLUMNS c  where c.column_name= t.column_name and c.TABLE_NAME='"+StringUtils.upperCase(table)+"' and c.TABLE_NAME=t.TABLE_NAME";
		return getColumns(SQLColumns,baseClazz);
	}
	public  ColumnDataConnections getMySQLColumnDatas(String table,String tableSchema,Class baseClazz){
		String SQLColumns ="select column_name ,data_type column_type,character_maximum_length column_type_length,column_comment from information_schema.columns where table_name = '"+table+"'  and table_schema = '"+tableSchema+"'";
		return getColumns(SQLColumns,baseClazz);
	}
	
	private ColumnDataConnections getColumns(String SQLColumns,Class baseClazz) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ColumnDataConnections cdc = new ColumnDataConnections();
		cdc.setParentColumnDataList(baseClazz);
		try {
			con = getConnection();
			ps = con.prepareStatement(SQLColumns);
			rs = ps.executeQuery();
			while (rs.next()) {
				String name = rs.getString(1);
				String type = rs.getString(2);
				String length = rs.getString(3);
				String comment = rs.getString(4);
				if(!BeanUtil.isFieldInBaseEntity(baseClazz,BeanUtil.underlineToCamelhump(name))){
					ColumnData cd = new ColumnData();
					cd.setColumnName(name);
					cd.setColumnType(type);
					cd.setColumnComment(comment);
					cd.setColumnTypeLength(length);
					
					cd.setJavaFieldFullType(getJavaFieldTypeBySQLType(type));
					cd.setJavaFieldSortType(cd.getJavaFieldFullType());
					cd.setJavaFieldName(name);
					
					cdc.addColumnData(cd);
					cdc.addJavaFieldFullTypeSet(cd.getJavaFieldFullType());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				ps.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return cdc;
	}
	
	
	/**
	 * 根据SQL type 获得 java Field type
	 * */
	private String getJavaFieldTypeBySQLType(String dataType) {
		dataType = dataType.toLowerCase();
		if (dataType.contains("char")||dataType.contains("text"))
			dataType = "java.lang.String";
		else if (dataType.contains("bit"))
			dataType = "java.lang.Boolean";
		else if (dataType.contains("bigint"))
			dataType = "java.lang.Long";
		else if (dataType.contains("int"))
			dataType = "java.lang.Integer";
		else if (dataType.contains("float"))
			dataType = "java.lang.Float";
		else if (dataType.contains("double"))
			dataType = "java.lang.Double";
		else if (dataType.contains("number")) {
			dataType = "java.lang.Integer";
		} else if (dataType.contains("decimal"))
			dataType = "BigDecimal";
		else if (dataType.contains("date"))
			dataType = "java.util.Date";
		else if (dataType.contains("time"))
			dataType = "java.sql.Timestamp";
		else if (dataType.contains("clob"))
			dataType = "java.sql.Clob";
		else {
			dataType = "java.lang.Object";
		}
		return dataType;
	}
	
	
	public static void main(String[] args){
		DBUtils db = new DBUtils();
		
//		System.out.println(db.driveName);
//		ColumnDataConnections cdc  =db.getMySQLColumnDatas("ic_aboutme","idealcore4",BaseEntity.class);
//		for(ColumnData columnData : cdc.getColumnDataList()){
//			System.out.println(columnData.getColumnName()+"  "+columnData.getColumnType()+"  "+columnData.getJavaFieldName()+"  "+columnData.getJavaFieldFullType()+"  "+columnData.getJavaFieldSortType());
//		}
	}

}
