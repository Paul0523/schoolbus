package com.schoolbus.controller;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import com.opensymphony.xwork2.ActionSupport;
import com.schoolbus.service.ManagerService;

/**
 * Created by Paul on 2016/3/2.
 */
@Controller
public class ManagerAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Log logger = LogFactory.getLog(ManagerAction.class);
	@Resource
	private ManagerService managerService;
	public String index(){
		return "index";
	}
	
}
