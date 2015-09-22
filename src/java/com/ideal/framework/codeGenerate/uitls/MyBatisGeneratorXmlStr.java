package com.ideal.framework.codeGenerate.uitls;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;

import com.ideal.framework.constants.JDBCConstants;
import com.ideal.framework.utils.string.PatternUtils;

/**
 * @ClassName:MyBatisGeneratorXmlStr.java
 * @CreateTime 2015-4-22 下午10:20:34
 * @author:himo
 * @mail:zhangyao0905@gmail.com
 * @Description:Mybaties生成器所需的XML文件
 */
public class MyBatisGeneratorXmlStr {

	private static Logger logger = LoggerFactory.getLogger(MyBatisGeneratorXmlStr.class);
	
	public static String textXml(){
		try {
			return getXml("com.ricore", "aboutMe", "IC_ABOUTME");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 获得XML文件
	 * @param packageName
	 * @param entityName
	 * @param packageName
	 * */
	public static String getXml(String packageName,String entityName,String tableName)  throws Exception{
		
		logger.info("packageName: "+packageName+"  entityName : "+entityName+"  tableName : "+tableName);
		
		// 获取文件分隔符
		String separator = File.separator;
		// 获取工程路径
		File projectPath = new DefaultResourceLoader().getResource("").getFile();
		while(!new File(projectPath.getPath()+separator+"src"+separator+"java").exists()){
			projectPath = projectPath.getParentFile();
		}
		logger.info("Project Path: {}", projectPath);
		
//		// Java文件路径   
//		String javaPath = StringUtils.replaceEach(projectPath+"/src/java/", 
//				new String[]{"/", "."}, new String[]{separator, separator});
////		javaPath = "C:\\Users\\himo\\Desktop\\mybatis-generator-core-1.3.2\\src";
//		logger.info("Java Path: {}", javaPath);
		
		// Lib包路径 
		String libPath = StringUtils.replace(projectPath+"/WebRoot/WEB-INF/lib", "/", separator);
		logger.info("libPath Path: {}", libPath);
		
		String dirveJar = "ojdbc14.jar";
		String dirveName = JDBCConstants.Oracle_JDBC_DRIVER_CLASS_NAME;
		if(PatternUtils.wildMatchForMat("*Oracle*", JDBCConstants.DIALECT_DEFAULT)){
			dirveJar = "ojdbc14.jar";
			dirveName = JDBCConstants.Oracle_JDBC_DRIVER_CLASS_NAME;
		}else if(PatternUtils.wildMatchForMat("*mysql*", JDBCConstants.DIALECT_DEFAULT)){
			dirveJar = "mysql-connector-java-5.1.21.jar";
			dirveName = JDBCConstants.MySql_JDBC_DRIVER_CLASS_NAME;
		}
		
		String jdbcJar = libPath+"\\"+dirveJar;
		
		
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<!DOCTYPE generatorConfiguration");
		sb.append("  PUBLIC \"-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN\"");
		sb.append("  \"http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd\">");
		sb.append("<generatorConfiguration>");
		sb.append("  <!-- classPathEntry:数据库的JDBC驱动的jar包地址-->");
		sb.append("<classPathEntry location=\""+jdbcJar+"\" />");
		sb.append("<context id=\"DB2Tables\" targetRuntime=\"MyBatis3\">");
		sb.append("  <commentGenerator>");
		sb.append("    <!-- 是否去除自动生成的注释 true：是 ： false:否 -->");
		sb.append("    <property name=\"suppressAllComments\" value=\"true\" />");
		sb.append("    <!--数据库连接的信息：驱动类、连接地址、用户名、密码 -->");
		sb.append("	 </commentGenerator>");
		sb.append("  <jdbcConnection driverClass=\""+dirveName+"\"");
		sb.append("     connectionURL=\""+JDBCConstants.JDBC_URL+"\" userId=\""+JDBCConstants.USER_NAME+"\" password=\""+JDBCConstants.PASSWORD+"\">");
		sb.append("  </jdbcConnection>");
		sb.append(" <!--  默认false，把JDBC DECIMAL 和 NUMERIC 类型解析为 Integer true，把JDBC DECIMAL 和 NUMERIC 类型解析为java.math.BigDecimal--> "); 
		sb.append("  <javaTypeResolver >"); 
		sb.append("     <property name=\"forceBigDecimals\" value=\"false\" />"); 
		sb.append("  </javaTypeResolver>"); 
		sb.append("  <!-- targetProject:自动生成代码的位置 -->"); 
		sb.append("  <javaModelGenerator targetPackage=\"entity\" targetProject=\""+packageName+"\">"); 
		sb.append("      <!-- enableSubPackages:是否让schema作为包的后缀 -->     "); 
		sb.append("      <property name=\"enableSubPackages\" value=\"true\" />"); 
		sb.append("  	<!-- 从数据库返回的值被清理前后的空格  --> "); 
		sb.append("      <property name=\"trimStrings\" value=\"true\" />"); 
		sb.append("      <property name=\"rootClass\" value=\"com.ricore.framework.domain.BaseEntity\"/>"); 
		sb.append("  </javaModelGenerator>"); 
		sb.append("  <sqlMapGenerator targetPackage=\"mapping\"   targetProject=\""+packageName+"\">"); 
		sb.append("       <property name=\"enableSubPackages\" value=\"false\" />"); 
		sb.append("  </sqlMapGenerator>"); 
//		sb.append("  <javaClientGenerator type=\"XMLMAPPER\" targetPackage=\""+packageName+"."+StringUtils.lowerCase(entityName)+".dao\"  targetProject=\""+javaPath+"\">"); 
//		sb.append("    <property name=\"enableSubPackages\" value=\"true\" />"); 
//		sb.append("    <property name=\"rootInterface\" value=\"com.ricore.framework.mybatis.mapper.BaseMapper\" />"); 
//		sb.append("  </javaClientGenerator>"); 
		sb.append("  <!--tableName:用于自动生成的数据库表；domainObjectName:对应于数据库表的javaBean类名-->"); 
		sb.append("		<table tableName=\""+tableName+"\" domainObjectName=\""+entityName+"-del222\" enableSelectByPrimaryKey=\"true\" " +
						"enableSelectByExample=\"false\" enableUpdateByPrimaryKey=\"false\" enableDeleteByPrimaryKey=\"false\" " +
						"enableDeleteByExample=\"false\" enableCountByExample=\"false\" enableUpdateByExample=\"false\" " +
						"selectByPrimaryKeyQueryId=\"false\" selectByExampleQueryId=\"false\" enableInsert=\"false\"/>"); 
		sb.append("</context>"); 
		sb.append("</generatorConfiguration>"); 
		logger.debug(sb.toString());
		return sb.toString();
	}
	
	public static void main(String[] args) throws Exception{
		System.out.println(textXml());;
//		// 获取文件分隔符
//		String separator = File.separator;
//		// 获取工程路径
//		File projectPath = new DefaultResourceLoader().getResource("").getFile();
//		while(!new File(projectPath.getPath()+separator+"src"+separator+"java").exists()){
//			projectPath = projectPath.getParentFile();
//		}
//		logger.info("Project Path: {}", projectPath);
//	 
//		// Java文件路径   
//		String javaPath = StringUtils.replaceEach(projectPath+"/src/java/", 
//				new String[]{"/", "."}, new String[]{separator, separator});
//		logger.info("Java Path: {}", javaPath);
//		MyBatisGeneratorWithJava.generator(MyBatisGeneratorXmlStr.getXml("com.ricore.project", "Test2015", "ic_aboutme"));
	}
	
}
