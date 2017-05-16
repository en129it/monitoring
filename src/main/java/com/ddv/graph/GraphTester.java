package com.ddv.graph;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import com.ddv.graph.model.LayeredGraph;
import com.ddv.graph.model.LayeredGraphFactory;
import com.ddv.graph.xml.graph.GraphType;
import com.ddv.graph.xml.graph.NodeType;

public class GraphTester {

	public void start() throws Exception {
		JAXBContext context = JAXBContext.newInstance("com.ddv.graph.xml");
		Unmarshaller unmarshaller = context.createUnmarshaller();
		JAXBElement<GraphType> graphElem = (JAXBElement<GraphType>)unmarshaller.unmarshal(new File("C:/Users/ddev/workspace/graph/src/main/resources/a.xml"));
		
		LayeredGraphFactory graphFactory = new LayeredGraphFactory();
		for (NodeType node : graphElem.getValue().getNode()) {
			graphFactory.addNode(node);
		}
		LayeredGraph graph = graphFactory.build();
		
		System.out.println(graph.toString());
	}
	
	public static void main(String[] args) throws Exception {
		GraphTester t = new GraphTester();
		t.start();
	}
	
}
