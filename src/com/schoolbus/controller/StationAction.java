package com.schoolbus.controller;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.schoolbus.entity.Station;
import com.schoolbus.service.StationService;

@Controller
public class StationAction extends ActionSupport{
	private Log logger = LogFactory.getLog(StationAction.class);
	@Resource
	private StationService stationService;
	private String resultStr;
	private Station station;
	private int page;
	private int rows;
	
	public String stations(){
		resultStr = stationService.getStations(station,page,rows);
		return "strResponse";
	}
	public String updateStation(){
		resultStr = stationService.updateStation(station);
		return "strResponse";
	}
	public String removeStation(){
		resultStr = stationService.removeStation(station);
		return "strResponse";
	}
	public Station getStation() {
		return station;
	}
	public void setStation(Station station) {
		this.station = station;
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
