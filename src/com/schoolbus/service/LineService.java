package com.schoolbus.service;

import com.schoolbus.entity.Line;

public interface LineService {

	public String getLines(Line line);
	public String updateLines(Line line);
	public String removeLine(Line line);
}
