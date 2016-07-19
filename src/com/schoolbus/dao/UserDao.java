package com.schoolbus.dao;

import java.util.ArrayList;

import com.schoolbus.entity.User;

public interface UserDao {
	public Page<User> selectUserByPage(User user,int start,int size);
	public User selectUserById(int id);
	public void saveUser(User user);
	public void updateUser(User user);
	public int deleteUser(int id);
	public User selectByAccount(String account);
	public ArrayList<User> selectUser(User user);
}
