/**  
* @Title: MultipleDataSourceAspectAdvice.java
* @Package com.ideal.framework.aop
* @Description: TODO
* @author himo.zhang ideal_tom@163.com
* @date 2015-9-22 上午8:39:06
*/
package com.ideal.framework.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.ideal.framework.datasource.DataSourceInstances;
import com.ideal.framework.datasource.DataSourceSwitch;
import com.ideal.framework.utils.string.PatternUtils;


/**
 * @ClassName: MultipleDataSourceAspectAdvice
 * @Description: 多数据源切换
 * @author himo.zhang ideal_tom@163.com
 * @date 2015-9-22 上午8:39:06
 */
@Component
@Aspect
public class MultipleDataSourceAspectAdvice {

	private final Logger logger = Logger.getLogger(getClass());
	
	/**
	 * 添加业务逻辑方法切入点
	 */
	@Pointcut("execution(* com.ideal.*.*.service.imp.*Impl.*(..))")
    public void doServiceCall() { }
    
    /**
     * 管理员添加操作日志(后置通知)
     * @param joinPoint
     * @param rtv
     * @throws Throwable
     */
	@Around("doServiceCall()")
    public Object doServiceCallCalls(ProceedingJoinPoint pjp) throws Throwable{
		Object result = null;
	     //环绕通知处理方法
	     try {
	     	String methodName = pjp.getSignature().getName();
	     	if(PatternUtils.wildMatch("*"+DataSourceInstances.MYSQL+"*", methodName)){
	         	DataSourceSwitch.setDataSourceType(DataSourceInstances.MYSQL);
	         }else  if(PatternUtils.wildMatch("*"+DataSourceInstances.ORACLE+"*", methodName)){
	         	DataSourceSwitch.setDataSourceType(DataSourceInstances.ORACLE);
	         }
	    	result = pjp.proceed();
	     }
	     catch(Exception ex) {
	        ex.printStackTrace();
	     }
	     return result;

	}
	
}
