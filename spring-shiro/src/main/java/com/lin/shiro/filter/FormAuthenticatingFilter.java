package com.lin.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import com.lin.shiro.common.Constants;
import com.lin.shiro.common.StringUtils;
import com.lin.shiro.exception.CaptchaException;

/**
 * 
 * desc: 表单认证
 * 
 * @author xuelin
 * @date Dec 22, 2015
 */
public class FormAuthenticatingFilter extends FormAuthenticationFilter {
	/**
	 * 是否开启验证码
	 */
	public static final String DEFAULT_CAPTCHA_ENABLED = "captchaEnabled";
	private String captchaEnabledParam = DEFAULT_CAPTCHA_ENABLED;
	
	/**
	 * 验证码字段
	 */
	public static final String DEFAULT_CAPTCHA_NAME = "captcha";
	private String captchaNameParam = DEFAULT_CAPTCHA_NAME;
	
	public void setCaptchaEnabledParam(String captchaEnabledParam) {
		this.captchaEnabledParam = captchaEnabledParam;
	}
	
	public void setCaptchaNameParam(String captchaNameParam) {
		this.captchaNameParam = captchaNameParam;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		// 1、验证码校验
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		try{
			captchaValidate(req, resp);
		}catch(CaptchaException e){
			e.printStackTrace();
			
			// 设置验证码错误属性
			request.setAttribute(this.getFailureKeyAttribute(), e.getClass().getName());
			
			// 拒绝访问 无需再做认证
			return true;
		}
		
		// 2、认证
		boolean authResult = super.onAccessDenied(request, response);
		
		// 3、如果认证失败 激活验证码验证
		String loginFailure = (String) request.getAttribute(getFailureKeyAttribute());
		if(StringUtils.hasText(loginFailure)){
			request.setAttribute(this.captchaEnabledParam, true);
			req.getSession().setAttribute(this.captchaEnabledParam, true);
		}
		
		return authResult;
	}
	
	private void captchaValidate(HttpServletRequest request, HttpServletResponse response) throws CaptchaException {
		if (needValidate(request, response)) {
			return;
		}
		
		// 验证码校验
		HttpSession session = request.getSession();
		String webCode = request.getParameter(this.captchaNameParam);
		String code = getCaptchaCode(session);
		if(!code.equals(webCode)){
			throw new CaptchaException("验证码不正确");
		}
	}
	
	private boolean needValidate(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		Boolean captchaEnabled = (Boolean) session.getAttribute(this.captchaEnabledParam);
		captchaEnabled = null == captchaEnabled ? Boolean.FALSE : captchaEnabled;
		return !isLoginRequest(request, response) || !captchaEnabled || !isLoginSubmission(request, response);
	}
	
	private String getCaptchaCode(HttpSession session){
		if((boolean)session.getAttribute(Constants.CAPTCHA_STATUS_ATTR)){
			throw new CaptchaException("验证码已使用过");
		}
		
		String code = (String) session.getAttribute(Constants.CAPTCHA_VALUE_ATTR);
		if(!StringUtils.hasText(code)){
			throw new CaptchaException("验证码未生成");
		}
		
		// 标记已使用
		session.setAttribute(Constants.CAPTCHA_STATUS_ATTR, true);
		return code;
	}
	
}
