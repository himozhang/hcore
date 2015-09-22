package com.ideal.framework.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.ideal.framework.utils.string.StringUUID;


/** 
* 2015-4-8 下午05:46:04
* author:himo
* mail:zhangyao0905@gmail.com
* descript:LOG4格式化对象，并根据配置方法保存到数据库
*/ 
public class Log4JResFilter implements Filter {
	
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(Log4JResFilter.class);
    
    private final static String DEFAULT_USERID= "none";   
    
    @Override
    public void destroy() {
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        //System.out.println("进入过滤器");
        HttpServletRequest req=(HttpServletRequest)request;  
        MDC.put("Id", StringUUID.getUUID());  
        MDC.put("Ip", req.getRemoteAddr());  
//        OperatorDetails  operatorDetails = SpringSecurityUtils.getCurrentUser();
//        OperatorDetails  operatorDetails = (OperatorDetails)((HttpServletRequest) request).getSession().getAttribute(SysConstants.SESSION_CURRENT_USER);
		
//        if (EmptyUtil.isEmpty(operatorDetails)){  
//            MDC.put("userId",DEFAULT_USERID);
//            MDC.put("userRole",DEFAULT_USERID);  
//        }else{  
//            //用户的类型
//            MDC.put("userId", operatorDetails.getUserId());  
//            MDC.put("userName", operatorDetails.getRealName());  
//            MDC.put("userRole", operatorDetails.getRoleNamesStr());  
//        }  
       chain.doFilter(request, response);  
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }

}