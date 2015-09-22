package com.ideal.framework.exception;

public class LDAPOperationException extends RuntimeException{
	private static final long serialVersionUID = 1564358271065640851L;
	
	public LDAPOperationException(){
		super();
	}
	public LDAPOperationException(String errorMessage){
		super(errorMessage);
		this.printStackTrace();
	}
	public LDAPOperationException(String errorMessage,Throwable cause){
		super(errorMessage,cause);
	}
}
