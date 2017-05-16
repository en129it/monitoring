package com.ddv.graph.model;

import java.io.Serializable;

public class VerticeUI implements Serializable {
	private int id;
	private int x;
	private int y;
	private String appName;
	private String appType;
	private boolean isCluster;
	
	public VerticeUI() {
		
	}
	
	public VerticeUI(Node aNode, int aX, int aY) {
		id = aNode.getId();
		appName = aNode.getAppName();
		appType = aNode.getAppType().name();
		isCluster = aNode.isCluster();
		x = aX;
		y = aY;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	
}
