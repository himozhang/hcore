package com.ideal.framework.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;
  
public class SpringMVCAllInterceptor implements WebRequestInterceptor {  
    
	/** 日志工具类 */
	protected  Logger logger = LoggerFactory.getLogger(getClass());
	
    /** 
     * 在请求处理之前执行，该方法主要是用于准备资源数据的，然后可以把它们当做请求属性放到WebRequest中 
     */  
    @Override  
    public void preHandle(WebRequest request) throws Exception {  
//    	logger.debug("AllInterceptor...............................");  
    }  
    
    
    /** 
     * 判断是否为Ajax请求 
     * 
     * @param request HttpServletRequest 
     * @return 是true, 否false 
     */  
    public static boolean isAjaxRequest(HttpServletRequest request) {  
        return request.getRequestURI().startsWith("/api");  
//        String requestType = request.getHeader("X-Requested-With");  
//        return requestType != null && requestType.equals("XMLHttpRequest");  
    }  
  
    /** 
     * 该方法将在Controller执行之后，返回视图之前执行，ModelMap表示请求Controller处理之后返回的Model对象，所以可以在 
     * 这个方法中修改ModelMap的属性，从而达到改变返回的模型的效果。 
     */  
    @Override  
    public void postHandle(WebRequest request, ModelMap map) throws Exception {  
        // TODO Auto-generated method stub  
    }  
  
    /** 
     * 该方法将在整个请求完成之后，也就是说在视图渲染之后进行调用，主要用于进行一些资源的释放 
     */  
    @Override  
    public void afterCompletion(WebRequest request, Exception exception)  
    throws Exception {  
        // TODO Auto-generated method stub  
//        logger.debug(exception + "-=-=--=--=-=-=-=-=-=-=-=-==-=--=-=-=-=");  
    }  
      
}  