package com.schoolbus.dao;

import java.util.ArrayList;

import com.schoolbus.entity.Bus;

public interface BusDao {
	public Page<Bus> selectBusByPage(Bus bus,int start,int size);
	public ArrayList<Bus> selectBus(Bus bus);
	public Bus selectBusById(int id);
	public void saveBus(Bus bus);
	public void updateBus(Bus bus);
	public int deleteBus(int id);
}
