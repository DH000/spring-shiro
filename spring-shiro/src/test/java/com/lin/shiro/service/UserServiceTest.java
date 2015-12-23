package com.lin.shiro.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lin.shiro.entity.permission.Permission;
import com.lin.shiro.entity.user.User;
import com.lin.shiro.service.permission.PermissionService;
import com.lin.shiro.service.user.UserService;
import com.lin.shiro.type.user.SexType;

/**
 * 
 * desc:   用户测试
 * @author xuelin
 * @date   Dec 17, 2015
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/*.xml"})
public class UserServiceTest {
	@Autowired
	private UserService userService;
	@Autowired
	private PermissionService permissionService;
	
	@Test
	public void findTest(){
		userService.find(1l);
	}
	
	@Test
	public void findByUsername(){
		for(int i=0; i<100; i++){
			userService.findByUername("admin");
		}
	}
	
	@Test
	public void findPasswordByUsernameTest(){
		String pwd = userService.findPasswordByUsername("admin");
		System.out.println(pwd);
	}
	
	@Test
	public void test(){
		for(int i=0; i<100; i++){
			User user = userService.findByUername("admin");
			Permission p = permissionService.find(1);
			System.out.println(user);
			System.out.println(p);
		}
	}
	
	public static void main(String[] args) {
		Enum.valueOf(SexType.class, "All");
	}
	
}






