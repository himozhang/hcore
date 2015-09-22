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
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.ideal.framework.exception.ControllerException;
import com.ideal.framework.exception.SpringAOPAdvisorException;
import com.ideal.framework.utils.json.JsonDateValueProcessor;

/**
 * 系统Controller性能监控
 * @author himo.zhang
 * */
@Aspect
@Component
public class TimeConsumAspect {
private final Logger logger = Logger.getLogger(getClass());
	
	
	@Pointcut("execution(* com.ideal.*.*.controller.*Controller.*(..))")
	public void controllerPointcut() {}
	
	/**
	 * 针对controller层下所有接口的实现类中的方法进行检测，输出的是该方法执行的多少时间（毫秒）
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around("controllerPointcut()")
	public Object serviceProcessTime(ProceedingJoinPoint pjp) throws Throwable {
		if(logger.isDebugEnabled()){
			long time = System.currentTimeMillis();
			Object retVal = pjp.proceed();
			time = System.currentTimeMillis() - time;
			logger.debug("此次操作消耗了["+(time)+"]毫秒！");
			return retVal;
		}
		return pjp.proceed();
	}
	
	@After("controllerPointcut()")  
    public void doAfter(JoinPoint jp){  
        if(logger.isDebugEnabled()){
            JSONArray jsonArray = new JSONArray();
 			JSONObject jsonObject = null;
 			StringBuffer url = new StringBuffer("");
			String ip = "";
			for (Object object : jp.getArgs()) {
				try {
					if(object instanceof HttpServletRequest){
						HttpServletRequest request = (HttpServletRequest)object;
						url =  request.getRequestURL();
						ip = request.getRemoteAddr();
						continue;
					}else{
						 JsonConfig jsonConfig = new JsonConfig();  
						 jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());  
						 jsonObject = JSONObject.fromObject(object, jsonConfig);  
						 jsonArray.add(jsonObject);
					}
				} catch (Exception e) {
					//logger.error("logWrite", e);
					jsonArray.add(object.toString());
				}
				
			}
			logger.debug("请求信息 ----> ip : "+ip+" url : "+url.toString());
			logger.debug("请求参数 ----> class:" + jp.getTarget().getClass().getName()+ ",method:" + jp.getSignature().getName() + ",args:"+ jsonArray.toString());
        }
    }  
	
	 
    
	@AfterThrowing( pointcut =  "controllerPointcut()", throwing = "e")
	public void errorLog(JoinPoint jp, Throwable e) throws Throwable {
		if(e!=null){  
			SpringAOPAdvisorException exceptionAdvisor = new SpringAOPAdvisorException();
			Method method = null;
			for(Method m : jp.getSignature().getDeclaringType().getMethods()){
				if(m.getName().equals(jp.getSignature().getName())){
					method = m;
					break;
				}
			}
			exceptionAdvisor.afterThrowing(method, jp.getArgs(), jp.getTarget(), new ControllerException(e.getMessage()));
            logger.error("Controller 执行异常:" + e.getMessage());  
        }  
	}
	
}
