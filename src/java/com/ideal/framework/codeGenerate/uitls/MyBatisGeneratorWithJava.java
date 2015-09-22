/**
* @包名   framework
* @文件名 MyBatisGenerator.java
* @作者   zhangyao
* @创建日期 2014-10-14
*/ 
package com.ideal.framework.codeGenerate.uitls;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.config.xml.ConfigurationParserFromString;
import org.mybatis.generator.internal.DefaultShellCallback;

import com.ideal.framework.utils.java.JavaUtils;

 

/**
 * @author zhangyao
 * 数据库自动生成MYBATIS对应文件
 */
public class MyBatisGeneratorWithJava {

	/**
	 * 根据传入的XML文件生成Mybaties文件
	 * @param String xmlStr
	 * */
    public static void generator(String xmlStr) throws Exception {
            // 信息缓存
            List<String> warnings = new ArrayList<String>();
            // 覆盖已有的重名文件
            boolean overwrite = true;
            // 1.创建 配置解析器
            ConfigurationParserFromString parser = new ConfigurationParserFromString();
            // 2.获取 配置信息
            Configuration config = parser.parseConfiguration(xmlStr);
            // 3.创建 默认命令解释调回器
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            // 4.创建 mybatis的生成器
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            // 5.执行，关闭生成器
            myBatisGenerator.generate(null);
            System.out.println("文件生成成功！");
    }
    
    /**
	 * 根据传入的XML文件生成Mybaties文件
	 * @param String xmlStr
	 * */
    public static void generator() throws Exception {
            // 信息缓存
            List<String> warnings = new ArrayList<String>();
            // 覆盖已有的重名文件
            boolean overwrite = true;
            // 准备 配置文件
            String filepath = JavaUtils.getClassPath(MyBatisGeneratorWithJava.class);
            File configFile = new File(filepath+"/MyBatisGenerator.xml");
            // 1.创建 配置解析器
            ConfigurationParser parser = new ConfigurationParser(warnings);
            // 2.获取 配置信息
            Configuration config = parser.parseConfiguration(configFile);
            // 3.创建 默认命令解释调回器
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            // 4.创建 mybatis的生成器
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            // 5.执行，关闭生成器
            myBatisGenerator.generate(null);
            System.out.println("文件生成成功！");
    }
    
     
    
    public static void main(String[] args){
    	MyBatisGeneratorWithJava mj = new MyBatisGeneratorWithJava();
    	try {
//    		mj.generator();
//    		MyBatisGeneratorWithJava.generator(MyBatisGeneratorXmlStr.textXml());
    		MyBatisGeneratorWithJava.generator(MyBatisGeneratorXmlStr.getXml("C:\\workspace\\ricore\\src\\java\\com\\ricore\\project\\", "test2015", "ic_aboutme"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
