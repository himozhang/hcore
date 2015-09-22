package com.ideal.framework.mybatis;

import java.util.List;

import org.apache.ibatis.jdbc.SqlBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ideal.core.user.entity.User;
import com.ideal.framework.basecore.baseEntity.BaseEntity;
import com.ideal.framework.basecore.baseEntity.IdEntity;
import com.ideal.framework.constants.SQLConstants;
import com.ideal.framework.mybatis.utils.SQLBulderUtils;
import com.ideal.framework.mybatis.vo.EntityColumn;
import com.ideal.framework.mybatis.vo.EntityHelper;
import com.ideal.framework.utils.string.EmptyUtil;

/** 
* 2015-4-8 下午05:50:21
* author:himo
* mail:zhangyao0905@gmail.com
* descript:Mybaties的SQL代理生成器
*/ 
public class MyBatisCRUDTemplates extends SqlBuilder {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * SQL构建工具，解析传递过来的参数
	 * */
	private SQLBulderUtils sbu = new SQLBulderUtils();
	
	
	/**
	 * 插入一条记录
	 * */
	public String insert(Object obj) {
		
		if(obj instanceof IdEntity){
			//如果ID为空，则执行基础数据初始化操作
			if(EmptyUtil.isEmpty(((IdEntity)obj).getId())){
				((IdEntity) obj).initID();
			}
		}
		
		BEGIN();
		INSERT_INTO(EntityHelper.getEntityTable(obj).getName());
		
		List<EntityColumn> columnvalueList = EntityHelper.getEntityColumnByNotNullColumns(obj);
		for(EntityColumn ecv :columnvalueList){
			VALUES(ecv.getColumn(),String.valueOf(ecv.getValue()));
		}
		String sql = SQL();
		logger.debug("insert sql : "+sql);
		return sql;
	}
	
	/**
	 * 删除一条数据对象
	 * */
	public String delete(Object obj) throws Exception {
		BEGIN();
		DELETE_FROM(sbu.getTableName(obj));
		WHERE("id=#{id,jdbcType=VARCHAR}");
		String sql = SQL();
		logger.debug(" delete sql : "+sql);
		return sql;
	}
	
	/**
	 * 根据条件删除数据对象
	 * */
	public String deleteByMapperHelper(Object obj) throws Exception {
		BEGIN();
		DELETE_FROM(sbu.getTableName(obj));
		String where = sbu.getWhere(obj);
		if(!EmptyUtil.isEmpty(where)){
			WHERE(where);
		}
		String sql = SQL();
		logger.debug("deleteByMapperHelper sql : "+sql);
		return sql;
	}
	
	/**
	 * 修改一条记录
	 * */
	public String update(Object obj) throws Exception {
		if(obj instanceof BaseEntity){
			//如果修改时间为空，则执行基础数据初始化操作
			if(EmptyUtil.isEmpty(((BaseEntity)obj).getModifyTime())){
				((BaseEntity) obj).initBaseInfo();
			}
		}
		BEGIN();
		UPDATE(EntityHelper.getEntityTable(obj).getName());
		SET(EntityHelper.getUpdateSetExceptID(obj));
		WHERE("id = #{id}");
		String sql = SQL();
		logger.debug("update sql : "+sql);
		return sql;
	}
	
	/**
	 * 根据条件修改记录
	 * */
	public String updateBySql(Object obj) throws Exception {
		
		BEGIN();
		UPDATE(sbu.getTableName(obj));
		SET(sbu.getSet(obj));
		WHERE(sbu.getWhere(obj));
		String sql = SQL();
		logger.debug("updateBySql sql : "+sql);
		return sql;
	}
	
	
	/**
	 * 查询一条结果记录
	 * */
	public String getByMapperHelper(Object obj) throws Exception {
		Object entity = sbu.getEntity(obj);
		BEGIN();
		SELECT(EntityHelper.getAllColumnsStr(entity));
		FROM(sbu.getTableName(obj)+"  t ");
		String where = sbu.getWhere(obj);
		if(!EmptyUtil.isEmpty(where)){
			WHERE(where);
		}
		String sql = SQL();
		logger.debug(" getByMapperHelper sql : "+sql);
		return sql;
	}
	
	
	/**
	 * 返回查询的结果集List
	 * */
	public String getListByMapperHelper(Object obj) throws Exception {
		
		Object entity = sbu.getEntity(obj);
		BEGIN();
		SELECT(EntityHelper.getAllColumnsStr(entity));
		FROM(sbu.getTableName(obj));
		String where = sbu.getWhere(obj);
		if(!EmptyUtil.isEmpty(where)){
			WHERE(where);
		}
		
		String orderby = sbu.getOrderBy(obj);
		if(!EmptyUtil.isEmpty(orderby)){
			ORDER_BY(orderby);
		}
		
		String sql = SQL();
		logger.debug("getListByMapperHelper sql : "+sql);
		return sql;
	}

	/**
	 * 排序
	 * */
	public String exchangeSort(Object obj) throws Exception {
		
		BEGIN();
		UPDATE(sbu.getTableName(obj));
		String setStr = " sort = #{sort} ";
		if(!EmptyUtil.isEmpty(sbu.getByKey(obj, "modifier"))){
			setStr+=" ,modifier_id = #{modifier},modify_time = #{modify_time} ";
		}
		SET(setStr);
		WHERE("id = #{id}");
		String sql = SQL();
		logger.debug("exchangeSort sql : "+sql);
		return sql;
	}
	
	/**
	 * 返回下一个排序号
	 * */
	public String getNextSortNum(Object entity) throws Exception {
		BEGIN();
		SELECT(" max(sort) ");
		FROM(EntityHelper.getEntityTable(entity).getName());
		String sql = SQL();
		logger.debug("getNextSortNum sql : "+sql);
		return sql;
	}
	
	/**
	 * 返回总记录数Count
	 * */
	public String getCount(Object obj) throws Exception {
		BEGIN();
		SELECT(" count(*) ");
		FROM(sbu.getTableName(obj));
		String where = sbu.getWhere(obj);
		if(!EmptyUtil.isEmpty(where)){
			WHERE(where);
		}
		String sql = SQL();
		logger.debug("getCount sql : "+sql);
		return sql;
	}
	
	
	/**
	 * 执行sql查询
	 * */
	public String excuteSql(Object obj) throws Exception {
		String sql = (String)sbu.getByKey(obj, SQLConstants.EXCUTE_SQL);
		String where = sbu.getWhere(obj);
		sql = sql+" where "+where ;
		logger.debug("excuteSql sql : "+sql);
		return sql;
	}
	

	public static void main(String[] args) throws Exception {
		MyBatisCRUDTemplates myBatisUtils = new MyBatisCRUDTemplates();
		User user = new User();
		user.setId("1");
		user.setLoginName("aa");
//		 System.out.println(myBatisUtils.update(user));
//		System.out.println(EntityHelper.getInsertColumnValues(user));
		
	}
}
