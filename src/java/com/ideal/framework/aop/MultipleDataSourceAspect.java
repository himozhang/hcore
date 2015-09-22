/**  
* @Title: MultipleDataSourceAspectAdvice.java
* @Package com.ideal.framework.aop
* @Description: TODO
* @author himo.zhang ideal_tom@163.com
* @date 2015-9-22 上午8:39:06
*/
package com.ideal.framework.aop;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.ideal.framework.annotation.ChooseDataSource;
import com.ideal.framework.datasource.DataSourceSwitch;
import com.ideal.framework.utils.string.EmptyUtil;


/**
 * @ClassName: MultipleDataSourceAspectAdvice
 * @Description: 多数据源切换
 * @author himo.zhang ideal_tom@163.com
 * @date 2015-9-22 上午8:39:06
 */
@Component
@Aspect
public class MultipleDataSourceAspect {

	private final Logger logger = Logger.getLogger(getClass());
	
	/**
	 * 添加业务逻辑方法切入点
	 */
//	@Pointcut("execution(* com.ideal.*.*.service.imp.*Impl.*(..))")
	@Pointcut("@annotation(com.ideal.framework.annotation.ChooseDataSource)") 
    public void doServiceCall() { }
    
    /**
     * 管理员添加操作日志(后置通知)
     * @param joinPoint
     * @param rtv
     * @throws Throwable
     */
	@Around("doServiceCall()")
    public Object doServiceCallCalls(ProceedingJoinPoint pjp) throws Throwable{
		
//		@annotation方式进行切面处理，注解需要写在service的实现层
		String ds = getDataSourceByAnnotation(pjp);
		logger.debug("----------> ds : "+ds);
		
		Object result = null;
	     //环绕通知处理方法
	     try {
//	     	String methodName = pjp.getSignature().getName();
//	     	if(PatternUtils.wildMatch("*"+DataSourceInstances.MYSQL+"*", methodName)){
//	         	DataSourceSwitch.setDataSourceType(DataSourceInstances.MYSQL);
//	         }else  if(PatternUtils.wildMatch("*"+DataSourceInstances.ORACLE+"*", methodName)){
//	         	DataSourceSwitch.setDataSourceType(DataSourceInstances.ORACLE);
//	         }
	    	 if(!EmptyUtil.isEmpty(ds)){
	    		 DataSourceSwitch.setDataSourceType(ds);
	    	 }
	    	result = pjp.proceed();
	     }
	     catch(Exception ex) {
	        ex.printStackTrace();
	     }
	     return result;
	}
	
	/**
	  * 获取Annotation中配置的参数
	  * */ 
   public static String getDataSourceByAnnotation(ProceedingJoinPoint joinPoint)  
           throws Exception {  
       String targetName = joinPoint.getTarget().getClass().getName();  
       String methodName = joinPoint.getSignature().getName();  
       Object[] arguments = joinPoint.getArgs();  
 
       Class targetClass = Class.forName(targetName);  
       Method[] method = targetClass.getMethods();  
       String methode = "";  
       for (Method m : method) {  
           if (m.getName().equals(methodName)) {  
               Class[] tmpCs = m.getParameterTypes();  
               if (tmpCs.length == arguments.length) {  
               	
            	   ChooseDataSource dsa = m.getAnnotation(ChooseDataSource.class);  
                   if (dsa != null) {  
                       methode = dsa.value();  
                   }  
                   break;  
               }  
           }  
       }  
       return methode;  
   } 
	
}
