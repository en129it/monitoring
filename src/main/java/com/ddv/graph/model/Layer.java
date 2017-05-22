package com.ddv.graph.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	
// NEW START	
	boolean adjustNodeHorizontalPosition() {
		boolean isModif = false;
		for (Node node : nodes) {
			Set<Node> children = node.getChildren();
			if (children.isEmpty()) {
				continue;
			}
			
			HashSet<Node> siblings = new HashSet<Node>(); 
			getParents(children, siblings);
			if (siblings.isEmpty()) {
				continue;
			}

			List<Node> target = toOrderedList(siblings);
			List<Node> ref = toOrderedList(children);

			int widthSiblings = getXIntervalWidth(target);
			int widthChildrens = getXIntervalWidth(ref);
			
			if (widthSiblings!=widthChildrens) {
				if (widthSiblings>widthChildrens) {
					// Adjust children
					List<Node> inter = ref;
					ref = target;
					target = inter;
				} else {
					// Adjust siblings
				}
				
				double targetCenter = getXIntervalCenter(target);
				double refCenter = getXIntervalCenter(ref);
				int offset = (int)(refCenter - targetCenter);
				if (offset!=0) {
					Node targetNode = ((offset>0) ? target : ref).get(0);
					graph.findLayer(targetNode).offsetNodes(targetNode, offset);
					isModif = true;
				}
			}
		}
		return isModif;
	}
	
	private void offsetNodes(Node aStartNode, int anOffset) {
		int pos = nodes.indexOf(aStartNode);
		if (pos>-1) {
			Node node = null;
			for (int i=pos; i<nodes.size(); i++) {
				node = nodes.get(i);
				node.setX(node.getX() + anOffset);
			}
		}
	}
	
	private List<Node> toOrderedList(Set<Node> aNodes) {
		ArrayList<Node> nodes = new ArrayList<Node>(aNodes);
		Collections.sort(nodes, new Comparator<Node>() {
			@Override
			public int compare(Node aFirst, Node aSecond) {
				return aFirst.getX()-aSecond.getX();
			}
		});
		return nodes;
	}
	
	private void getParents(Set<Node> aNodes, Set<Node> anOut) {
		for (Node node : aNodes) {
			Set<Node> parents = node.getParents();
			for (Node parent : parents) {
				if (nodes.contains(parent)) {
					anOut.add(parent);
				}
			}
		}
	}
	
	private double getXIntervalCenter(List<Node> anOrderedNodes) {
		return ( ((double)anOrderedNodes.get(0).getX()) + ((double)anOrderedNodes.get(anOrderedNodes.size()-1).getX()) ) / 2d;
	}
	
	private int getXIntervalWidth(List<Node> anOrderedNodes) {
		return anOrderedNodes.get(anOrderedNodes.size()-1).getX() - anOrderedNodes.get(0).getX(); 
	}
// NEW END	
	
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
