package com.ideal.framework.page;

/** 
* @ClassName:WebTool.java
* @CreateTime 2015-5-7 下午11:27:45
* @author:himo
* @mail:zhangyao0905@gmail.com
* @Description:主要用于显示页码
*/ 
public class WebTool {
	
	/**
	 * 显示页码
	 * @param pagecode 要获得记录的开始索引　即　开始页码
	 * @param pageNow 当前页
	 * @param pageCount 总页数
	 * @return PageIndex
	 * */
  public static PageIndex getPageIndex(long pagecode, int pageNow, long pageCount){
		long startpage = pageNow-(pagecode%2==0? pagecode/2-1 : pagecode/2);
		long endpage = pageNow+pagecode/2;
		if(startpage<1){
			startpage = 1;
			if(pageCount>=pagecode) endpage = pagecode;
			else endpage = pageCount;
		}
		if(endpage>pageCount){
			endpage = pageCount;
			if((endpage-pagecode)>0) startpage = endpage-pagecode+1;
			else startpage = 1;
		}
		return new PageIndex(startpage, endpage);		
  }
}
