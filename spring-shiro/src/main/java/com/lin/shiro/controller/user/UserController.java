package com.lin.shiro.controller.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lin.shiro.common.StringUtils;
import com.lin.shiro.controller.BaseController;
import com.lin.shiro.exception.CaptchaException;

/**
 * 
 * desc:   用户控制器
 * @author xuelin
 * @date   Dec 17, 2015
 */
@Controller
public class UserController extends BaseController {
	
	/**
	 * 
	 * desc: 认证失败才会进来
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/user/login")
	public String login(HttpServletRequest request, Map<String, String> map){
		String loginFailure = (String) request.getAttribute("shiroLoginFailure");
		
		if(StringUtils.hasText(loginFailure)){
			if(isLoginFailure(loginFailure)){
				// 用户名不存在或密码错误
				map.put("alert_msg", "帐号或密码不正确");
			}else if(CaptchaException.class.getName().equals(loginFailure)){
				// 验证码出错
				map.put("alert_msg", "验证码不正确");
			}else{
				// 未知异常
				map.put("alert_msg", "服务器出问题了，请稍候再试");
			}
		}
		
		return "login";
	}
	
	private boolean isLoginFailure(String loginException){
		return AuthenticationException.class.getName().equals(loginException) || IncorrectCredentialsException.class.getName().equals(loginException);
	}
	
}






