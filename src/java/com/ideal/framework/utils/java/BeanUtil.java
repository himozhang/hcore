package com.ideal.framework.utils.java;

import java.lang.reflect.Field;
import java.util.Map;

import com.ideal.framework.exception.ControllerException;
import com.ideal.framework.exception.ReflectException;
import com.ideal.framework.utils.string.EmptyUtil;


/** 
* @ClassName:BeanUtil.java
* @CreateTime 2015-5-4 下午08:51:41
* @author:himo
* @mail:zhangyao0905@gmail.com
* @Description:java实体操作工具
*/ 
public class BeanUtil {
	
	/**
	 * 获得Entity所对应的包名
	 * */
	@SuppressWarnings({ "unchecked"})
	public static String getClassPackageName(Class clazz,String BasePaceageName) {
		String packageName = "";
		try {
			//clazz.getSimpleName().toString().toLowerCase(); 这里是获取实体类的简单名称，再把类名转为小写
			packageName = clazz.getPackage().getName();
			//logger.debug("packageName before : "+packageName);
			String[] packageNames = packageName.split("\\.");
			String[] BasePaceagePaths = BasePaceageName.split("\\.");
			if(packageNames.length>BasePaceagePaths.length){
				packageName = packageNames[BasePaceagePaths.length];
			}
		} catch (Exception e) {
			 new  ControllerException("获得JAVA类包名出错！");
		}
		
		//logger.debug("packageName after : "+packageName);
		return packageName;
	}
	
	/**
	 * 根据泛型T类型创建泛型T实例对象
	 * */
	@SuppressWarnings("unchecked")
	public static Object get_T_Instance(Class clazz) {
		Class clasz = ReflectionUtils.getSuperClassGenricType(clazz);
		Object obj = null;
		try {
			obj = clasz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			new ReflectException("根据泛型T类型创建泛型T实例对象失败！");
		}
		return obj;
	}
	
	/**
	 * 将Map集合对象转换为JavaBena类，JavaBena类必须拥有默认构造函数.
	 * @param map 传入的Map集合对象
	 * @param clazz 传入的JavaBean类
	 * @return Bean对象
	 */
	public static <T> T toBean(Map<String,Object> map,Class<T> clazz){
		T bean = null;
		try {
			Field[] fields = clazz.getDeclaredFields();
			bean = clazz.newInstance();
			for (String key : map.keySet()) {
				for (Field field : fields) {
					if(key.equals(field.getName())){
						System.out.println(key+"=="+field.getName());
						field.setAccessible(true);//暴力反射
						field.set(bean, map.get(key));
						break;
					}
				}
			}
		} catch (Exception e) {
			new ReflectException(clazz.getName()+"类转换异常！");
		} 
		return bean;
	}
	
	/**
	 * 得到实体对象的Annotation对象
	 * */
	@SuppressWarnings("unchecked")
	public static Object getBeanAnnotationPresent(Class beanClazz,Class annotationClazz){
		if (beanClazz.isAnnotationPresent(annotationClazz)) {
			Object annotation = beanClazz.getAnnotation(annotationClazz);
	        if (!EmptyUtil.isEmpty(annotation)) {
	            return annotation;
	        }
	    }else{
	    	new ReflectException(beanClazz.getName()+"的"+annotationClazz.getName()+"注解对象不存在！");
	    }
		return null;
	}
	
	
	/**
	 * 判断实体属性是否存在于BaseEntity中
	 * @param Class clazz -->BaseEntity
	 * @param String fieldName 属性名
	 * @return boolean 
	 * */
	@SuppressWarnings("unchecked")
	public static boolean isFieldInBaseEntity(Class clazz,String fieldName){
		 boolean isIn = false;
		 for(Field field:clazz.getDeclaredFields()){
			 if(fieldName.equals(field.getName())){
				 isIn = true;
			 }
			 if(isIn){
				 break;
			 }
		 }
		 for(Field field:clazz.getSuperclass().getDeclaredFields()){
			 if(fieldName.equals(field.getName())){
				 isIn = true;
			 }
			 if(isIn){
				 break;
			 }
		 }
		return isIn;
	}
	
	/**
     * 将驼峰风格替换为下划线风格
     * @like createTime ->  CREATE_TIME
     */
    public static String camelhumpToUnderline(String str) {
        final int size;
        final char[] chars;
        final StringBuilder sb = new StringBuilder(
                (size = (chars = str.toCharArray()).length) * 3 / 2 + 1);
        char c;
        for (int i = 0; i < size; i++) {
            c = chars[i];
            if (isUppercaseAlpha(c)) {
                sb.append('_').append(c);
            } else {
                sb.append(toUpperAscii(c));
            }
        }
        return sb.charAt(0) == '_'? sb.substring(1): sb.toString();
    }

    /**
     * 判断字符是否大写
     * @like A :return true | a:return false
     */
    public static boolean isUppercaseAlpha(char c) {
        return (c >= 'A') && (c <= 'Z');
    }

    /**
     * 将字符转换为大写
     * @like a return : A
     */
    public static char toUpperAscii(char c) {
        if (isUppercaseAlpha(c)) {
            c -= (char) 0x20;
        }
        return c;
    }
    
    /**
     * 将下划线风格替换为驼峰风格
     * @like create_time -> createTime
     */
   public static String underlineToCamelhump(String name) {
			String[] split = name.toLowerCase().split("_");
		      if (split.length > 1) {
		        StringBuffer sb = new StringBuffer();
		        for (int i = 0; i < split.length; i++) {
		         String tempName="";
		          if(i==0) {
		        	  tempName=split[0].substring(0, 1).toLowerCase() + split[0].substring(1, split[0].length());
		        	  sb.append(tempName);
		        	  continue;
		        	  }
		          tempName= split[i].substring(0, 1).toUpperCase() + split[i].substring(1, split[i].length());
		          sb.append(tempName);
		        }
		
		        return sb.toString();
		      }
		      String tempName = split[0].substring(0, 1).toLowerCase() + split[0].substring(1, split[0].length());
		 return tempName;
	}
 
	
	public static void main(String[] args){
//		RequestMapping tb = (RequestMapping)getBeanAnnotationPresent(CRUDController.class,RequestMapping.class);
//		System.out.println(tb.value()[0]);;
//		System.out.println(getClassPackageName(ArticleController.class,"com.ricore"));
//		System.out.println(ReflectionUtils.getSuperClassGenricType(ArticleController.class).getSimpleName().toLowerCase());
	}
	
}
