package com.ddv.graph.model;

import java.io.Serializable;

public class EdgeUI implements Serializable {

	private int parentEdgeId;
	private int childEdgeId;
	
	public EdgeUI() {
		
	}
	
	public EdgeUI(int aParentEdgeId, int aChildEdgeId) {
		parentEdgeId = aParentEdgeId;
		childEdgeId = aChildEdgeId;
	}
	
	public int getParentEdgeId() {
		return parentEdgeId;
	}
	public void setParentEdgeId(int anId) {
		parentEdgeId = anId;
	}
	public int getChildEdgeId() {
		return childEdgeId;
	}
	public void setChildEdgeId(int anId) {
		childEdgeId = anId;
	}
	
	
}
