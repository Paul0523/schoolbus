package com.schoolbus.service;

import com.schoolbus.entity.Driver;
import com.schoolbus.entity.Line;

/**
 * Created by Paul on 2016/3/2.
 */
public interface DriverService {
	public String getLine(Driver driver);
	public String start(Driver driver);
	public String updateGPS(Driver driver);
	public String stop(Driver driver);
}
