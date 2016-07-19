package com.schoolbus.controller;

import java.util.Map;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class LoginInterceptor extends AbstractInterceptor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Object action = invocation.getAction();
		if(action instanceof ManagerAction){
			Map<String, Object> session = invocation.getInvocationContext().getSession();  
			String userName = (String) session.get("userName");  
	        if ("".equals(userName) || null == userName) {  
	            return "login_page";  
	        } else {  
	            return invocation.invoke();  
	        }  
		}else{
			return invocation.invoke(); 
		}
	}

}
