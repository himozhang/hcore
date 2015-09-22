/**  
* @Title: datasource.java
* @Package com.ideal.framework.annotation
* @Description: TODO
* @author himo.zhang ideal_tom@163.com
* @date 2015-9-22 上午11:25:59
*/
package com.ideal.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Target;

import com.ideal.framework.datasource.DataSourceInstances;

/**
 * @ClassName: datasource
 * @Description: 数据源选择
 * @author himo.zhang ideal_tom@163.com
 * @date 2015-9-22 上午11:25:59
 */
@Documented
@Target(ElementType.METHOD)
@Inherited
public @interface DataSourceAnno {
 
    String ds() default DataSourceInstances.ORACLE;
 
}