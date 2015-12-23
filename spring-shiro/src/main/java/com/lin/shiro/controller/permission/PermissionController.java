package com.lin.shiro.controller.permission;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lin.shiro.controller.BaseController;
import com.lin.shiro.entity.permission.Permission;
import com.lin.shiro.page.PageInfo;
import com.lin.shiro.service.permission.PermissionService;

/**
 * 
 * desc:   权限
 * @author xuelin
 * @date   Dec 21, 2015
 */
@Controller
public class PermissionController extends BaseController {
	
	@Autowired
	private PermissionService permissionService;
	
	@RequestMapping("/perm/list")
	@RequiresPermissions("perm:list")
	public String list(@RequestParam(required = false) Integer page, Map<String, Object> map){
		int currPage = null == page ? PageInfo.DEFAULT_PAGE : page;
		PageInfo<Permission> pageInfo = permissionService.findForPage(currPage, PageInfo.DEFAULT_SIZE);
		map.put("pageInfo", pageInfo);
		return "perm/list";
	}
	
}
