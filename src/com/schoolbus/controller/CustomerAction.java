package com.schoolbus.controller;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionSupport;
import com.schoolbus.entity.Customer;
import com.schoolbus.service.CustomerService;

/**
 * Created by Paul on 2016/3/2.
 */
public class CustomerAction extends ActionSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Log logger = LogFactory.getLog(CustomerAction.class);
	@Resource
	private CustomerService customerService;
	private Customer customer;
	private String resultStr = "{\"errmsg\":\"遇到异常，请稍后重试\"}";
	
	
	public String searchInfo(){
		resultStr = customerService.getSearch(customer);
		return "strResponse";
	}
	
	public String station(){
		resultStr = customerService.getStation(customer);
		return "strResponse";
	} 
	
	public String line(){
		resultStr = customerService.getLine(customer);
		return "strResponse";
	}

	public String lineStations(){
		resultStr = customerService.getLineStations(customer);
		return "strResponse";
	}
	
	public String lineBuses(){
		resultStr = customerService.getLineBuses(customer);
		return "strResponse";
	}
	
	public String callBus(){
		resultStr = customerService.callBus(customer);
		return "strResponse";
	}
	
	
	
	//getter,setter
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getResultStr() {
		return resultStr;
	}

}