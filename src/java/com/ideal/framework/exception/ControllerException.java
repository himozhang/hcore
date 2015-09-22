/**
 * ServiceException.java 2008-4-23 上午08:46:43
 */
package com.ideal.framework.exception;

/**
 *  Controller层异常基类
 * @author himo.zhang
 */

public class ControllerException extends Exception {

	/**
	 * 
	 */
	public ControllerException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public ControllerException(String arg0) {
		super(arg0);
		this.printStackTrace();
	}

	/**
	 * @param arg0
	 */
	public ControllerException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ControllerException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
