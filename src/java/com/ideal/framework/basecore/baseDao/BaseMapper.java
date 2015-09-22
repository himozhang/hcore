/**
 * 
 */
package com.ideal.framework.basecore.baseDao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import com.ideal.framework.mybatis.MyBatisCRUDTemplates;


 
/** 
* 2015-4-8 下午05:48:21
* author:himo
* mail:zhangyao0905@gmail.com
* descript:基础MAPPER对象，所有模块的mapper都必须继承该接口
*/ 
//@Repository用于将数据访问层 (DAO 层 ) 的类标识为 Spring Bean
@Repository
public interface BaseMapper<T> {

	/**
	 * @see  /** 
     * 获取产品系列 
     *  
     * @param id 
     *            系列编号 
     *  
     * @return List<ProductList> 
	    @SelectProvider(type = ProductProvider.class, method = "getListProductListSql")  
	    @Results(value={  
	            @Result(id=true,property="id", column="ID",javaType=Integer.class,jdbcType=JdbcType.INTEGER),  
	            @Result(property="seriesName",column="SERIESNAME",javaType=String.class,jdbcType=JdbcType.VARCHAR),  
	            @Result(property="seriesImage",column="SERIESIMAGE",javaType=String.class,jdbcType=JdbcType.VARCHAR)  
	    })
	 * */
	
	
	/** 
     * Insert语句从CUDTemplate类中生成 
     * @param obj 
     * 使用MyBatisUtils操作
     */  
    @InsertProvider(type = MyBatisCRUDTemplates.class, method = "insert")  
    @Options(useGeneratedKeys=false )
	public int insert(T entity);
    
    /**
   	 * 删除一条记录
   	 * Delete语句从CUDTemplate类中生成
   	 * @param obj
   	 */
   	@DeleteProvider(type = MyBatisCRUDTemplates.class, method = "delete")
   	public int delete(Map<String,Object> params);
   	
       /**
   	 * 根据条件删除记录
   	 */
   	@DeleteProvider(type = MyBatisCRUDTemplates.class,method = "deleteByMapperHelper")
   	public int deleteByMapperHelper(Map<String,Object> params);

	/**
	 * @param entity
	 * 使用MyBatisUtils操作
	 */
    @UpdateProvider(type = MyBatisCRUDTemplates.class, method = "update") 
    @Options(useGeneratedKeys=false )
	public int update(Object entity);
    
    /**
     * 修改数据
     * 使用MyBatisUtils操作<br/>
     * @param Map<String, Object> map <br/>
     * @like
	 *  Map<String, Object> map = new HashMap<String, Object>();<br/>
	 *	String set = "photo=#{photo}";<br/>
	 *	String where = "id=#{id}";<br/>
	 *	map.put("photo", "/upload/userphoto/2015/4/7/crop_5415c7fe534a63366b48d388f204c328_20150407_133318.jpg");<br/>
	 *	map.put("id", "ab0fe284f8a045a4a49089f96f24fce4");<br/>
	 * */
    @UpdateProvider(type = MyBatisCRUDTemplates.class, method = "updateBySql") 
    @Options(useGeneratedKeys=false )
	public int updateBySql(Map<String, Object> map);
    
	
	/**
	 * 根据实体注入的值获得记录
	 * 使用MyBatisUtils操作
	 * */
	@SelectProvider(type = MyBatisCRUDTemplates.class, method = "getByMapperHelper")
	@ResultMap(value="BaseResultMap")
	public T getByMapperHelper(Map<String,Object> params);
	
	/**
	 * 根据MapperHelper查询
	 * @see 如果map中存在PageView对象，则进行分页查询
	 */
	@SelectProvider(type = MyBatisCRUDTemplates.class,method = "getListByMapperHelper")
	@ResultMap(value="BaseResultMap")
	public List<T> getListByMapperHelper(Map<String, Object> map);
	
	
	@UpdateProvider(type = MyBatisCRUDTemplates.class, method = "excuteSql") 
    @Options(useGeneratedKeys=false )
	public int executeSql(Map<String, Object> params);
    
    /**
	 * @param entity
	 * 使用MyBatisUtils操作
	 */
    @UpdateProvider(type = MyBatisCRUDTemplates.class, method = "exchangeSort") 
    @Options(useGeneratedKeys=false )
	public int exchangeSort(Map<String,Object> params);
	    
	/**
	 * 根据MapperHelper查询
	 * @see 如果map中存在PageView对象，则进行分页查询
	 */
	@SelectProvider(type = MyBatisCRUDTemplates.class,method = "getCount")
	public int getCount(Map<String, Object> map);

	/**
	 * 根据MapperHelper查询
	 * @see 如果map中存在PageView对象，则进行分页查询
	 */
	@SelectProvider(type = MyBatisCRUDTemplates.class,method = "getNextSortNum")
	public Object getNextSortNum(T entity);
}
