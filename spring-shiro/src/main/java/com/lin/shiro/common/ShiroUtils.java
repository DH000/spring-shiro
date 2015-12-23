package com.lin.shiro.common;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * 
 * desc:   shiro工具
 * @author xuelin
 * @date   Dec 20, 2015
 */
public final class ShiroUtils {
	
	public static Subject getSubject(){
		return SecurityUtils.getSubject();
	}
	
	public static Principal getPrincipal(){
		return (Principal) getSubject().getPrincipal();
	}
	
}
