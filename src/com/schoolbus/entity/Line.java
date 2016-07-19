package com.schoolbus.entity;
// Generated 2016-4-21 22:32:06 by Hibernate Tools 4.3.1.Final

/**
 * Line generated by hbm2java
 */
public class Line implements java.io.Serializable {

	private Integer id;
	private String name;
	private Integer state;
	private Integer orientation;

	public Line() {
	}

	public Line(String name) {
		this.name = name;
	}

	public Line(String name, Integer state, Integer orientation) {
		this.name = name;
		this.state = state;
		this.orientation = orientation;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getOrientation() {
		return this.orientation;
	}

	public void setOrientation(Integer orientation) {
		this.orientation = orientation;
	}

}