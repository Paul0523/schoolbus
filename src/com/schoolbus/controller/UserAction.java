package com.schoolbus.controller;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.schoolbus.entity.User;
import com.schoolbus.service.UserService;

@Controller
public class UserAction extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Log logger = LogFactory.getLog(UserAction.class);
	@Resource
	private UserService userService;
	private String resultStr;
	private User user;
	private int page;
	private int rows;
	
	
	public String login(){
		resultStr = userService.login(user);
		return "strResponse"; 
	}
	
	public String logout(){
		resultStr = userService.logout(user);
		return "strResponse"; 
	}
	
	public String register(){
		resultStr = userService.register(user);
		return "strResponse"; 
	}
	
	public String users(){
		resultStr = userService.getUsers(user,page,rows);
 		logger.debug(resultStr);
 		return "strResponse";
 	}
	
	public String updateUser(){
		resultStr = userService.updateUser(user);
		logger.debug(resultStr);
		return "strResponse";
	}
	public String removeUser(){
		resultStr = userService.removeUser(user);
		return "strResponse";
	}
	
	
	
	public String getResultStr() {
		return resultStr;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
}
