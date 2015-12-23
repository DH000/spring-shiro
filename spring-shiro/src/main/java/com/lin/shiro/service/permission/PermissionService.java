package com.lin.shiro.service.permission;

import java.util.List;

import com.lin.shiro.entity.permission.Permission;
import com.lin.shiro.page.PageInfo;
import com.lin.shiro.type.permission.PermissionType;

/**
 * 
 * desc:   权限
 * @author xuelin
 * @date   Dec 18, 2015
 */
public interface PermissionService {
	
	public Permission find(Integer id);
	
	public List<Permission> findByUser(Long userId);
	
	public List<Permission> findMenuByUser(Long userId);
	
	public int count();
	
	public int count(Long userId);
	
	public int count(Long userId, PermissionType type);
	
	public PageInfo<Permission> findForPage(int currPage, short pageSize);
	
	public PageInfo<Permission> findForPage(Long userId, int currPage, short pageSize);
	
	public PageInfo<Permission> findForPage(Long userId, PermissionType type, int currPage, short pageSize);
	
}
