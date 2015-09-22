package com.ideal.framework.codeGenerate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;

import com.ideal.framework.basecore.baseEntity.BaseEntity;
import com.ideal.framework.codeGenerate.table.ColumnDataConnections;
import com.ideal.framework.codeGenerate.uitls.DBUtils;
import com.ideal.framework.codeGenerate.uitls.DateUtils;
import com.ideal.framework.codeGenerate.uitls.FreeMarkers;
import com.ideal.framework.codeGenerate.uitls.MyBatisGeneratorWithJava;
import com.ideal.framework.codeGenerate.uitls.MyBatisGeneratorXmlStr;
import com.ideal.framework.constants.JDBCConstants;
import com.ideal.framework.utils.io.ApacheFileUtils;
import com.ideal.framework.utils.java.JavaUtils;
import com.ideal.framework.utils.string.PatternUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;

 
/** 
* @ClassName:Generate.java
* @CreateTime 2015-4-22 下午03:26:04
* @author:himo
* @mail:zhangyao0905@gmail.com
* @Description:RICORE代码生成器
*/ 
public class CodeGenerate {
	
	private static Logger logger = LoggerFactory.getLogger(CodeGenerate.class);
	
	// ========== ↓↓↓↓↓↓ 执行前请修改参数，谨慎执行。↓↓↓↓↓↓ ====================
	// 主要提供基本功能模块代码生成。
	// 目录生成结构：{packageName}/{moduleName}/{dao,entity,service,web}/{className}()/{ClassName}
	// packageName 包名，这里如果更改包名，请在applicationContext.xml和srping-mvc.xml中配置base-package、packagesToScan属性，来指定多个（共4处需要修改）。
	private static Boolean isEnable = true;//是否启用生成工具 ,为false不执行代码生成	
	// ========== ↑↑↑↑↑↑ 执行前请修改参数，谨慎执行。↑↑↑↑↑↑ ====================
	
	
	public static void main(String[] args) throws Exception {
							                                                                               
		CodeGenerate.doGenerate("com.ideal",  //模块名packageName
								"javamail",       //包名moduleName
								"ic_user", //实体对应数据库表名tableName
								"mailContact",    //实体名className
								"邮件联系人表",    //模块描述description
								"zhangyao",   //作者classAuthor
								"zhangyao0905@gmail.com",//邮箱mail
								true);//是否生成前台JSP、JS文件isGenerateJsp
	}
	/**
	 * 执行模板生成
	 * @param packageName  包名
	 * @param moduleName  模块名
	 * @param tableName  数据库表名
	 * @param className  实体类名
	 * @param classAuthor  类作者
	 * @param mail  类作者邮箱
	 * @param isGenerateJsp  是否生成前台JSP、JS文件
	 * @code 目录生成结构：{packageName}/{moduleName}/{className}/{dao,entity,service,web}/{ClassName}
	 * */
	public static void doGenerate(String packageName,String moduleName,String tableName,String className,String description,String classAuthor,String mail,boolean isGenerateJsp){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("packageName", StringUtils.lowerCase(packageName)); //包名
		model.put("moduleName", StringUtils.lowerCase(moduleName));  //模块名
		model.put("ClassName", StringUtils.capitalize(className)); //实体类名
		model.put("className", StringUtils.lowerCase(className));//小写类名
		model.put("author", StringUtils.isNotBlank(classAuthor)?classAuthor:"Generate Tools"); //类作者
		model.put("mail", mail); //类作者邮箱
		model.put("tableName",StringUtils.upperCase(tableName)); //数据库表名
		model.put("CreateTime", DateUtils.getDateTime()); //代码生成时间
		model.put("Description", description); //代码生成时间
		try {
			bulidTemplate(model,isGenerateJsp);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("代码生成失败！");
		}
	}
	
	/**
	 * 构建模板
	 * @param isGenerateJsp  是否生成前台JSP、JS文件
	 * */
	private static void bulidTemplate(Map<String, Object> model,boolean isGenerateJsp)throws Exception{
		if (!isEnable){
			logger.error("请启用代码生成工具，设置参数：isEnable = true");
			return;
		}
		
		if (StringUtils.isBlank(String.valueOf(model.get("packageName"))) || StringUtils.isBlank(String.valueOf(model.get("moduleName"))) 
			||StringUtils.isBlank(String.valueOf(model.get("ClassName"))) || StringUtils.isBlank(String.valueOf(model.get("author"))) 
			||StringUtils.isBlank(String.valueOf(model.get("mail"))) || StringUtils.isBlank(String.valueOf(model.get("tableName"))) ){
			logger.error("参数设置错误：包名、模块名、类名、作者、邮箱、数据库表明不能为空。");
			return;
		}
		
		// 获取文件分隔符
		String separator = File.separator;
		
		// 获取工程路径
		File projectPath = new DefaultResourceLoader().getResource("").getFile();
		while(!new File(projectPath.getPath()+separator+"src"+separator+"java").exists()){
			projectPath = projectPath.getParentFile();
		}
		logger.info("Project Path: {}", projectPath);
		
		// 模板文件路径   
		String tplPath = StringUtils.replace(JavaUtils.getClassPath(CodeGenerate.class)+"/template", "/", separator);
		logger.info("Template Path: {}", tplPath);
		
		// Java文件路径   
		String javaPath = StringUtils.replaceEach(projectPath+"/src/java/"+String.valueOf(model.get("packageName"))+"/"+StringUtils.lowerCase(String.valueOf(model.get("moduleName"))), 
				new String[]{"/", "."}, new String[]{separator, separator});
		logger.info("Java Path: {}", javaPath);
		
		// 代码模板配置
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File(tplPath));

		// 定义模板变量
		model.put("urlPrefix", model.get("moduleName")+"/"+model.get("className"));
		model.put("viewPrefix",model.get("moduleName")+"/"+model.get("className"));
		model.put("permissionPrefix", model.get("moduleName")+":"+model.get("className"));

		// 生成 Entity
		DBUtils db = new DBUtils();
		ColumnDataConnections cdc = new ColumnDataConnections();
		if(PatternUtils.wildMatchForMat("*Oracle*", JDBCConstants.DIALECT_DEFAULT)){
			cdc  = db.getOracleColumnDatas(String.valueOf(model.get("tableName")),BaseEntity.class);
		}else if(PatternUtils.wildMatchForMat("*mysql*", JDBCConstants.DIALECT_DEFAULT)){
			cdc  = db.getMySQLColumnDatas(String.valueOf(model.get("tableName")),JDBCConstants.MYSQL_TABLESCHEMA,BaseEntity.class);
		}
		model.put("columnDataConnections", cdc);
		Template template = cfg.getTemplate("entity.ftl");
		String content = FreeMarkers.renderTemplate(template, model);
		String filePath = javaPath+separator+StringUtils.lowerCase((String)model.get("className"))+separator+"entity"+separator+model.get("ClassName")+".java";
		writeFile(content, filePath);
		logger.info("Entity: {}", filePath);
		
		// 生成 EntityDTO
		 template = cfg.getTemplate("entityDTO.ftl");
		 content = FreeMarkers.renderTemplate(template, model);
		 filePath = javaPath+separator+StringUtils.lowerCase((String)model.get("className"))+separator+"entity"+separator+model.get("ClassName")+"DTO.java";
		 writeFile(content, filePath);
		 logger.info("EntityDTO: {}", filePath);
		
		
		// 生成 Dao
		template = cfg.getTemplate("dao.ftl");
		content = FreeMarkers.renderTemplate(template, model);
		filePath = javaPath+separator+StringUtils.lowerCase((String)model.get("className"))+separator+"dao"+separator+model.get("ClassName")+"Mapper.java";
		writeFile(content, filePath);
		logger.info("Dao: {}", filePath);
		
		// 生成 DaoMapping
		template = cfg.getTemplate("mapping.ftl");
		content = FreeMarkers.renderTemplate(template, model);
		filePath = javaPath+separator+StringUtils.lowerCase((String)model.get("className"))+separator+"mapping"+separator+model.get("ClassName")+"Mapper.xml";
		writeFile(content, filePath);
		logger.info("Dao: {}", filePath);
		
		//使用MyBatisGenerator生成mappingXML文件
		MyBatisGeneratorWithJava.generator(MyBatisGeneratorXmlStr.getXml(javaPath+separator+StringUtils.lowerCase((String)model.get("className"))+separator, String.valueOf(model.get("className")), String.valueOf(model.get("tableName"))));
		
		// 生成IService
		template = cfg.getTemplate("IService.ftl");
		content = FreeMarkers.renderTemplate(template, model);
		filePath = javaPath+separator+StringUtils.lowerCase((String)model.get("className"))+separator+"service"+separator+"I"+model.get("ClassName")+"Service.java";
		writeFile(content, filePath);
		logger.info("Service: {}", filePath);
		
		// 生成 Service
		template = cfg.getTemplate("service.ftl");
		content = FreeMarkers.renderTemplate(template, model);
		filePath = javaPath+separator+StringUtils.lowerCase((String)model.get("className"))+separator+"service"+separator+"impl"+separator+model.get("ClassName")+"ServiceImpl.java";
		writeFile(content, filePath);
		logger.info("Service: {}", filePath);
		
		// 生成 controller
		template = cfg.getTemplate("controller.ftl");
		content = FreeMarkers.renderTemplate(template, model);
		filePath = javaPath+separator+StringUtils.lowerCase((String)model.get("className"))+separator+"controller"+separator+model.get("ClassName")+"Controller.java";
		writeFile(content, filePath);
		logger.info("Action: {}", filePath);
		
		//是否生成前台JSP和JS文件
		if(isGenerateJsp){
			// JSP文件路径
			String jspPath = StringUtils.replace(projectPath+"/WebRoot/WEB-INF/content/"+String.valueOf(model.get("moduleName")).toLowerCase()+"/web", "/", separator);
			logger.info("jspPath Path: {}", jspPath);
			// JS文件路径
			String jsPath = StringUtils.replace(projectPath+"/WebRoot/static/js/app", "/", separator);
			logger.info("jsPath Path: {}", jsPath);
			// 生成entity.jsp
			template = cfg.getTemplate("jsp.ftl");
			content = FreeMarkers.renderTemplate(template, model);
			filePath = jspPath+separator+String.valueOf(model.get("ClassName")).toLowerCase()+separator+String.valueOf(model.get("ClassName")).toLowerCase()+".jsp";
			writeFile(content, filePath);
			logger.info("entity.jsp: {}", filePath);
			
			// 生成input.jsp
			template = cfg.getTemplate("jspinput.ftl");
			content = FreeMarkers.renderTemplate(template, model);
			filePath = jspPath+separator+String.valueOf(model.get("ClassName")).toLowerCase()+separator+String.valueOf(model.get("ClassName")).toLowerCase()+"-input"+".jsp";
			writeFile(content, filePath);
			logger.info("input.jsp: {}", filePath);
			
			// 生成JS
			template = cfg.getTemplate("js.ftl");
			content = FreeMarkers.renderTemplate(template, model);
			filePath = jsPath+separator+String.valueOf(model.get("moduleName")).toLowerCase()+separator+String.valueOf(model.get("moduleName")).toLowerCase()+"."+String.valueOf(model.get("ClassName")).toLowerCase()+""+".js";
			writeFile(content, filePath);
			logger.info("input.js: {}", filePath);
		}
		
		logger.info("代码生成成功.");
	}
	
	/**
	 * 将内容写入文件
	 * @param content
	 * @param filePath
	 */
	private static void writeFile(String content, String filePath) {
		try {
			if (ApacheFileUtils.createFile(filePath)){
				FileWriter fileWriter = new FileWriter(filePath, true);
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				bufferedWriter.write(content);
				bufferedWriter.close();
				fileWriter.close();
			}else{
				logger.info("生成失败，文件已存在！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
