package com.schoolbus.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionContext;
import com.schoolbus.dao.Page;
import com.schoolbus.dao.UserDao;
import com.schoolbus.entity.User;
import com.schoolbus.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	private Log logger = LogFactory.getLog(UserServiceImpl.class);
	@Resource
	private UserDao userDao;
	private String resultStr;
	private Gson gson = new Gson();
	
	@Override
	public String login(User user) {
		String account = user.getAccount();
		String password = user.getPassword();
		User resultUser = userDao.selectByAccount(account);
		if(resultUser == null){
			return "{\"code\":0,\"msg\":\"账号不存在\"}";
		}else{
			if(resultUser.getPassword().equals(password)){
				int userType = resultUser.getUserType();
				if(userType == 1){
					Map<String, Object> session = ActionContext.getContext().getSession();
					session.put("userName", resultUser.getAccount());
					logger.debug("ssssss");
				}
				resultUser.setState(1);
				userDao.updateUser(resultUser);
				String str = gson.toJson(resultUser);
				return "{\"code\":1,\"msg\":\"登陆成功\",\"user\":"+str+"}";
			}else{
				return "{\"code\":0,\"msg\":\"密码错误\"}";
			}
		}
	}

	@Override
	public String logout(User user) {
		String account = user.getAccount();
		User resultUser = userDao.selectByAccount(account);
		if(resultUser != null){
			resultUser.setState(0);
			userDao.updateUser(resultUser);
			return "{\"code\":1,\"msg\":\"登出成功\"}";
		}else{
			return "{\"code\":0,\"msg\":\"登出异常，请稍后重试\"}";
		}
	}

	@Override
	public String register(User user) {
		String account = user.getAccount();
		User resultUser = userDao.selectByAccount(account);
		if(resultUser == null){
			user.setUserType(3);
			user.setCreateTime(new Date());
			userDao.saveUser(user);
			return "{\"code\":1,\"msg\":\"注册成功\"}";
		}else{
			return "{\"code\":0,\"msg\":\"用户名已存在\"}";
		}
	}
	
	@Override
	public String getUsers(User user,int page,int rows) {
		int start = (page - 1)*rows;
		int size = rows;		
		Page<User> list = userDao.selectUserByPage(user,start,size);
		if(list != null){
			if(list.getList() != null){
				ArrayList<User> users = list.getList();
				logger.debug(users.get(0).getAccount());
				String str = gson.toJson(users);
				resultStr = "{\"total\":"+list.getTotalCount()+",\"rows\":"+str+"}";
			}
		}else{
			resultStr = "{\"total\":"+0+",\"rows\":[]}";
		}
		return resultStr;
	}
	
	@Override
	public String updateUser(User user) {
		User u = userDao.selectByAccount(user.getAccount());
		if(u == null){
			user.setState(0);
			user.setAppointmentLine(0);
			user.setCreateTime(new Date());
			userDao.saveUser(user);
		}else{	
			u.setUsername(user.getUsername());
			u.setEmail(user.getEmail());
			u.setPhone(user.getPhone());
			if(!user.getPassword().equals("")){
				u.setPassword(user.getPassword());
			}
			userDao.updateUser(u);
		}
		
		return null;
	}
	@Override
	public String removeUser(User user) {
		int i = userDao.deleteUser(user.getId());
		if(i == 1){
			resultStr = "{\"success\":true}";
		}else{
			resultStr = "{\"success\":false}";
		}
		return resultStr;
	}

}
