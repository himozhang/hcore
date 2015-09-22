package com.ideal.framework.spring;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;

/**
 * Spring 应用上下文工具<br />
 * 如果是web程序，则可以设置web 上下文，从而获取web应用的WebApplicationContext;
 * 否则，默认加载applicationContext.xml
 * @author songzh@yeah.net
 * @date 2008-1-29 上午11:06:32
 */
public class ApplicationContextUtil {
	
	private ApplicationContextUtil(){}
	private final static ApplicationContextUtil instance = new ApplicationContextUtil();

	//Spring ApplicationContext
	private ApplicationContext applicationContext;
	//request
	private HttpServletRequest request;
	//session
	private HttpSession session;
	//sc
	private ServletContext servletContext;

	/**
	 * 获取ApplicationContextUtil单例
	 * @return
	 */
	public static ApplicationContextUtil getInstance(){
		return instance;
	}

	 

	/**
     * 根据class获得bean.
     * 
     * @param clz
     *            Class
     * @return T
     */
    public <T> T getBean(Class<T> clz) {
        return applicationContext.getBean(clz);
    }
	
	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	/**
	 * @return the applicationContext
	 */
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * @param applicationContext the applicationContext to set
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
}
