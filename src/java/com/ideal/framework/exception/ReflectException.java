package com.ideal.framework.exception;

public class ReflectException extends Exception {

	private static final long serialVersionUID = 2613161498707743269L;
	
	public ReflectException(){
		super();
	}
	public ReflectException(String errorMessage){
		super(errorMessage);
		this.printStackTrace();
	}
	public ReflectException(String errorMessage,Throwable cause){
		super(errorMessage,cause);
	}
}
