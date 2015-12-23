package com.lin.shiro.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.lin.shiro.entity.permission.Permission;
import com.lin.shiro.service.permission.PermissionService;
import com.lin.shiro.type.permission.PermissionType;

/**
 * 
 * desc:   权限测试
 * @author xuelin
 * @date   Dec 17, 2015
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/*.xml"})
public class PermissionServiceTest {
	@Autowired
	private PermissionService permissionService;
	
	@Test
	public void findTest(){
		Permission permission = permissionService.find(1);
		System.out.println(permission);
	}
	
	@Test
	public void findByUserTest(){
		List<Permission> list = permissionService.findByUser(1l);
		System.out.println(list);
	}
	
	@Test
	public void findMenuByUser(){
		List<Permission> menus = permissionService.findMenuByUser(1l);
		System.out.println(menus);
	}
	
	@Test
	public void countTest(){
		System.out.println(permissionService.count());
		System.out.println(permissionService.count(1l));
		System.out.println(permissionService.count(1l, PermissionType.MENU));
	}
	
	@Test
	public void findForPageTest(){
		System.out.println(JSON.toJSONString(permissionService.findForPage(1, (short)10)));
		System.out.println(JSON.toJSONString(permissionService.findForPage(1l, 1, (short)10)));
		System.out.println(JSON.toJSONString(permissionService.findForPage(1l, PermissionType.MENU, 1, (short)10)));
	}
	
}






