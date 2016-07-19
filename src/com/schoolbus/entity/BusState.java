package com.schoolbus.entity;

public class BusState {
	private int curStationId;
	private int nextStationId;
	private int num;
	public int getCurStationId() {
		return curStationId;
	}
	public void setCurStationId(int curStationId) {
		this.curStationId = curStationId;
	}
	public int getNextStationId() {
		return nextStationId;
	}
	public void setNextStationId(int nextStationId) {
		this.nextStationId = nextStationId;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
}
