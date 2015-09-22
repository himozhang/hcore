package com.ideal.framework.i18n;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.ideal.framework.utils.string.EmptyUtil;


/**
 * 针对国际化的工具类，根据浏览器的语言信息来获取相应的资源
 * @author himo.zhang
 */
public class I18nUtil {


	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(I18nUtil.class);
	
	private static ApplicationContext ctx = null;
	private static String[] configs = { "i18n/applicationContext-i18n.xml" };// 国际化的配置文件存放的地方
	static {
		ctx = new ClassPathXmlApplicationContext(configs);
	}

	
	/**
	 * 获取客户客户端的语言设置 如果客户自己选择了相应的语言（改变了页面语言选项），则以客户选择的语言为准 获取后，信息放入
	 * @param request
	 */
	public static void setLocale(HttpServletRequest request) {
		logger.debug("设置国际化信息");
		Locale locale = null;
		String userLocale = request.getParameter("userLocale");

		// 如果用户没有设置自己的语言选项，获取sesion中的语言，否则获取客户端的语言
		if (EmptyUtil.isEmpty(userLocale)) {
			Object sessionLocale = request.getSession().getAttribute("userLocale");
			locale = EmptyUtil.isEmpty(sessionLocale) ? request.getLocale() : (Locale) sessionLocale;
			request.getSession().setAttribute("userLocale", locale);
			request.getSession().setAttribute("locale", locale);
			request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale);
		} else {
			//en_us
			String[] userLocales = userLocale.split("_");
			try {
				locale = new Locale(userLocales[0], userLocales[1]);
			} catch (Exception e) {// 若输入了错误的地址，进行容错处理
				locale = Locale.CHINA;
			}
			request.getSession().setAttribute("userLocale", locale);
			request.getSession().setAttribute("locale", locale);
			request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale);
		}
	}
	
	
	public static String getMessage(String code) {
		return ctx.getMessage(code, null, Locale.CHINA);
	}
	
	public static String getMessage(String code,Locale locale) {
		return ctx.getMessage(code, null,locale);
	}
	
	/**
	 * 读取国际化的资源
	 * 
	 * @param code
	 *            资源的代码
	 * @param request
	 *            rquest对象，系统会提取里面的locale对象
	 * @return
	 */
	public static String getMessage(String code, HttpServletRequest request) {
//		Locale locale = (Locale) request.getSession().getAttribute(
//				SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
		 Locale locale=(Locale) request.getSession().getAttribute("userLocale");
		if(EmptyUtil.isEmpty(locale)){
			locale=Locale.CHINA;
			request.setAttribute("userLocale",locale.toString());
			I18nUtil.setLocale(request);
		}
		try {
			return ctx.getMessage(code, null, locale);
		} catch (Exception e) {
			logger.debug("KEY=["+code+"]在资源文件中未找到对应的值！");
			return null;
		}
	}

	public static void main(String[] args) {
		// getMessage("dd");
		// System.out.println("结束了");
		Locale locale = new Locale("en_US");
		System.out.println(locale);
		System.out.println(I18nUtil.getMessage("system.titleName",locale));
	}
}
