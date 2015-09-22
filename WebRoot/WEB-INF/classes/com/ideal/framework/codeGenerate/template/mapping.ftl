<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >

<!-- @CreateTime ${CreateTime} -->
<!-- @ClassName:${ClassName}Mapper.xml -->
<!-- @author:${author} -->
<!-- @mail:${mail} -->
<!-- @Description:${Description}-Mapper - 由代码生成器生成 -->

<mapper namespace="${packageName}.${moduleName}.${className?lower_case}.dao.${ClassName}Mapper" >
  <resultMap id="BaseResultMap" type="${packageName}.${moduleName}.${className?lower_case}.entity.${ClassName}" >
    
    <!-- 基本属性从 ${className}2Mapper.xml 文件中拷贝-->
	
	
    <result column="PID" property="pid" jdbcType="VARCHAR" />
    <association property="parent${ClassName}" column="pid"  select="${packageName}.${moduleName}.${className?lower_case}.dao.${ClassName}Mapper.getParent${ClassName}" />   
    <collection property="child${ClassName}s" column="id" ofType="${ClassName}" select="${packageName}.${moduleName}.${className?lower_case}.dao.${ClassName}Mapper.getChild${ClassName}s" /> 
	
  </resultMap>
  
  <sql id="Base_Column_List" >
	${columnDataConnections.allColumn}
  </sql>
  
  <select id="getChild${ClassName}s" resultMap="BaseResultMap">
    select distinct
    <include refid="Base_Column_List" />
    from ${tableName}
    where PID = ${"#"}${"{"}id}
    order by sort asc,modify_time desc
  </select>
  
  <select id="getParent${ClassName}" resultMap="BaseResultMap">
    select distinct
    <include refid="Base_Column_List" />
    from ${tableName}
    where ID = ${"#"}${"{"}pid}
  </select>
  
</mapper>