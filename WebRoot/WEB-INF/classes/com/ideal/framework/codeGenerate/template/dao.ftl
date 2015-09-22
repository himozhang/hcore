package ${packageName}.${moduleName}.${className?lower_case}.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ${packageName}.${moduleName}.${className?lower_case}.entity.${ClassName};
import com.ricore.framework.mybatis.mapper.BaseMapper;

/** 
* @ClassName:${ClassName}Mapper.java
* @CreateTime ${CreateTime}
* @author:${author}
* @mail:${mail}
* @Description:${Description}-Dao执行对象 - 由代码生成器生成
*/ 
public interface ${ClassName}Mapper extends BaseMapper<${ClassName}>{

	/**
	 * 根据ID获得下级对象集合
	 * */
	public List<${ClassName}> getChild${ClassName}s(@Param("id")String id);
	/**
	 * 根据Pid获得上级对象对象
	 * */
	public ${ClassName} getParent${ClassName}(@Param("pid")String pid);
	
}