/**
 * DaoException.java 2008-4-23 上午08:46:10
 */
package com.ideal.framework.exception;

/**
 * Dao层异常基类
 * @author songzh@yeah.net 2008-4-23 上午08:46:10
 */
public class DaoException extends Exception { 

	/**
	 * 
	 */
	public DaoException() {
		
	}

	/**
	 * @param arg0
	 */
	public DaoException(String arg0) {
		super(arg0);
		this.printStackTrace();
	}

	/**
	 * @param arg0
	 */
	public DaoException(Throwable arg0) {
		super(arg0);
		
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public DaoException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		
	}

}
