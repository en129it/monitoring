package com.ddv.graph.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient.EurekaServiceInstance;

import com.ddv.graph.model.LayeredGraph;
import com.ddv.graph.model.Service;

public class ServiceRepository {

	private HashMap<String, Service> serviceIdToServiceMap = null;
	@Autowired
	private LayeredGraph layeredGraph;

	public ServiceRepository() {
		serviceIdToServiceMap = new HashMap<String, Service>();
	}

	public synchronized Service getService(String aServiceId) {
		return serviceIdToServiceMap.get(aServiceId);
	}

	public synchronized List<String> getAllServiceIds() {
		return new ArrayList<String>(serviceIdToServiceMap.keySet());
	}
	
	public synchronized Service register(String anAppName, URI aManagementUrl, URI aHealthUrl) {
		if (layeredGraph.containsNodeFor(anAppName)) {
			String serviceId = generateId(anAppName, aManagementUrl, aHealthUrl);
	
			Service service = serviceIdToServiceMap.get(serviceId);
			if (service!=null) {
				service.updateService(anAppName, aManagementUrl, aHealthUrl);
				System.out.println("#### Update existing service " + anAppName);
			} else {
				service = new Service(serviceId, anAppName, aManagementUrl, aHealthUrl);
				serviceIdToServiceMap.put(serviceId, service);
				System.out.println("#### Register new service " + anAppName);
			}
			return service;
		} else {
			System.out.println("#### Register new service skip : unknown service" + anAppName);
			return null;
		}
	}

	public synchronized void unRegister(String aServiceId) {
		System.out.println("#### Unregister service " + aServiceId);
		serviceIdToServiceMap.remove(aServiceId);
	}
	
	private String generateId(String anAppName, URI aManagementUrl, URI aHealthUrl) {
		return aHealthUrl.toASCIIString();
	}
}
