package com.schoolbus.service;

import com.schoolbus.entity.Bus;

public interface BusService {
	public String getBuses(Bus bus,int page,int rows);
	public String updateBus(Bus bus);
	public String removeBus(Bus bus);
	public String busById(Bus bus);
}
