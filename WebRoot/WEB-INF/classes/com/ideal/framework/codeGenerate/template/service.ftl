package ${packageName}.${moduleName}.${className?lower_case}.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ${packageName}.${moduleName}.${className?lower_case}.dao.${ClassName}Mapper;
import ${packageName}.${moduleName}.${className?lower_case}.entity.${ClassName};
import ${packageName}.${moduleName}.${className?lower_case}.service.I${ClassName}Service;
import com.ricore.framework.mybatis.mapper.BaseMapper;
import com.ricore.framework.service.impl.BaseServiceImpl;


/** 
* @ClassName:${ClassName}ServiceImpl.java
* @CreateTime ${CreateTime}
* @author:${author}
* @mail:${mail}
* @Description:${Description}-${ClassName}Service接口实现类 - 由代码生成器生成
*/
@Transactional
@Service("${ClassName?uncap_first}Service")
public class ${ClassName}ServiceImpl extends BaseServiceImpl<${ClassName}> implements I${ClassName}Service{
	
	@Autowired
	private ${ClassName}Mapper ${ClassName?uncap_first}Mapper;

	/**
	 * getMapper() -> BaseServiceImpl中使用
	 */
	@Override
	public BaseMapper<${ClassName}> getMapper() {
		return ${ClassName?uncap_first}Mapper;
	}
}
