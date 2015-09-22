package com.ideal.framework.utils.string;

import com.ideal.framework.constants.JDBCConstants;



/**
 * 正则匹配
 * himo.zhang
 * 2015-03-28
 * */
public class PatternUtils {

	 	/**
	 	 * @param String pattern 正则表达式
	 	 * @param String str 带匹配的字符串
	 	 * */
	   public static boolean wildMatch(String pattern, String str) {
	        pattern = toJavaPattern(pattern);
	        return java.util.regex.Pattern.matches(pattern, str);
	    }
	   
	   /**
	     * 将/*格式化为（/+）*匹配所有 结尾有“/”和没有“/”
	 	 * @param String pattern 正则表达式
	 	 * @param String str 带匹配的字符串
	 	 * */
	   public static boolean wildMatchForMat(String pattern, String str) {
		    if(pattern.endsWith("/*")){
	        	 pattern = pattern.substring(0, pattern.indexOf("/*"))+"(/+)*";
	        	 return java.util.regex.Pattern.matches(pattern, str);
	        }else{
	        	return wildMatch(pattern,str);
	        }
	    }

	    private static String toJavaPattern(String pattern) {
	        String result = "^";
	        char metachar[] = { '$', '^', '[', ']', '(', ')', '{', '|', '*', '+', '?', '.' , '\\' };
	        for (int i = 0; i < pattern.length(); i++) {
	            char ch = pattern.charAt(i);
	            boolean isMeta = false;
	            for (int j = 0; j < metachar.length; j++) {
	                if (ch == metachar[j]) {
	                    result += "." + ch;
	                    isMeta = true;
	                    break;
	                }
	            }
	            if (!isMeta) {
	                if (ch == '*') {
	                    result += ".*";
	                } else {
	                    result += ch;
	                }

	            }
	        }
	        result += "$";
	        return result;
	    }
	    
	    public static boolean isImg(String filename){
	    	String ALLOW_ACRIOINS = "*.[jpg|bmp|png|tif|jpeg]";
	    	test(ALLOW_ACRIOINS,filename);
	    	return false;
	    }
	    
	    public static void main(String[] args) {
	    	String ALLOW_ACRIOINS = "*.[jpg|bmp|png|tif|jpeg|JPG|BMP|PNG|TIF|JPEG]";
	    	test(ALLOW_ACRIOINS,"/ricore/upload/temp/2015/5/25/000c016d34ff41e245b69c67f22c83ff_20150525_190954.JPG");
//	    	 String str = "/log/syslog/";
//	         String pattern ="toto*";
//	         if(pattern.endsWith("/*")){
//	        	 pattern = pattern.substring(0, pattern.indexOf("/*"))+"(/+)*";
//	         }
//	         System.out.println(java.util.regex.Pattern.matches(pattern, str));
//	         test(pattern, str);	
	          test("*MYSQL*", "getPageViewByMapperHelperSaveMYSQL");	
	          test("/log/syslog/*", "/log/syslog/");	
	          test("/log/syslog*", "/log/syslog");	
	          test("/menu/delete", "/menu/update");	
	          test("/menu/update", "/menu/update");	
	          test("toto*", "toto.java");
	          test("12345", "1234");
	          test("1234", "12345");
	          test("*f", "");
	          test("***", "toto");
	          test("*.java", "toto.");
	          test("*.java", "toto.jav");
	          test("*.java", "toto.java");
	          test("abc*", "");
	          test("a*c", "abbbbbccccc");
	          test("abc*xyz", "abcxxxyz");
	          test("*xyz", "abcxxxyz");
	          test("abc**xyz", "abcxxxyz");
	          test("abc**x", "abcxxx");
	          test("*a*b*c**x", "aaabcxxx");
	          test("abc*x*yz", "abcxxxyz");
	          test("abc*x*yz*", "abcxxxyz");
	          test("a*b*c*x*yf*z*", "aabbccxxxeeyffz");
	          test("a*b*c*x*yf*zze", "aabbccxxxeeyffz");
	          test("a*b*c*x*yf*ze", "aabbccxxxeeyffz");
	          test("a*b*c*x*yf*ze", "aabbccxxxeeyfze");
	          test("*LogServerInterface*.java", "_LogServerInterfaceImpl.java");
	          test("abc*xyz", "abcxyxyz");
	    }

	    private static void test(String pattern, String str) {
	        System.out.println(pattern+" " + str + " =>> " + wildMatch(pattern, str));        
	    }
	    
}
