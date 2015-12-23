package com.lin.shiro.exception;

/**
 * 
 * desc:   验证码异常
 * @author xuelin
 * @date   Dec 22, 2015
 */
public class CaptchaException extends RuntimeException {

	/**
	 * desc: TODO
	 */
	private static final long serialVersionUID = -3207962735370425487L;
	
	public CaptchaException(){
		super();
	}
	
	public CaptchaException(String message){
		super(message);
	}
	
}
