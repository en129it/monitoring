package com.ddv.graph.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.ddv.graph.xml.graph.AppTypeType;

public class LayeredGraph {

	private ArrayList<Layer> layers;
	
	public LayeredGraph(Set<Node> aNodes) {
		layers = new ArrayList<Layer>();
		initLayers(aNodes);
		minimizeCrossings();
		adjustNodeHorizontalPosition();
	}
	
	private void initLayers(Set<Node> aNodes) {
		// Here is defined the order between the different layer types: WEB_APP -> LEGACY_APP -> DATABASE 
		int layerPosMax = getMaxLayerPos(AppTypeType.WEB_APP, aNodes);
		initLayersHelper(layerPosMax, aNodes, AppTypeType.LEGACY_APP, AppTypeType.DATABASE);

		layerPosMax = getMaxLayerPos(AppTypeType.LEGACY_APP, aNodes);
		initLayersHelper(layerPosMax, aNodes, AppTypeType.DATABASE);
		
		layerPosMax = getMaxLayerPos(AppTypeType.DATABASE, aNodes);
		
		createLayers(layerPosMax, aNodes);
	}

	private void minimizeCrossings() {
		boolean isModif = true; int count = 0;
		while ((count<100) && (isModif)) {
			System.out.println("Minimize crossings (iteration " + count + ")");
			isModif = false;
			for (Layer layer : layers) {
				isModif = layer.minimizeCrossings() || isModif;
			}
			count++;
		}
	}
	
// NEW START	
	private void adjustNodeHorizontalPosition() {
		boolean isModif = true; int count = 0;
		while ((count<100) && (isModif)) {
			System.out.println("Adjust horizontal position (iteration " + count + ")");
			isModif = false;
			for (Layer layer : layers) {
				isModif = layer.adjustNodeHorizontalPosition() || isModif;
			}
			count++;
		}
	}
// NEW END	
	
	private void createLayers(int aMaxLayers, Set<Node> aNodes) {
		for (int i=0; i<aMaxLayers; i++) {
			layers.add(new Layer(this));
		}
		
		for (Node node : aNodes) {
			layers.get(node.getLayerPos()).addNode(node);
		}
	}
	
	private int getMaxLayerPos(AppTypeType anAddType, Set<Node> aNodes) {
		int rslt = 0;
		for (Node node : aNodes) {
			if (node.getAppType().equals(anAddType)) {
				rslt = Math.max(rslt,  node.getLayerPos());
			}
		}
		return rslt+1;
	}
	
	private void initLayersHelper(int aMinLayerPosRange, Set<Node> aNodes, AppTypeType... anAppTypes) {
		List<AppTypeType> appTypes = Arrays.asList(anAppTypes);
		for (Node node : aNodes) {
			if (appTypes.contains(node.getAppType())) {
				if (node.getLayerPos()<aMinLayerPosRange) {
					node.adjustLayerPos(aMinLayerPosRange);
				}
			}
		}
	}
	
	public Node findNode(String anAppName) {
		for (Layer layer : layers) {
			Node node = layer.findNode(anAppName);
			if (node!=null) {
				return node;
			}
		}
		return null;
	}
	
	public boolean containsNodeFor(String anAppName) {
		return (findNode(anAppName)!=null);
	}
	
	public Layer findLayer(Node aNode) {
		for (Layer layer : layers) {
			if (layer.containsNode(aNode)) {
				return layer;
			}
		}
		return null;
	}
	
	public int getLayerCount() {
		return layers.size();
	}
	
	public List<Node> getNodes(int aLayerIndex) {
		return layers.get(aLayerIndex).getNodes();
	}
	
	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		
		for (Layer layer : layers) {
			buffer.append("Layer\r\n");
			for (Node node : layer.getNodes()) {
				buffer.append("   ").append(node).append("\r\n");
			}
		}
		
		return buffer.toString();
	}
}
