package com.ddv.graph.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ddv.graph.model.EdgeUI;
import com.ddv.graph.model.GraphUI;
import com.ddv.graph.model.LayeredGraph;
import com.ddv.graph.model.Node;
import com.ddv.graph.model.Service;
import com.ddv.graph.model.ServiceStatusEnum;
import com.ddv.graph.model.StatusUI;
import com.ddv.graph.model.VerticeUI;
import com.ddv.graph.service.ServiceRepository;
import com.ddv.graph.xml.graph.AppTypeType;


@RestController
@RequestMapping("/api")
public class MonitoringRestController {

	@Autowired
	private LayeredGraph layeredGraph;
	@Autowired
	private ServiceRepository serviceRepository;
	
	
	@RequestMapping("/graph")
	public ResponseEntity<GraphUI> getGraph() {
		GraphUI graph = new GraphUI();
		
		int layerCount = layeredGraph.getLayerCount();
		for (int y=0; y<layerCount; y++) {
			List<Node> nodes = layeredGraph.getNodes(y);
			int x = 0;
			for (Node node : nodes) {
				graph.getVertices().add(new VerticeUI(node, x, y));
				x++;
				
				for (Node childNode : node.getChildren()) {
					graph.getEdges().add(new EdgeUI(node.getId(), childNode.getId()));
				}
			}
		}
		
		ResponseEntity<GraphUI> rslt = new ResponseEntity<GraphUI>(graph, HttpStatus.OK); 
		return rslt;
	}
	
	@RequestMapping("/status")
	public ResponseEntity<List<StatusUI>> getServiceStatuses() {
		HashMap<Service, Node> serviceToNodeMap = new HashMap<Service, Node>();
		HashMap<Node, List<Service>> nodeToServiceMap = new HashMap<Node, List<Service>>();
		
		List<String> serviceIds = serviceRepository.getAllServiceIds();
		for (String serviceId : serviceIds) {
			Service service = serviceRepository.getService(serviceId);
					
			Node node = findServiceNode(service);
			serviceToNodeMap.put(service, node);
			
			List<Service> nodeServices = nodeToServiceMap.get(node);
			if (nodeServices==null) {
				nodeServices = new ArrayList<Service>();
				nodeToServiceMap.put(node, nodeServices);
			}
			nodeServices.add(service);
		}

		ArrayList<StatusUI> statuses = new ArrayList<StatusUI>();  
		for (Service service : serviceToNodeMap.keySet()) {
			Node serviceNode = serviceToNodeMap.get(service);
			ServiceStatusEnum status = computeStatus(service, serviceNode, nodeToServiceMap);
			statuses.add(new StatusUI(serviceNode.getId(), service.getId(), status.name()));
		}
		
		ResponseEntity<List<StatusUI>> rslt = new ResponseEntity<List<StatusUI>>(statuses, HttpStatus.OK); 
		return rslt;
	}

	private Node findServiceNode(Service aService) {
		return layeredGraph.findNode(aService.getAppName());
	}
	
	private ServiceStatusEnum computeStatus(Service aService, Node aServiceNode, HashMap<Node, List<Service>> aNodeToServiceMap) {
		if (ServiceStatusEnum.UP.equals(aService.getStatus())) {
			return computeStatusHelper(aServiceNode, aNodeToServiceMap);
		} else {
			return aService.getStatus();
		}
	}
	
	private ServiceStatusEnum computeStatusHelper(Node aNode, HashMap<Node, List<Service>> aNodeToServiceMap) {
		for (Node child : aNode.getChildren()) {
			if (AppTypeType.WEB_APP.equals(child.getAppType())) {
				List<Service> nodeServices = aNodeToServiceMap.get(child);
				if (nodeServices!=null) {
					for (Service nodeService : nodeServices) {
						if (!ServiceStatusEnum.UP.equals(nodeService.getStatus())) {
							return ServiceStatusEnum.MALFUNCTION;
						}
					}
					if (ServiceStatusEnum.MALFUNCTION.equals(computeStatusHelper(child,  aNodeToServiceMap))) {
						return ServiceStatusEnum.MALFUNCTION;
					}
				} else {
					// Case : child node without associated service
					return ServiceStatusEnum.MALFUNCTION;
				}
			}
		}
		return ServiceStatusEnum.UP;
	}
	
}
