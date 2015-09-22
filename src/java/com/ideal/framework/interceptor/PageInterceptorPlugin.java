package com.ideal.framework.interceptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import javax.xml.bind.PropertyException;

import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.log4j.Logger;

import com.ideal.framework.dialet.Dialect;
import com.ideal.framework.page.PageView;
import com.ideal.framework.utils.java.ReflectionUtils;
import com.ideal.framework.utils.string.EmptyUtil;



/**
 * Mybatis的分页查询插件，通过拦截StatementHandler的prepare方法来实现。 
 * 只有在参数列表中包括Page类型的参数时才进行分页查询。 
 * 在多参数的情况下，只对第一个Page类型的参数生效。 
 * 另外，在参数列表中，Page类型的参数无需用@Param来标注 
 * @author zhengq
 * 2013-11-19
 * @Email: mmm333zzz520@163.com
 * @version 1.0v
 */
@SuppressWarnings("unchecked")
@Intercepts( { @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class PageInterceptorPlugin implements Interceptor {

	Logger logger = Logger.getLogger(PageInterceptorPlugin.class);
	private static Dialect dialectObject = null; // 数据库方言
	private static String pageSqlId = ""; // mybaits的数据库xml映射文件中需要拦截的ID(正则匹配)
	
	public static void changeDialect(Dialect dialectObject){
		System.out.println(" 数据库方言变更  -->dialectObject : "+dialectObject.getClass().getSimpleName());
		PageInterceptorPlugin.dialectObject = dialectObject;
	}

	public Object intercept(Invocation ivk) throws Throwable {
		if (ivk.getTarget() instanceof RoutingStatementHandler) {
			RoutingStatementHandler statementHandler = (RoutingStatementHandler) ivk
					.getTarget();
			BaseStatementHandler delegate = (BaseStatementHandler) ReflectionUtils.getFieldValue(statementHandler, "delegate");
			MappedStatement mappedStatement = (MappedStatement) ReflectionUtils.getFieldValue(delegate, "mappedStatement");
			
			SqlCommandType type = mappedStatement.getSqlCommandType();
			
			BoundSql boundSql = delegate.getBoundSql();
			Object parameterObject = boundSql.getParameterObject();// 分页SQL<select>中parameterType属性对应的实体参数，即Mapper接口中执行分页方法的参数,该参数不得为空
			
			/**
			 * 方法1：通过ＩＤ来区分是否需要分页．"(?i).*list.*|.*query.*"-->(?i)忽略大小写
			 * 方法2：传入的参数是否有page参数，如果有，则分页，
			 */
			if (mappedStatement.getId().matches("(?i)"+pageSqlId)) { // 拦截需要分页的SQL
				if (parameterObject == null) {
					//throw new NullPointerException("boundSql.getParameterObject() is null!");
					return ivk.proceed();
				} else {
					PageView pageView = null;
					if (parameterObject instanceof PageView) { // 参数就是Pages实体
						pageView = (PageView) parameterObject;
					} else if (parameterObject instanceof Map) {
						for (Entry entry : (Set<Entry>) ((Map) parameterObject)
								.entrySet()) {
							if (entry.getValue() instanceof PageView) {
								pageView = (PageView) entry.getValue();
								break;
							}
						}
						if (pageView == null) {
							return ivk.proceed();
						}
					} else { // 参数为某个实体，该实体拥有Pages属性
						pageView = ReflectionUtils.getValueByFieldType(
								parameterObject, PageView.class);
						if (pageView == null) {
							return ivk.proceed();
						}
					}

					String sql = boundSql.getSql();
					PreparedStatement countStmt = null;
					ResultSet rs = null;
					try {
						Connection connection = (Connection) ivk.getArgs()[0];
						String countSql = "select count(1) from (" + sql+ ") tmp_count"; // 记录统计
						countStmt = connection.prepareStatement(countSql);
						ReflectionUtils.setFieldValue(boundSql, "sql",
								countSql);
						DefaultParameterHandler parameterHandler = new DefaultParameterHandler(
								mappedStatement, parameterObject, boundSql);
						parameterHandler.setParameters(countStmt);
						rs = countStmt.executeQuery();
						int count = 0;
						if (rs.next()) {
							count = ((Number) rs.getObject(1)).intValue();
						}
						pageView.setRowCount(count);
					} finally {
						try {
							rs.close();
						} catch (Exception e) {
						}
						try {
							countStmt.close();
						} catch (Exception e) {
						}
					}
					String pageSql = generatePagesSql(sql, pageView);
					ReflectionUtils.setFieldValue(boundSql, "sql", pageSql); // 将分页sql语句反射回BoundSql.
				}
			}else if(!SqlCommandType.SELECT.equals(type)){
				logger.debug("SqlCommandType.Type : "+type);
//				logger.debug("parameterObject : "+JSONObject.fromObject(parameterObject).toString());
			}
		}
		return ivk.proceed();
	}

	/**
	 * 根据数据库方言，生成特定的分页sql
	 * @param sql
	 * @param page
	 * @return
	 */
	private String generatePagesSql(String sql, PageView page) {
		if (page != null && dialectObject != null) {
			//pageNow默认是从1，而已数据库是从0开始计算的．所以(page.getPageNow()-1)
			int pageNow = page.getPageNow();
			return dialectObject.getLimitString(sql, (pageNow<=0?0:pageNow-1)
					* page.getPageSize(), page.getPageSize());
		}
		return sql;
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties p) {
		 
//		if (EmptyUtil.isEmpty(dialectObject)) {
//			dialectObject = Dialect.getDialectInstance();
//		} 
//		pageSqlId = p.getProperty("pageSqlId");//根据id来区分是否需要分页
//		if (EmptyUtil.isEmpty(pageSqlId)) {
//			try {
//				throw new PropertyException("pageSqlId property is not found!");
//			} catch (PropertyException e) {
//				e.printStackTrace();
//			}
//		}
		
		String dialect = ""; // 数据库方言
		dialect = p.getProperty("dialect");
		System.out.println("数据库方言 dialect : "+dialect);
		if (EmptyUtil.isEmpty(dialect)) {
			try {
				throw new PropertyException("dialect property is not found!");
			} catch (PropertyException e) {
				e.printStackTrace();
			}
		} else {
			try {
				dialectObject = (Dialect) Class.forName(dialect)
						.getDeclaredConstructor().newInstance();
			} catch (Exception e) {
				throw new RuntimeException(dialect + ", init fail!\n" + e);
			}
		}
		pageSqlId = p.getProperty("pageSqlId");//根据id来区分是否需要分页
		if (EmptyUtil.isEmpty(pageSqlId)) {
			try {
				throw new PropertyException("pageSqlId property is not found!");
			} catch (PropertyException e) {
				e.printStackTrace();
			}
		}
	}
}
