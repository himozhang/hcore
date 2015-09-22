package com.ideal.framework.spring;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

  
/**
 * @ClassName: SpringContextLoaderListener
 * @Description: 自定义spring加载器，把ApplicationContext装载到SpringUtil 
 *
 */
public class SpringContextLoaderListener extends ContextLoaderListener {  
  
    public void contextInitialized(ServletContextEvent event) {  
  
        // 初始化父类 ContextLoaderListener  
        super.contextInitialized(event);  
        ServletContext context = event.getServletContext();  
        ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);  
  
        // 装载 SpringContextUtil 中的 ApplicationContext  
        new SpringContextHolder().setApplicationContext(ctx);
        ApplicationContextUtil.getInstance().setApplicationContext(ctx);
    }  
}  
