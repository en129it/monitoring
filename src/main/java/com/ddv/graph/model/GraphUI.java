package com.ddv.graph.model;

import java.util.ArrayList;
import java.util.List;

public class GraphUI {

	private int xCount;
	private int yCount;
	private List<VerticeUI> vertices;
	private List<EdgeUI> edges;
	
	public GraphUI() {
		vertices = new ArrayList<VerticeUI>();
		edges = new ArrayList<EdgeUI>();
	}
	
	public List<VerticeUI> getVertices() {
		return vertices;
	}
	public void addVertice(VerticeUI aVertice) {
		vertices.add(aVertice);
		xCount = Math.max(xCount,  aVertice.getX());
		yCount = Math.max(yCount,  aVertice.getY());
	}
	public List<EdgeUI> getEdges() {
		return edges;
	}
	public void addEdge(EdgeUI anEdge) {
		edges.add(anEdge);
	} 
	
}
