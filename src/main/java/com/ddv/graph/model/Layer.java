package com.ddv.graph.model;

import java.util.ArrayList;
import java.util.List;

import com.ddv.graph.service.Utils;

public class Layer {

	private List<Node> nodes;
	private LayeredGraph graph;
	
	public Layer(LayeredGraph aGraph) {
		nodes = new ArrayList<Node>();
		graph = aGraph;
	}
	
	public void addNode(Node aNode) {
		nodes.add(aNode);
	}
	
	public void removeNode(Node aNode) {
		nodes.remove(aNode);
	}
	
	public Node findNode(String anAppName) {
		for (Node node : nodes) {
			if (node.getAppName().equals(anAppName)) {
				return node;
			}
		}
		return null;
	}
	
	public boolean containsNode(Node aNode) {
		return nodes.contains(aNode);
	}
	
	public List<Node> getNodes() {
		return nodes;
	}
	
	public Boolean isBefore(Node aRefNode, Node aNodeToCompare) {
		int refNodePos = nodes.indexOf(aRefNode);
		int nodeToComparePos = nodes.indexOf(aNodeToCompare);
		
		if ((refNodePos==-1) || (nodeToComparePos==-1)) {
			return null;
		} else {
			return (nodeToComparePos<refNodePos);
		}
	}
	
	public int countCrossings(List<Node> aNodeList) {
		int rslt = 0;
		int nodeSize = aNodeList.size();
		
		for (int i=0; i<nodeSize; i++) {
			Node node = aNodeList.get(i);
			for (Node parent : node.getParents()) {
				Layer parentLayer = graph.findLayer(parent);
				
				for (int j=i+1; j<nodeSize; j++) {
					Node nextNode = aNodeList.get(j);
					for (Node nextParent : nextNode.getParents()) {

						if (Boolean.TRUE.equals(parentLayer.isBefore(parent, nextParent))) {
							rslt++;
						}
					}
				}
			}
		}
		return rslt;
	}
	
	
	public boolean minimizeCrossings() {
		List<int[]> combinations = Utils.generateAllCombinations(nodes.size());
		
		List<Node> reorderedNodeList = null; List<Node> candidateList = nodes; int candidateNbCrossings = Integer.MAX_VALUE; int nbCrossings = 0;
		for (int[] combination : combinations) {
			reorderedNodeList = Utils.reorder(nodes, combination);
			nbCrossings = countCrossings(reorderedNodeList);
			if (nbCrossings < candidateNbCrossings) {
				candidateNbCrossings = nbCrossings;
				candidateList = reorderedNodeList;
			}
		}
		
		boolean isChange = (!Utils.areStrictEquals(nodes, candidateList));
		nodes = candidateList;
		
		return isChange;
	}
/*
	
	public int countCrossings(List<Node> aNodeList) {
		int rslt = 0;
		int nodeSize = aNodeList.size();
		
		for (int i=0; i<nodeSize; i++) {
			Node node = aNodeList.get(i);
			for (Node child : node.getChildren()) {
				Layer childLayer = graph.findLayer(child);
				
				for (int j=i+1; j<nodeSize; j++) {
					Node nextNode = aNodeList.get(j);
					for (Node nextChild : nextNode.getChildren()) {

						if (Boolean.TRUE.equals(childLayer.isBefore(child, nextChild))) {
							rslt++;
						}
					}
				}
			}
		}
		return rslt;
	}
	
	
	public boolean minimizeCrossings() {
		List<int[]> combinations = Utils.generateAllCombinations(nodes.size());
		
		List<Node> reorderedNodeList = null; List<Node> candidateList = nodes; int candidateNbCrossings = Integer.MAX_VALUE; int nbCrossings = 0;
		for (int[] combination : combinations) {
			reorderedNodeList = Utils.reorder(nodes, combination);
			nbCrossings = countCrossings(reorderedNodeList);
			if (nbCrossings < candidateNbCrossings) {
				candidateNbCrossings = nbCrossings;
				candidateList = reorderedNodeList;
			}
		}
		
		boolean isChange = (!Utils.areStrictEquals(nodes, candidateList));
		nodes = candidateList;
		
		return isChange;
	}
	
 */
	private void debug(List<Node> aNodeList) {
		String rslt = "";
		for (Node node : aNodeList) {
			rslt += node.getAppName() + " > ";
		}
	}
}
