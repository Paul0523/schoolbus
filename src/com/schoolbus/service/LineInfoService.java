package com.schoolbus.service;

import com.schoolbus.entity.LineInfo;

public interface LineInfoService {
	public String getLineInfo(LineInfo lineInfo);
	public String saveLineInfo(LineInfo lineInfo);
	public String updateLineInfoGPSById(LineInfo lineInfo);
	public String removeLineInfo(LineInfo lineInfo);
	public String removeLineInfoById(LineInfo lineInfo);
}
