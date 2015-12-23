package com.lin.shiro.realm;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.lin.shiro.common.ObjectUtils;
import com.lin.shiro.common.Principal;
import com.lin.shiro.common.StringUtils;
import com.lin.shiro.entity.permission.Permission;
import com.lin.shiro.entity.user.User;
import com.lin.shiro.menu.Menu;
import com.lin.shiro.service.permission.PermissionService;
import com.lin.shiro.service.user.UserService;

/**
 * 
 * desc: 身份认证及授权
 * 
 * @author xuelin
 * @date Dec 17, 2015
 */
public class ShiroAuthRealm extends AuthorizingRealm {
	private UserService userService;
	private PermissionService permissionService;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}
	
	/**
	 * 
	 * desc: 授权
	 * 
	 * @param principals
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Principal principal = (Principal) principals.getPrimaryPrincipal();
		return getAuthorizationInfo(principal);
	}
	
	private AuthorizationInfo getAuthorizationInfo(Principal principal) {
		// 获取用户权限信息
		List<Permission> permissions = permissionService.findByUser(principal.getUserId());
		List<String> permissionAlias = new ArrayList<String>(permissions.size());
		for (Permission permission : permissions) {
			permissionAlias.add(permission.getAlias());
		}
		
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.addStringPermissions(permissionAlias);
		
		return authorizationInfo;
	}
	
	/**
	 * 
	 * desc: 身份认证
	 * 
	 * @param token
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = token.getPrincipal().toString();
		Object password = token.getCredentials();
		
		if (!StringUtils.hasText(username) || ObjectUtils.isEmpty(password)) {
			return null;
		}
		
		// 获取用户信息
		User user = userService.findByUername(username);
		Principal principal = getPrincipal(user);
		
		return new SimpleAuthenticationInfo(principal, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), getName());
	}
	
	private Principal getPrincipal(User user) {
		
		// 创建身份信息
		Principal principal = new Principal(user.getId(), user.getUsername());
		principal.setMenus(getMenus(user));
		
		return principal;
	}
	
	// 只有两级
	private List<Menu> getMenus(User user){
		// 获取用户菜单
		List<Menu> menus = new ArrayList<Menu>();
		List<Permission> subMenuPermissions = new ArrayList<Permission>();
		List<Permission> menuPermissions = permissionService.findMenuByUser(user.getId());
		for(Permission menupermission : menuPermissions){
			Integer parentId = menupermission.getParentId();
			if(null == parentId){
				// 一级菜单
				Menu menu = new Menu(menupermission.getId(), parentId, menupermission.getName(), menupermission.getUrl());
				menus.add(menu);
			}else{
				// 二级菜单
				subMenuPermissions.add(menupermission);
			}
		}
		
		// 处理二级菜单
		for(Permission subMenuPermission : subMenuPermissions){
			for(Menu menu : menus){
				Integer parentId = subMenuPermission.getParentId();
				if(menu.getId().equals(parentId)){
					Menu subMenu = new Menu(subMenuPermission.getId(), parentId, subMenuPermission.getName(), subMenuPermission.getUrl());
					menu.addSubMenu(subMenu);
				}
			}
		}
		
		return menus;
	}
	
}
