
package com.ideal.framework.mybatis.vo;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ideal.core.user.entity.User;
import com.ideal.framework.utils.java.BeanUtil;
import com.ideal.framework.utils.java.ReflectionUtils;
import com.ideal.framework.utils.string.EmptyUtil;

 
/** 
* 2015-4-8 下午05:51:01
* author:himo
* mail:zhangyao0905@gmail.com
* descript:映射实体与SQL对象之间的关系，包括实体对应的表名以及实体属性对应的SQL列
*/ 
public class EntityHelper {

	protected static Logger logger = LoggerFactory.getLogger(EntityHelper.class);

    /**
     * 缓存map，实体类 => 表对象
     */
    private static final Map<Class<?>, EntityTable> entityTableMapCache = new HashMap<Class<?>, EntityTable>();

    /**
     * 缓存map，实体类 => 全部列属性
     */
    private static final Map<Class<?>, List<EntityColumn>> entityClassColumnMapCache = new HashMap<Class<?>, List<EntityColumn>>();
 

    /**
     * 获取表对象
     * @param Object obj
     * @return EntityTable
     */
    public static EntityTable getEntityTable(Object obj) {
    	 //表名
        EntityTable entityTable = entityTableMapCache.get(obj.getClass());
        if(EmptyUtil.isEmpty(entityTable)){
        	 //根据Annotation获得@Table中存放的表名
             if (obj.getClass().isAnnotationPresent(Table.class)) {
                 Table table = obj.getClass().getAnnotation(Table.class);
                 if (!table.name().equals("")) {
                     entityTable = new EntityTable();
                     entityTable.setTable(table);
                 }
             }
             //如果entityTable还为空，则抛出异常
            if (entityTable == null) {
                throw new RuntimeException("无法获取实体类" + obj.getClass().getCanonicalName() + "对应的表名!");
            }else{
            	entityTableMapCache.put(obj.getClass(), entityTable);
            }
        }
        
        return entityTable;
    }
	 
	
    /**
     * 获取查询的Select
     * @param obj 需要查询的数据库表所对应的java实体对象
     * @return
     */
    public static String getAllColumnsStr(Object obj) {
        List<EntityColumn> columnList = getAllColumns(obj);
        String selectBuilderStr = ReflectionUtils.convertElementPropertyToString(columnList, "column", ",");
        return selectBuilderStr;
    }
    
    /**
     * 获取全部列
     *
     * @param entityClass
     * @return
     */
    public static List<EntityColumn> getAllColumns(Object obj) {
        //可以起到初始化的作用
        //getEntityTable(obj);
    	try {
    		initEntityColumnMap(obj);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return entityClassColumnMapCache.get(obj.getClass());
    }

    
    /**
     * 获取Insert方法所传递的对象非空列名以及相关列需要的值参数
     * @param obj  Entity对象
     * @return List<EntityColumn>
     * */
    public static List<EntityColumn> getEntityColumnByNotNullColumns(Object obj) {
    	List<EntityColumn> columnvalueList = new ArrayList<EntityColumn>();
    	
    	List<EntityColumn> columnList = getAllColumns(obj);
        for (EntityColumn entityColumn : columnList) {
        	if(!EmptyUtil.isEmpty(ReflectionUtils.getFieldValue(obj, entityColumn.getProperty()))){
        		columnvalueList.add(entityColumn);
        	}
        }
    	return columnvalueList;
    }
    
    public static void main(String args[]){
//		System.out.println(EntityHelper.getColumnValues(role));
    	User u = new User();
    	
    	System.out.println(getAllColumnsStr(u));
    	//	System.out.println(ReflectionUtils.getFieldValue(menu, "createTime"));
//    	  System.out.println(camelhumpToUnderline("userName"));
//          System.out.println(camelhumpToUnderline("userPassWord"));
//          System.out.println(camelhumpToUnderline("ISO9001"));
//          System.out.println(camelhumpToUnderline("hello_world"));
          
    }
    
    /**
     * 获取update中实体对象除ID意外的字段列
     * @param entityClass
     * @return
     */
    public static String getUpdateSetExceptID(Object obj) {
        List<EntityColumn> columnList = getAllColumns(obj);
        StringBuilder updateBuilder = new StringBuilder();
        for (EntityColumn entityColumn : columnList) {
        	if(!EmptyUtil.isEmpty(ReflectionUtils.getFieldValue(obj, entityColumn.getProperty()))){
            	if(!"id".equalsIgnoreCase(entityColumn.getColumn())){
    	        	updateBuilder.append(',').append(entityColumn.getColumn());
    	        	updateBuilder.append(" = #{").append(entityColumn.getProperty()).append('}');
            	}
        	}
        }
        String upadteStr = updateBuilder.toString().replaceFirst(",","");
        return upadteStr;
    }
    
    /**
     * 获取update语句中实体对象的所有字段列
     * @param entityClass
     * @return
     */
    public static String getUpdateSet(Object obj) {
        List<EntityColumn> columnList = getAllColumns(obj);
        StringBuilder updateBuilder = new StringBuilder();
        for (EntityColumn entityColumn : columnList) {
        	if(!EmptyUtil.isEmpty(ReflectionUtils.getFieldValue(obj, entityColumn.getProperty()))){
//            	if(!"id".equalsIgnoreCase(entityColumn.getColumn())){
//            		logger.debug(entityColumn.getColumn()+" entityColumn.getJavaType() : "+entityColumn.getJavaType().getSimpleName());
    	        	updateBuilder.append(',').append(entityColumn.getColumn());
    	        	updateBuilder.append("=#{").append(entityColumn.getProperty()).append('}');
//            	}
        	}
        }
        String upadteStr = updateBuilder.toString().replaceFirst(",","");
        return upadteStr;
    }
    
    /**
     * 获取查询的Select where
     * @param entityClass
     * @return
     */
    public static String getSelectWhere(Object obj) {
        List<EntityColumn> columnList = getAllColumns(obj);
        StringBuilder selectBuilder = new StringBuilder();
        for (EntityColumn entityColumn : columnList) {
    		if(!EmptyUtil.isEmpty(ReflectionUtils.getFieldValue(obj, entityColumn.getProperty()))){
    			selectBuilder.append(" and ").append(entityColumn.getColumn()+"=");
//    			logger.debug(entityColumn.getColumn()+" entityColumn.getJavaType() : "+entityColumn.getJavaType().getSimpleName()+" ReflectionUtils.getFieldValue(obj, entityColumn.getProperty())： "+ReflectionUtils.getFieldValue(obj, entityColumn.getProperty()));
    			selectBuilder.append("#{").append(entityColumn.getProperty()).append('}');
    		}
        }
        String selectBuilderStr = selectBuilder.toString().replaceFirst("and", " ");
        return selectBuilderStr;
    }

    

    /**
     * 初始化实体属性
     *
     * @param entityClass
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     * @throws NoSuchFieldException 
     * @throws SecurityException 
     */
    private static synchronized void initEntityColumnMap(Object obj) throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchFieldException {
    		List<EntityColumn> columnList = entityClassColumnMapCache.get(obj.getClass());
        	EntityTable et = getEntityTable(obj);
        	String tableName = et.getName(); 
        	String entityClassName = obj.getClass().getSimpleName();
        	
        	if(EmptyUtil.isEmpty(columnList)){
        		//实体对应的属性
                List<Field> fieldList = getAllField(obj.getClass(), null);
                columnList = new ArrayList<EntityColumn>();
                
                for (Field field : fieldList) {
                	 //排除通过cglib反向代理生成的无用字段
                	if(field.getName().indexOf("CGLIB")==-1){
                		 //排除字段
                        if (field.isAnnotationPresent(Transient.class)) {
                            continue;
                        }
                         EntityColumn entityColumn = new EntityColumn();
                    	 String columnName = null;
                         if (field.isAnnotationPresent(Column.class)) {
                             Column column = field.getAnnotation(Column.class);
                             columnName = column.name();
                         } else {
                             columnName = BeanUtil.camelhumpToUnderline(field.getName());
                         }
                         
                         //设置实体所对应的表明
                         entityColumn.setTableName(tableName);
                         //设置实体类名
                         entityColumn.setEntityClassName(entityClassName);
                         //注入值为实体属性加上#作为参数
                         entityColumn.setValue("#{"+field.getName()+"}");
                         //设置实体对应的属性名
                         entityColumn.setProperty(field.getName());
                         //设置实体属性所对应的数据库字段列
                         entityColumn.setColumn(columnName.toUpperCase());
                         //设置属性所对应的java数据类型
                         entityColumn.setJavaType(field.getType());
                         columnList.add(entityColumn);
                	}
                }
                entityClassColumnMapCache.put(obj.getClass(), columnList);
        	}
    }
    
  
    /**
     * 获取全部的Field
     *
     * @param entityClass
     * @param fieldList
     * @return
     */
    public static List<Field> getAllField(Class<?> entityClass, List<Field> fieldList) {
        if (fieldList == null) {
            fieldList = new ArrayList<Field>();
        }
        if (entityClass.equals(Object.class)) {
            return fieldList;
        }
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            //排除静态字段，解决bug#2
            if (!Modifier.isStatic(field.getModifiers())) {
                fieldList.add(field);
            }
        }
        if (entityClass.getSuperclass() != null
                && !entityClass.getSuperclass().equals(Object.class)
                && !Map.class.isAssignableFrom(entityClass.getSuperclass())
                && !Collection.class.isAssignableFrom(entityClass.getSuperclass())) {
            return getAllField(entityClass.getSuperclass(), fieldList);
        }
        return fieldList;
    }
}