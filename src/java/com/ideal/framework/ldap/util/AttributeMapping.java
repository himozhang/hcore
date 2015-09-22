package com.ideal.framework.ldap.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface AttributeMapping {
	String[] mappingValue();
	boolean isArray() default false;
	boolean ifIgnore() default false;
	String keyOfMember() default "";
	String dnOfMember() default "";
}
