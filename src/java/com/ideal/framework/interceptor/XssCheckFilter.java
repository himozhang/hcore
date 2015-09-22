package com.ideal.framework.interceptor;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JS注入过滤
 * */
public class XssCheckFilter{
	private static Logger logger = LoggerFactory.getLogger(XssCheckFilter.class);
	private FilterConfig config;
	private static String errorPath;// 出错跳转的目的地
	private static String[] excludePaths;// 不进行拦截的url
	private String characterEncoding = null;
	private static String[] safeless = {
			"<script", // 需要拦截的JS字符关键字
			"</script", "<iframe", "</iframe", "<frame", "</frame",
			"set-cookie", "%3cscript", "%3c/script", "%3ciframe", "%3c/iframe",
			"%3cframe", "%3c/frame", "src=\"javascript:", "<body", "</body",
			"%3cbody", "%3c/body", "alert",
			/*
			 * "&", "<", ">",
			 */
			"</", "/>", "%3c", "%3e", "%3c/", "/%3e",
			"<script>alert(document.cookie)</script>", "onmouseover", "dprompt" };

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain filterChain) throws IOException, ServletException {
		Enumeration params = req.getParameterNames();
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		/* 设置请求编码格式 */
		req.setCharacterEncoding("UTF-8");
		boolean isSafe = true;
		String requestUrl = request.getRequestURI();
		// 当前请求，用于筛选不用过滤的url
		String url = request.getServletPath();
		if (isSafe(requestUrl)) {
			requestUrl = requestUrl.substring(requestUrl.indexOf("/"));
			if (!excludeUrl(url)) {
				while (params.hasMoreElements()) {
					String cache = req.getParameter((String) params
							.nextElement());
					if (StringUtils.isNotBlank(cache)) {
						if (!isSafe(cache)) {
							isSafe = false;
							break;
						}
					}
				}
			}
		} else {
			isSafe = false;
		}

		if (!isSafe) {
			request.setAttribute("err", "您输入的参数有非法字符，请输入正确的参数！");
			request.getRequestDispatcher(errorPath).forward(request, response);
			return;
		}
		filterChain.doFilter(req, resp);
	}

	private static boolean isSafe(String str) {
		if (StringUtils.isNotBlank(str)) {
			for (String s : safeless) {
				if (str.toLowerCase().contains(s)) {
					logger.error("检测到非法字符：{}" , s );
					return false;
				}
			}
		}
		return true;
	}

	private boolean excludeUrl(String url) {
		if (excludePaths != null && excludePaths.length > 0) {
			for (String path : excludePaths) {
				if (url.equals(path)) {
					return true;
				}
			}
		}
		return false;
	}

	public void destroy() {
	}

	public void init(FilterConfig config) throws ServletException {
		this.config = config;
		errorPath = config.getInitParameter("errorPath");
		String excludePath = config.getInitParameter("excludePaths");
		if (StringUtils.isNotBlank(excludePath)) {
			excludePaths = excludePath.split(",");
		}
//		characterEncoding = Validation.isEmpty(config
//				.getInitParameter("characterEncoding")) ? "UTF-8" : config
//				.getInitParameter("characterEncoding");
	}
}