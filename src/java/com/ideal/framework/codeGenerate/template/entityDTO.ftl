package ${packageName}.${moduleName}.${className?lower_case}.entity;

import com.ricore.framework.domain.BaseDTO;
import com.ricore.framework.utils.date.DateFormatter;
 
/** 
* @ClassName:${ClassName}DTO.java
* @CreateTime ${CreateTime}
* @author:${author}
* @mail:${mail}
* @Description:${Description}-实体数据传输类 - 由代码生成器生成
*/
public class ${ClassName}DTO extends BaseDTO<${ClassName}> {

	/**
	 * @param ${ClassName} entity
	 */
	public ${ClassName}DTO(${ClassName} entity) {
		super(entity);
	}

	<#list columnDataConnections.columnDataList as columnData> 
	<#if columnData.columnComment??>
	//${columnData.columnComment}
	</#if>
	public <#if columnData.javaFieldSortType=='Date'>String</#if><#if columnData.javaFieldSortType!='Date'>${columnData.javaFieldSortType}</#if>  get${columnData.javaFieldName?cap_first}() {
		<#if columnData.javaFieldSortType=='Date'>
		return DateFormatter.formatSimpleShortDate(this.entity.get${columnData.javaFieldName?cap_first}());
		</#if>
		<#if columnData.javaFieldSortType!='Date'>
		return this.entity.get${columnData.javaFieldName?cap_first}();
		</#if>
	}
	
	</#list> 

}
