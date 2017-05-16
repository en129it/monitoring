package com.ddv.graph.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ddv.graph.xml.graph.AppTypeType;
import com.ddv.graph.xml.graph.DependenciesType;
import com.ddv.graph.xml.graph.NodeType;

public class Node {

	private int id;
	private NodeType metaData;
	private HashSet<Node> children;
	private HashSet<Node> parents;
	private int layerPos = 0;
	
	public Node(NodeType aMetaData, int anId) {
		id = anId;
		metaData = aMetaData;
		children = new HashSet<Node>();
		parents = new HashSet<Node>();
	}
	
	public int getId() {
		return id;
	}
	
	public String getAppName() {
		return metaData.getAppName();
	}
	
	public AppTypeType getAppType() {
		return metaData.getAppType();
	}
	
	public boolean isCluster() {
		return metaData.isCluster();
	}
	
	public int getLayerPos() {
		return layerPos;
	}
	
	public boolean isDependentUpon(String anAppName) {
		DependenciesType dependencies = metaData.getDependencies();
		if (dependencies!=null) {
			return dependencies.getAppName().contains(anAppName);
		}
		return false;
	}
	
	public Set<Node> getParents() {
		return parents;
	}
	public void addParent(Node aParent) {
		parents.add(aParent);
System.out.println("######## add parent " + parents.size());		
		adjustLayerPos();
	}
	
	void adjustLayerPos(int aNewPos) {
		layerPos = aNewPos; 
		
		for (Node child : children) {
			child.adjustLayerPos();
		}
	}
	
	void adjustLayerPos() {
		for (Node parent : parents) {
			System.out.println("      ### adjust layer pos " + parent.layerPos + " - " + layerPos);				
			if (parent.layerPos>=layerPos) {
				layerPos = parent.layerPos +1; 
System.out.println("                         ### adjust layer pos " + this.getAppName() + " - " + layerPos);				
				for (Node child : children) {
					child.adjustLayerPos();
				}
			}
		}
	}
	
	public Set<Node> getChildren() {
		return children;
	}
	public void clearChildren() {
		children.clear();
	}
	public void addChild(Node aChild) {
		children.add(aChild);
	}
	
	@Override
	public String toString() {
		return "[Node " + getAppName() + " (layer=" + layerPos  + ")]";
	}
}
