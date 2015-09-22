package com.ideal.framework.basecore.baseController;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.ideal.framework.constants.Charset;
import com.ideal.framework.constants.ContentType;
import com.ideal.framework.exception.ControllerException;
import com.ideal.framework.page.PageView;
import com.ideal.framework.utils.string.EmptyUtil;

 

/**
 * 基类Controller
 * */
public abstract class BaseController{
	
	/** 日志工具类 */
	public  Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * /common/
	 * */
	public  String CommonPath = "/common/";
	/**
	 * /WEB-INF/content/
	 * */
	public  String ContentPath = "/WEB-INF/content/";
	 
	
	public static final String UNKNOWN = "unknown";
	
	 
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;

	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request,
			HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.session = request.getSession();
	}
	
	/**
	 * @Description: 处理页面参数序列化后数据类型的问题
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder){
		SimpleDateFormat dataSdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dataTimesdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dataSdf, true));
		binder.registerCustomEditor(Timestamp.class, new CustomDateEditor(dataTimesdf, true));
		binder.registerCustomEditor(List.class, new CustomCollectionEditor(List.class, true));
	}
	
	
	/**
	 * 获得应用域名
	 * @return  http://localhost:8080/appName/
	 * */
	public String getAppDomain(HttpServletRequest request){
		String domain = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
		return domain;
	}
	
	/**
	 * 获得排序SQL
	 * @param  orderBys 自定义排序SQL，可为空
	 * @param  request 获得前台传入的排序因子及排序规则
	 * @return  default : sort asc,modify_time desc
	 * */
	public String getOrderBy(HttpServletRequest request,String orderBys){
		
		if(!EmptyUtil.isEmpty(request)){
			//jggrid 排序列
			String sidx = request.getParameter("sidx");
			//jggrid 排序顺序
			String sord = request.getParameter("sord");
			if(!EmptyUtil.isEmpty(sidx)){
				orderBys = sidx+" "+sord+","+orderBys;
			}
		}
		return orderBys;
	}
	
	 
	/**
     * 将request中的所有参数设置到entityClass类型的对象上
     * @param entityClass
     * @param request
     * @return Object
     */
    public static Object copyParam(Class entityClass,HttpServletRequest request){
         
        try {
            Object entity = entityClass.newInstance();
             
            //把请求中的参数取出
            Map allParams = request.getParameterMap();
            Set entries = allParams.entrySet();
            for (Iterator iterator = entries.iterator(); iterator.hasNext();) {
                Map.Entry entry = (Map.Entry) iterator.next();
                String name = (String)entry.getKey();
                Object[] value = (Object[])entry.getValue();
                 
                if(value != null){
                    if(value.length == 1){
                        BeanUtils.copyProperty(entity, name, value[0]);
                    }else{
                        BeanUtils.copyProperty(entity, name, value);
                    }
                }
            }
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
        }
         
        return null;
    }
	
 
	
	
	@ExceptionHandler
    public String exception(HttpServletRequest request, Exception ex) {
        String resultViewName = "errors/500";

        logger.debug("come in @ExceptionHandler.......");
        
        // 记录日志
        logger.error(ex.getMessage(), ex);

        // 根据不同错误转向不同页面
        if (ex instanceof ControllerException) {
            resultViewName = "errors/500";
        } else {
            // 异常转换
            ex = new Exception("系统太累了，需要休息!");
        }
        request.setAttribute("ex", ex);
        String xRequestedWith = request.getHeader("X-Requested-With");
        if (!EmptyUtil.isEmpty(xRequestedWith)) {
        	logger.debug("ajax Exception : "+ex.getMessage());
            // ajax请求
            resultViewName = "errors/ajax-error";

        }
        return resultViewName;
    }
	
	/**
	 * 配置Page对象分页大小和当前页
	 * */
	public PageView pageConfig(PageView pageView,HttpServletRequest request){
		
		String page  = request.getParameter("page");//当前页
		String rows  = request.getParameter("rows");//分页大小
		
		//设置分页信息
		if (!EmptyUtil.isEmpty(page)) {
			pageView.setPageNow(Integer.parseInt(page));
		}
		if (!EmptyUtil.isEmpty(rows)) {
			pageView.setPageSize(Integer.parseInt(rows));//分页大小
		}
		return pageView;
	}
	
	/**
	 * 向页面输入JSON对象
	 * @param response
	 * @param contentType 为null时 : ContentType.JSON-> text/json 
	 * @param content 输出内容
	 * */
	protected void responseOutPut(HttpServletResponse response, String contentType,String content){
		String charset = Charset.UTF_8;
		if(EmptyUtil.isEmpty(contentType)){
			contentType = ContentType.JSON;
		}
		// 设置字符串输出的内容类型
		response.setContentType(contentType +(";charset=" + charset));
		response.setCharacterEncoding(charset);
		if (content != null) {
			logger.debug(" jsonpOutput content : "+content);
			try {
				response.getWriter().write(content);
				response.flushBuffer();
			} catch (IOException e) {
				new ControllerException("response输出失败！");
			}
			
		}
	}
}
