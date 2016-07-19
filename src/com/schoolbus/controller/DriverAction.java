package com.schoolbus.controller;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.schoolbus.entity.Driver;
import com.schoolbus.service.DriverService;

/**
 * Created by Paul on 2016/3/2.
 */
@Controller
public class DriverAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Log logger = LogFactory.getLog(DriverAction.class);
	private String resultStr;
	@Resource
	private DriverService driverService;
	private Driver driver;
	
	public String line(){
		resultStr = driverService.getLine(driver);
		return "strResponse";
	}
	
	public String start(){
		resultStr = driverService.start(driver);
		return "strResponse";
	}

	public String stop(){
		resultStr = driverService.stop(driver);
		return "strResponse";
	}
	
	public String updateGPS(){
		resultStr = driverService.updateGPS(driver);
		return "strResponse";
	}
	
	
	
	public Driver getDriver() {
		return driver;
	}
	public void setDriver(Driver driver) {
		this.driver = driver;
	}
	public String getResultStr() {
		return resultStr;
	}
	
}
