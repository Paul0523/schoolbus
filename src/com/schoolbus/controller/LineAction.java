package com.schoolbus.controller;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.schoolbus.entity.Line;
import com.schoolbus.service.LineService;

@Controller
public class LineAction extends ActionSupport{
	private Log logger = LogFactory.getLog(LineAction.class);
	@Resource
	private LineService lineService;
	private String resultStr;
	private Line line;
	
	public String lines(){
		resultStr = lineService.getLines(line);
		return "strResponse";
	}
	
	public String updateLine(){
		resultStr = lineService.updateLines(line);
		return "strResponse";
	}
	
	public String removeLine(){
		resultStr = lineService.removeLine(line);
		return "strResponse";
	}

	public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
	}

	public String getResultStr() {
		return resultStr;
	}
}
