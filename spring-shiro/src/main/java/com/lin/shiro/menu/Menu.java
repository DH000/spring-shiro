package com.lin.shiro.menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * desc: 菜单
 * 
 * @author xuelin
 * @date Dec 20, 2015
 */
public class Menu implements Serializable {
	
	/**
	 * desc: TODO
	 */
	private static final long serialVersionUID = 6267850244246731196L;
	
	private Integer id;
	private Integer parentId;
	private String name;
	private String url;
	
	private List<Menu> subs;
	
	public Menu() {
		super();
		this.subs = new ArrayList<Menu>();
	}
	
	public Menu(Integer id, Integer parentId, String name, String url) {
		super();
		this.id = id;
		this.parentId = parentId;
		this.name = name;
		this.url = url;
		this.subs = new ArrayList<Menu>();
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getParentId() {
		return parentId;
	}
	
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public List<Menu> getSubs() {
		return subs;
	}
	
	public void setSubs(List<Menu> subs) {
		this.subs = subs;
	}
	
	public void addSubMenu(Menu menu){
		if(null != menu){
			this.subs.add(menu);
		}
	}
	
}
