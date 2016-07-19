package com.schoolbus.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter {

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		System.out.println("ddljad");
		if(session == null){
			res.sendRedirect(req.getContextPath()+"/index.jsp");
		}else{
			if(session.getAttribute("userName")==null){
				System.out.println(req.getContextPath()+"/index.jsp");
				res.sendRedirect(req.getContextPath()+"/index.jsp");
			}else{
				chain.doFilter(req, res);
			}
		}
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}


}
