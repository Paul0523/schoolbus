package com.schoolbus.controller;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.schoolbus.entity.Line;
import com.schoolbus.service.LineStationsService;

@Controller
public class LineStationsAction extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Log logger  = LogFactory.getLog(LineStationsAction.class);
	@Resource
	private LineStationsService lineStationsService;
	private String resultStr;
	private Line line;
	private int orientation;
	private int lineId;
	private int newStationId;
	private int curStationId;
	private int page;
	private int rows;
	
	
	public String lineStations(){
		resultStr = lineStationsService.getLineStations(line,orientation,page,rows);
		return "strResponse";
	}
	
	public String updateLineStations(){
		resultStr = lineStationsService.updateLineStations(lineId,newStationId,curStationId,orientation);
		return "strResponse";
	}
	
	public String removeLineStations(){
		resultStr = lineStationsService.removeLineStations(lineId,curStationId,orientation);
		return "strResponse";
	}

	public String lineStationsForList(){
		resultStr = lineStationsService.getLineStationsForList(line,orientation);
		return "strResponse";
	}
	
	
	public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
	}

	public int getOrientation() {
		return orientation;
	}

	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}

	public int getLineId() {
		return lineId;
	}

	public void setLineId(int lineId) {
		this.lineId = lineId;
	}

	public int getNewStationId() {
		return newStationId;
	}

	public void setNewStationId(int newStationId) {
		this.newStationId = newStationId;
	}

	public int getCurStationId() {
		return curStationId;
	}

	public void setCurStationId(int curStationId) {
		this.curStationId = curStationId;
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
