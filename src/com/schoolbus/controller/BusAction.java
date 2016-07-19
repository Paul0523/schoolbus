package com.schoolbus.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.schoolbus.entity.Bus;
import com.schoolbus.service.BusService;

@Controller
public class BusAction extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private BusService busService;
	private String resultStr;
	private Bus bus;
	private int page;
	private int rows;
	
	public String buses(){
		resultStr = busService.getBuses(bus,page,rows);
		return "strResponse";
	}
	
	public String updateBus(){
		resultStr = busService.updateBus(bus);
		return "strResponse";
	}
	
	public String removeBus(){
		resultStr = busService.removeBus(bus);
		return "strResponse";
	}

	public String busById(){
		resultStr = busService.busById(bus);
		return "strResponse";
	}
	
	
	public Bus getBus() {
		return bus;
	}

	public void setBus(Bus bus) {
		this.bus = bus;
	}

	public String getResultStr() {
		return resultStr;
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
