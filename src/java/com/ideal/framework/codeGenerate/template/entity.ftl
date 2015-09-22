package ${packageName}.${moduleName}.${className?lower_case}.entity;

import java.util.List;

import javax.persistence.Table;
import javax.persistence.Transient;

import com.ricore.framework.domain.BaseEntity;

<#list columnDataConnections.javaFieldFullTypeSet as javaFieldFullType>  
 import  ${javaFieldFullType};
</#list> 

/** 
* @ClassName:${ClassName}.java
* @CreateTime ${CreateTime}
* @author:${author}
* @mail:${mail}
* @Description:${Description}-实体对象 - 由代码生成器生成
*/ 
@SuppressWarnings("serial")
@Table(name = "${tableName}")
public class ${ClassName} extends BaseEntity{
	 
    <#list columnDataConnections.columnDataList as columnData>  
	<#if columnData.columnComment??>
	//${columnData.columnComment}
	</#if>
	private ${columnData.javaFieldSortType} ${columnData.javaFieldName};
	
	</#list> 
	
    //上级ID
    private String pid;
    //上级对象
    @Transient
    private ${ClassName} parent${ClassName};
    //下级对象集合
    @Transient
    private List<${ClassName}> child${ClassName}s;   
    
	
    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

	/**
	 * @return the parent${ClassName}
	 */
	public ${ClassName} getParent${ClassName}() {
		return parent${ClassName};
	}

	/**
	 * @param parent${ClassName} the parent${ClassName} to set
	 */
	public void setParent${ClassName}(${ClassName} parent${ClassName}) {
		this.parent${ClassName} = parent${ClassName};
	}

	/**
	 * @return the child${ClassName}s
	 */
	public List<${ClassName}> getChild${ClassName}s() {
		return child${ClassName}s;
	}

	/**
	 * @param child${ClassName}s the child${ClassName}s to set
	 */
	public void setChild${ClassName}s(List<${ClassName}> child${ClassName}s) {
		this.child${ClassName}s = child${ClassName}s;
	}
	
	
	<#list columnDataConnections.columnDataList as columnData> 
	/**
	 *<#if columnData.columnComment??> 获取${columnData.columnComment}</#if>
	 * @return  ${columnData.javaFieldSortType}
	 * */
	public ${columnData.javaFieldSortType} get${columnData.javaFieldName?cap_first}() {
		return ${columnData.javaFieldName};
	}
	
	/**
	 *<#if columnData.columnComment??> 设置${columnData.columnComment}</#if>
	 * @param  ${columnData.javaFieldName}
	 */
	public void set${columnData.javaFieldName?cap_first}(${columnData.javaFieldSortType} ${columnData.javaFieldName}) {
		this.${columnData.javaFieldName} = ${columnData.javaFieldName} == null ? null : ${columnData.javaFieldName};
	}
	</#list> 

}