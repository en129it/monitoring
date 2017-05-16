package com.ddv.graph.model;

import java.util.ArrayList;
import java.util.HashSet;

import com.ddv.graph.xml.graph.DependenciesType;
import com.ddv.graph.xml.graph.NodeType;

public class LayeredGraphFactory {

	private HashSet<Node> nodes;
	private int idGenerator = 1;
	
	public LayeredGraphFactory() {
		nodes = new HashSet<Node>();
	}
	
	public void addNode(NodeType aNode) {
		// Adjust incoming data
		aNode.setAppName(aNode.getAppName().toUpperCase());
		if (aNode.getDependencies()!=null) {
			ArrayList<String> upperCaseDependentAppNames = new ArrayList<String>();
			for (String dependentAppName : aNode.getDependencies().getAppName()) {
				upperCaseDependentAppNames.add(dependentAppName.toUpperCase());
			}
			aNode.getDependencies().getAppName().clear();
			aNode.getDependencies().getAppName().addAll(upperCaseDependentAppNames);
		}
		
		// Create new node
		Node newNode = new Node(aNode, idGenerator++);
		nodes.add(newNode);

		// Create the linkages between the new node and its children
		DependenciesType dependencies = aNode.getDependencies();
		if (dependencies!=null) {
			for (String dependentAppName : dependencies.getAppName()) {
				Node dependentNode = findNode(dependentAppName.toUpperCase());
				if (dependentNode!=null) {
					newNode.addChild(dependentNode);
					dependentNode.addParent(newNode);
				}
			}
		}
		
		// Resolve linkages that could not be established because the nodes were not present
		for (Node node : nodes) {
			if (node.isDependentUpon(aNode.getAppName())) {
				node.addChild(newNode);
				newNode.addParent(node);
			}
		}
	}
	
	public LayeredGraph build() {
		System.out.println(String.format("Building graph from %d nodes", nodes.size()));
		return new LayeredGraph(nodes);
	}
	
	private Node findNode(String anAppName) {
		for (Node node : nodes) {
			if (node.getAppName().equals(anAppName)) {
				return node;
			}
		}
		return null;
	}
	
}
