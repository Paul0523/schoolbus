package com.schoolbus.dao;

import java.util.ArrayList;

public class Page<T> {
	private ArrayList<T> list;
	private int totalCount;
	
	public Page(){}
	
	public Page(ArrayList<T> list,int totalCount){
		this.list = list;
		this.totalCount = totalCount;
	}
	
	
	public ArrayList<T> getList() {
		return list;
	}
	public void setList(ArrayList<T> list) {
		this.list = list;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
}
