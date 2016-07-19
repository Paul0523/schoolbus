package com.schoolbus.controller;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionSupport;
import com.schoolbus.entity.LineInfo;
import com.schoolbus.service.LineInfoService;


public class LineInfoAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Log logger = LogFactory.getLog(LineInfoAction.class);
	private String resultStr;
	@Resource
	private LineInfoService lineInfoService;
	private LineInfo lineInfo;
	
	
	public String lineInfo(){
		resultStr = lineInfoService.getLineInfo(lineInfo);
		return "strResponse";
	}
	
	public String saveLineInfo(){
		resultStr = lineInfoService.saveLineInfo(lineInfo);
		return "strResponse";
	}
	
	public String updateLineInfoGPSById(){
		resultStr = lineInfoService.updateLineInfoGPSById(lineInfo);
		return "strResponse";
	}
	
	public String removeLineInfoById(){
		resultStr = lineInfoService.removeLineInfoById(lineInfo);
		return "strResponse";
	}
	
	
	public String removeLineInfo(){
		resultStr = lineInfoService.removeLineInfo(lineInfo);
		return "strResponse";
	}
	
	
	
	public String getResultStr() {
		return resultStr;
	}
	public LineInfo getLineInfo() {
		return lineInfo;
	}
	public void setLineInfo(LineInfo lineInfo) {
		this.lineInfo = lineInfo;
	}
}
