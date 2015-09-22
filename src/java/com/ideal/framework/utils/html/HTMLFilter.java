package com.ideal.framework.utils.html;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ideal.framework.utils.string.EmptyUtil;

public class HTMLFilter {
    
 
    
    /**
     * 将HTML标签转移为文本标签
     * */
    public static String htmlSpecialChars(String str) {
//    	str = str.replaceAll("&", "&amp;");
    	str = str.replaceAll("<", "&lt;");
    	str = str.replaceAll(">", "&gt;");
    	str = str.replaceAll("\"", "&quot;");
    	return str;
    }
    
    /**
     * 将特殊字符转译为HTML
     * */
    public static String charsSpecialHtml(String str) {
    	if(!EmptyUtil.isEmpty(str)){
//	    	str = str.replaceAll("&amp;", "&");
	    	str = str.replaceAll("&lt;", "<");
	    	str = str.replaceAll("&gt;", ">");
	    	str = str.replaceAll("&quot;", "\"");
//	    	str = str.replaceAll("&&nbsp;", " ");
	    	return str;
    	}
    	return "";
    }
    
    /**
     * 转译HTML中的回车和换行格式
     * */
    public static String CRLF(String htmlStr) {
    	if(!EmptyUtil.isEmpty(htmlStr)){
	    	String newString=htmlStr;  
	        Pattern CRLF = Pattern.compile("(\r\n\t)");  
	        Matcher m = CRLF.matcher(htmlStr);  
	        if (m.find()) {  
	          newString = m.replaceAll("<br/>");
	//          newString=newString.replaceAll(" ", "&nbsp;");
	        }  
	        Pattern CRLF2 = Pattern.compile("(\r\n)");  
	        Matcher m2 = CRLF2.matcher(newString);  
	        if (m2.find()) {  
	        	newString = m2.replaceAll(" ");
	//          newString=newString.replaceAll(" ", "&nbsp;");
	        }  
	//        newString = CRTab(newString);
	        return newString; 
    	}
    	return "";
    }
    
    
    
    /**
     * 转译HTML中的回车和换行格式
     * */
    public static String CRTab(String htmlStr) {
    	String newString=htmlStr;  
    	Pattern CRLF = Pattern.compile("(\t)");  
    	Matcher m = CRLF.matcher(htmlStr);  
    	if (m.find()) {  
    		newString = m.replaceAll("&nbsp;&nbsp;&nbsp;");
//          newString=newString.replaceAll(" ", "&nbsp;");
    	}  
    	return newString; 
    }
    
    /**
     * 清除HTML标签
     * */
    public static String Html2Text(String inputString) {
		String htmlStr = inputString; // 含html标签的字符串
		String textStr = "";
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;

		java.util.regex.Pattern p_html1;
		java.util.regex.Matcher m_html1;

		try {
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
			// }
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
			// }
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
			String regEx_html1 = "<[^>]+";
			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签

			p_html1 = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);
			m_html1 = p_html1.matcher(htmlStr);
			htmlStr = m_html1.replaceAll(""); // 过滤html标签

			textStr = htmlStr;

		} catch (Exception e) {
			System.err.println("Html2Text: " + e.getMessage());
		}

		return textStr;// 返回文本字符串
	}
    
    
    public static void main(String[] args) {
    	String a=htmlSpecialChars("<span style='font-size: 18px;'>一80后夫妻有了个可爱的宝宝，<img src='cc'>老婆每天都很用心的教导孩子叫“爸爸”老公大受感动，认为太太真好，先教孩子叫爸爸，而不是叫妈妈，觉得真幸福。在一个寒冬深夜，孩子哭闹不休</span>");
    	System.out.println(a);
    	a = charsSpecialHtml(a);
    	System.out.println(a);
    	
	}
}