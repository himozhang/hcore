/**
 * ServiceException.java 2008-4-23 上午08:46:43
 */
package com.ideal.framework.exception;

/**
 * 业务层异常基类
 * 
 * @author himo.zhang
 */

public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 3152616724785436891L;

	public ServiceException(String frdMessage) {
		super(createFriendlyErrMsg(frdMessage));
		this.printStackTrace();
	}

	public ServiceException(Throwable throwable) {
		super(throwable);
	}

	public ServiceException(Throwable throwable, String frdMessage) {
		super(throwable);
	}

	private static String createFriendlyErrMsg(String msgBody) {
		String prefixStr = "抱歉，";
		String suffixStr = " 请稍后再试或与管理员联系！";

		StringBuffer friendlyErrMsg = new StringBuffer("");

		friendlyErrMsg.append(prefixStr);

		friendlyErrMsg.append(msgBody);

		friendlyErrMsg.append(suffixStr);

		return friendlyErrMsg.toString();
	}

}
