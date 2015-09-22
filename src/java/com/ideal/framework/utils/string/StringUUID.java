package com.ideal.framework.utils.string;

import java.util.UUID;


/** 
* 2015-4-8 下午06:15:53
* author:himo
* mail:zhangyao0905@gmail.com
* descript:UUID的方式生成主键
*/ 
public class StringUUID {
	
	public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",  
        "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",  
        "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",  
        "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",  
        "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",  
        "W", "X", "Y", "Z" }; 
	
	/**
	 * @Title: generaterPrimaryKey
	 * @Description: 以UUID的方式生成主键
	 * @return String : 主键值
	 */
	public static  String getUUID() {
		
		UUID uuid = UUID.randomUUID();
		
		if (null==uuid || "".equals(uuid.toString())){
			throw new NullPointerException("uuid is null");
		}
		
		String primaryKey = String.valueOf(uuid);
		
		if (null!=primaryKey && primaryKey.contains("-")){
			primaryKey = primaryKey.replaceAll("-", "");
		}
		
		return primaryKey;
	}
	
	/**
	 * 根据传入长度生成UUID随机数
	 * @param length 需要生成的随机数长度
	 * */
	public static String generateShortUuid(int length) {  
	    StringBuffer shortBuffer = new StringBuffer();  
	    String uuid = UUID.randomUUID().toString().replace("-", "");  
	    for (int i = 0; i < length; i++) {  
	        String str = uuid.substring(i * length/2, i * length/2 + length/2);  
	        int x = Integer.parseInt(str, 16);  
	        shortBuffer.append(chars[x % 0x3E]);  
	    }  
	    return shortBuffer.toString();  
	}  
	
	// Demonstraton and self test of class
	public static void main(String args[]) {
		System.out.println(generateShortUuid(7)+"-"+generateShortUuid(3));
	}
}
