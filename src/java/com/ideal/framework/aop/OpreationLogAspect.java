/**  
* @Title: LogAop.java
* @Package com.ideal.framework.aop
* @Description: TODO
* @author himo.zhang ideal_tom@163.com
* @date 2015-9-22 下午1:58:56
*/
package com.ideal.framework.aop;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.ideal.framework.annotation.ChooseDataSource;
import com.ideal.framework.basecore.baseEntity.IdEntity;
import com.ideal.framework.datasource.DataSourceSwitch;
import com.ideal.framework.utils.json.JsonDateValueProcessor;
import com.ideal.framework.utils.string.EmptyUtil;

/**
 * @ClassName: OpreationLogAspect
 * @Description: 操作日志记录，添加、删除、修改方法AOP
 * @author himo.zhang ideal_tom@163.com
 * @date 2015-9-22 下午1:58:56
 */
@Aspect
@Component
public class OpreationLogAspect {
	
	private final Logger logger = Logger.getLogger(getClass());
	
	/**
	 * 添加业务逻辑方法切入点
	 */
    @Pointcut("execution(* com.ideal.*.*.service.imp.*Impl.*save*(..))||execution(* com.ideal.*.*.service.imp.*Impl.*Save*(..))")
    public void insertServiceCall() { }
    
    /**
	 * 修改业务逻辑方法切入点
	 */
    @Pointcut("execution(* com.ideal.*.*.service.imp.*Impl.*update*(..))||execution(* com.ideal.*.*.service.imp.*Impl.*Update*(..))")
    public void updateServiceCall() { }
    
    /**
	 * 删除影片业务逻辑方法切入点
	 */
    @Pointcut("execution(* com.ideal.*.*.service.imp.*Impl.*del*(..))||execution(* com.ideal.*.*.service.imp.*Impl.*Del*(..))")
    public void deleteServiceCall() { }
    
    public void saveLog(JoinPoint joinPoint){
		String url = "";
		String ip = "";
		String infoid = "";
		//获取方法名
		String className = joinPoint.getSourceLocation().getWithinType().getName();
		String result = "success";
		String methodName = joinPoint.getSignature().getName();
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		
		for (Object object : joinPoint.getArgs()) {
			if(object instanceof HttpServletRequest){
				HttpServletRequest request = (HttpServletRequest)object;
				url =  request.getRequestURL().toString();
				ip = request.getRemoteAddr();
				continue;
			}else if (object instanceof IdEntity) {
				infoid = ((IdEntity)object).getId();
			}
			try {
				 JsonConfig jsonConfig = new JsonConfig();  
				 jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());  
				 jsonObject = JSONObject.fromObject(object, jsonConfig);  
				 jsonArray.add(jsonObject);
			} catch (Exception e) {
				//LOGGER.error("logWrite", e);
				jsonArray.add(object.toString());
			}
		}
		logger.debug("-------------------"+className+"."+methodName+"    日志写入开始  -------------------------------------------------------");
		logger.debug("url : "+url);
		logger.debug("ip: "+ip);
		logger.debug("infoid :  "+infoid);
		logger.debug("参数 : "+jsonArray.toString());
		logger.debug("-------------------"+className+"."+methodName+"    日志写入结束  -------------------------------------------------------");
		
//		String uid =  EmptyUtil.isEmpty(SpringSecurityUtils.getCurrentUser())?"":SpringSecurityUtils.getCurrentUser().getUserId();
//		String uname =  EmptyUtil.isEmpty(SpringSecurityUtils.getCurrentUserName())?"":SpringSecurityUtils.getCurrentUserName();
//		SysLogUtils.addLog(methodName, ip, className, jsonArray.toString(),url.toString(),uid,uname,result,infoid);
    }
    
    public void saveLog(ProceedingJoinPoint pjp){
		
		String url = "";
		String ip = "";
		String infoid = "";
		//获取方法名
		String className = pjp.getSignature().getDeclaringType().getName();
		String methodName = pjp.getSignature().getName();
		String result = "success";
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		
		logger.debug("-------------"+className+"."+methodName+"    日志写入开始  -------------------------------------------------------");
		for (Object object :  pjp.getArgs()) {
			if(object instanceof HttpServletRequest){
				HttpServletRequest request = (HttpServletRequest)object;
				url =  request.getRequestURL().toString();
				ip = request.getRemoteAddr();
				continue;
			}else if (object instanceof IdEntity) {
				infoid = ((IdEntity)object).getId();
			}
			
			try {
				jsonObject = JSONObject.fromObject(object);
				jsonArray.add(jsonObject);
			} catch (Exception e) {
				//LOGGER.error("logWrite", e);
				jsonArray.add(object.toString());
			}
		}
		logger.debug("url : "+url);
		logger.debug("ip: "+ip);
		logger.debug("infoid :  "+infoid);
		logger.debug("参数 : "+jsonArray.toString());
		logger.debug("-------------"+className+"."+methodName+"    日志写入结束  -------------------------------------------------------");
		
//		String uid =  EmptyUtil.isEmpty(SpringSecurityUtils.getCurrentUser())?"":SpringSecurityUtils.getCurrentUser().getUserId();
//		String uname =  EmptyUtil.isEmpty(SpringSecurityUtils.getCurrentUserName())?"":SpringSecurityUtils.getCurrentUserName();
//		SysLogUtils.addLog(methodName, ip, className, jsonArray.toString(),url.toString(),uid,uname,result,infoid);
		
    }
    
 
    /**
     * 管理员添加操作日志(后置通知)
     * @param joinPoint
     * @param rtv
     * @throws Throwable
     */
    @After("insertServiceCall()")
    public void insertServiceCallCalls(JoinPoint joinPoint) throws Throwable{
     
		//判断参数
		if(joinPoint.getArgs() == null){//没有参数
			return;
		}else{
			saveLog(joinPoint);
		}
	}
	
	 /**
     * 管理员修改操作日志(后置通知)
     * @param joinPoint
     * @throws Throwable
     */
	@After("updateServiceCall()")
    public void updateServiceCallCalls(JoinPoint joinPoint) throws Throwable{
		//判断参数
		if(joinPoint.getArgs() == null){//没有参数
			return;
		}else{
			saveLog(joinPoint);
		}
	}
	
	/**
     * 使用环绕通知的目的是
     * 在信息被删除前可以先查询出信息用于日志记录
     * @param joinPoint
     * @param rtv
     * @throws Throwable
     */
	@Around(value="deleteServiceCall()")
	public Object deleteServiceCallCalls(ProceedingJoinPoint pjp) throws Throwable {
		
		Object result = null;
	     //环绕通知处理方法
	     try {
	    	//执行删除操作
	    	result = pjp.proceed();
	    	saveLog(pjp);
	     }
	     catch(Exception ex) {
	        ex.printStackTrace();
	     }
	     return result;
	}
	
	 
}
