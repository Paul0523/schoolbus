package com.schoolbus.entity;
// Generated 2016-4-21 22:32:06 by Hibernate Tools 4.3.1.Final

/**
 * LineStations generated by hbm2java
 */
public class LineStations implements java.io.Serializable {

	private Integer id;
	private Integer lineId;
	private Integer stationId;
	private Integer priority;
	private Integer orientation;
	private Integer num;

	public LineStations() {
	}

	public LineStations(Integer lineId, Integer stationId, Integer priority, Integer orientation, Integer num) {
		this.lineId = lineId;
		this.stationId = stationId;
		this.priority = priority;
		this.orientation = orientation;
		this.num = num;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLineId() {
		return this.lineId;
	}

	public void setLineId(Integer lineId) {
		this.lineId = lineId;
	}

	public Integer getStationId() {
		return this.stationId;
	}

	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}

	public Integer getPriority() {
		return this.priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Integer getOrientation() {
		return this.orientation;
	}

	public void setOrientation(Integer orientation) {
		this.orientation = orientation;
	}

	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

}
