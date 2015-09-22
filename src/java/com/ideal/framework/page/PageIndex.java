package com.ideal.framework.page;

 
/** 
* @ClassName:PageIndex.java
* @CreateTime 2015-5-7 下午11:26:09
* @author:himo
* @mail:zhangyao0905@gmail.com
* @Description:分页索引
*/ 
public class PageIndex {
	private long startindex;
	private long endindex;
	
	public PageIndex(long startindex, long endindex) {
		this.startindex = startindex;
		this.endindex = endindex;
	}
	public long getStartindex() {
		return startindex;
	}
	public void setStartindex(long startindex) {
		this.startindex = startindex;
	}
	public long getEndindex() {
		return endindex;
	}
	public void setEndindex(long endindex) {
		this.endindex = endindex;
	}
	
}
