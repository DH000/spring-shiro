package com.lin.shiro.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ModelAttribute;

import com.lin.shiro.common.Constants;
import com.lin.shiro.common.ObjectUtils;
import com.lin.shiro.common.Principal;
import com.lin.shiro.common.ShiroUtils;
import com.lin.shiro.common.StringUtils;
import com.lin.shiro.menu.Menu;

public class BaseController {
	
	/**
	 * 
	 * desc: 参数只有单个值取回单个值，否则取回多个值
	 * @param request
	 * @return
	 */
	public static Map<String, Object> getParameterMap(HttpServletRequest request){
		Map<String, String[]> params = request.getParameterMap();
		Map<String, Object> targetMap = new HashMap<String, Object>(params.size());
		for(Entry<String, String[]> entry : params.entrySet()){
			String key = entry.getKey();
			String[] vals = entry.getValue();
			boolean isEmpty = ObjectUtils.isEmpty(vals);
			
			if(!isEmpty && vals.length > 1){
				targetMap.put(key, vals);
			}else if(!isEmpty){
				targetMap.put(key, vals[0]);
			}else{
				targetMap.put(key, null);
			}
		}
		
		return targetMap;
	}
	
	/**
	 * 
	 * desc: 获取session
	 * @param request
	 * @return
	 */
	public static HttpSession getHttpSession(HttpServletRequest request){
		return request.getSession();
	}
	
	@ModelAttribute
	public void setPrincipal(HttpServletRequest request, HttpSession session, Map<String, Object> map){
		Principal principal = ShiroUtils.getPrincipal();
		if(null == principal){
			return;
		}
		
		// 用户信息
		request.setAttribute(Constants.USERNAME, principal.getUsername());
		request.setAttribute(Constants.USER_IMG_PATH, principal.getImgPath());
		
		// 用户菜单信息
		List<Menu> menus = principal.getMenus();
		map.put("menus", menus);
		
		// 菜单高亮
		String reqUri = request.getRequestURI();
		String index = request.getParameter("index");
		if(StringUtils.hasText(index) || "/".equals(reqUri)){
			session.setAttribute(Constants.MENU_INDEX, index);
			map.put("index", index);
		}else{
			index = (String) session.getAttribute(Constants.MENU_INDEX);
			map.put("index", index);
		}
	}
	
}
