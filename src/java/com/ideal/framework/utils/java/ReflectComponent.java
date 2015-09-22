package com.ideal.framework.utils.java;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ideal.framework.ldap.util.AttributeMapping;

@SuppressWarnings({"rawtypes","unused"})
public class ReflectComponent  {
	
	 Logger log = LoggerFactory.getLogger(ReflectComponent.class);
	 
	public ReflectComponent() {
	}

	// get all attributes from the class
	public List<String> allAttributeName(Class clas) {
		List<String> attributeNames = new ArrayList<String>();
		Field[] fields = clas.getDeclaredFields();
		if (fields != null && fields.length > 0) {
			for (Field f : fields) {
				attributeNames.add(f.getName());
			}
		}
		return attributeNames;
	}

	// get all methods from the class
	public List<String> allMethodName(Class clas) {
		List<String> methodNames = new ArrayList<String>();
		Method[] methods = clas.getDeclaredMethods();
		if(methods!=null&&methods.length>0){
			for (Method m : methods) {
				methodNames.add(m.getName());
			}
		}
		return methodNames;
	}

	// return the class if has the attribute
	public boolean ifContainAttribute(Class clas, String attributeName) {
		boolean has = false;
		Field[] fields = clas.getDeclaredFields();
		if (fields != null && fields.length > 0) {
			if (fields != null && fields.length > 0) {
				for (Field f : fields) {
					if(attributeName.equals(f.getName())){
						has = true;
						break;
					}
				}
			}
		}
		return has;
	}

	// return the class if has the method
	public boolean ifContainMethod(Class clas, String methodName) {
		boolean has = false;
		Method[] methods = clas.getDeclaredMethods();
		if(methods!=null&&methods.length>0){
			for (Method m : methods) {
				if(methodName.equals(m.getName())){
					has = true;
					break;
				}
			}
		}
		return has;
	}

	// return the object if has the value
	public String attributeContainValue(Object obj, Object value) {
		String attrName = null;
		Field[] fields = obj.getClass().getDeclaredFields();
		if(fields!=null&&fields.length>0){
			for(Field f : fields){
				String mName = parGetName(f.getName());
				try{
					Method method = obj.getClass().getMethod(mName);
					Object o = method.invoke(obj);
					if(o==value){
						attrName = f.getName();
					}
					break;
				}catch(Exception e){
					log.info("Error in attributeContainValue",e);
				}
			}
		}
		return attrName;
	}

	// return the class attribute which annotated the Annotated
	public String attributeAnnotated(Class clas,Class<? extends Annotation> annoClass) {
		String attrName = null;
		Field[] fields = clas.getDeclaredFields();
		if(fields!=null&&fields.length>0){
			for (Field f : fields) {
				if(f.isAnnotationPresent(annoClass)){
					attrName = f.getName();
					break;
				}
			}
		}
		return attrName;
	}

	// return the value of the object which's attribute annotated the annotation
	public Object valueOfAnnotatedAttribute(Object obj, Class<? extends Annotation> annoClass) {
		Object value = null;
		Field[] fields = obj.getClass().getDeclaredFields();
		if(fields!=null&&fields.length>0){
			for (Field f : fields) {
				if(f.isAnnotationPresent(annoClass)){
					String mName = parGetName(f.getName());
					try{
						Method method = obj.getClass().getMethod(mName);
						value = method.invoke(obj);
						break;
					}catch(Exception e){
						log.info("Error in valueOfAnnotatedAttribute",e);
					}
				}
			}
		}
		return value;
	}
	
	//get the value of the Object's property which annotated the Annotation and the value 
	public Object fetchAnnotatedValue(Object obj,Class<? extends Annotation> annoClass,Object annoValue) {
		Object value = null;
		Field[] fields = obj.getClass().getDeclaredFields();
		if(fields!=null&&fields.length>0){
			for (Field f : fields) {
				if(f.isAnnotationPresent(annoClass)){
					AttributeMapping am = f.getAnnotation(AttributeMapping.class);
					String[] amVs = am.mappingValue();
					for (String amv : amVs) {
						if (amv.equals(annoValue)) {
							String mName = this.parGetName(f.getName());
							try {
								Method method = obj.getClass().getMethod(mName);
								value = method.invoke(obj);
								break;
							} catch (Exception e) {
								log.info("Error in fetchAnnotatedValue!", e);
							}
						}
					}
				}
			}
		}
		return value;
	}

	// getMethodName
	private String parGetName(String fieldName) {
		if (null == fieldName || "".equals(fieldName)) {
			return null;
		}
		return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}

	// setMethodName
	private String parSetName(String fieldName) {
		if (null == fieldName || "".equals(fieldName)) {
			return null;
		}
		return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}
}
