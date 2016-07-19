package com.schoolbus.service;

import com.schoolbus.entity.User;

public interface UserService {
	public String login(User user);
	public String logout(User user);
	public String register(User user);
	public String getUsers(User user,int page,int rows);
	public String updateUser(User user);
	public String removeUser(User user);
}
