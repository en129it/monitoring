package com.ddv.graph.discovery;

import java.net.URI;
import java.util.List;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.event.HeartbeatEvent;
import org.springframework.cloud.client.discovery.event.HeartbeatMonitor;
import org.springframework.cloud.client.discovery.event.InstanceRegisteredEvent;
import org.springframework.cloud.client.discovery.event.ParentHeartbeatEvent;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient.EurekaServiceInstance;
import org.springframework.context.event.EventListener;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import com.ddv.graph.model.Service;
import com.ddv.graph.service.IServiceProvider;
import com.ddv.graph.service.ServiceRepository;
import com.netflix.appinfo.InstanceInfo;

public class ServiceDiscoveryListener implements IServiceProvider {
	private static final String MANAGEMENT_PORT__KEY = "management.port";
	private static final String MANAGEMENT_CONTEXT_PATH__KEY = "management.context-path";
	
	private DiscoveryClient discoveryClient;
	private ServiceRepository serviceRepository;
	private HeartbeatMonitor monitor = new HeartbeatMonitor();

	public ServiceDiscoveryListener(DiscoveryClient aDiscoveryClient, ServiceRepository aServiceRepository) {
		discoveryClient = aDiscoveryClient;
		serviceRepository = aServiceRepository;
	}

	@EventListener
	public void onInstanceRegistered(InstanceRegisteredEvent<?> anEvent) {
		System.out.println("##### onInstanceRegisteredInstance " + anEvent.getSource() + "- " + anEvent.getConfig());
		discover();
	}

	@EventListener
	public void onParentHeartbeat(ParentHeartbeatEvent anEvent) {
		System.out.println("##### onParentHeartbeat " + anEvent.getSource() + " - " + anEvent.getValue());
		discover(anEvent.getValue());
	}

	@EventListener
	public void onApplicationEvent(HeartbeatEvent anEvent) {
		System.out.println("##### onApplicationEventHeartbeat " + anEvent.getSource() + " - " + anEvent.getValue());
		discover(anEvent.getValue());
	}

	private void discover(Object anHeartbeatCountValue) {
		if (this.monitor.update(anHeartbeatCountValue)) {
			discover();
		}
	}

	private void discover() {
		try {		
			for (String serviceId : discoveryClient.getServices()) {
				for (ServiceInstance instance : discoveryClient.getInstances(serviceId)) {
					if (instance instanceof EurekaServiceInstance) {
						EurekaServiceInstance eurekaInstance = (EurekaServiceInstance)instance;
						serviceRepository.register(eurekaInstance.getServiceId().toUpperCase(), getManagementUrl(eurekaInstance), getHealthUrl(eurekaInstance));
					}
				}
			}
		} catch (RuntimeException ex) {
			// Necessary because EventListener ignores generated exceptions
			ex.printStackTrace();
			throw ex;
		}
	}
	
	private URI getManagementUrl(EurekaServiceInstance aServiceInstance) {
		String managamentPath = aServiceInstance.getMetadata().get(MANAGEMENT_CONTEXT_PATH__KEY);
		if (managamentPath==null) {
			managamentPath = "";
		} else if (managamentPath.startsWith("/")) {
			managamentPath = managamentPath.substring(1);
		}

		URI serviceUrl = aServiceInstance.getUri();
		String managementPort = aServiceInstance.getMetadata().get(MANAGEMENT_PORT__KEY);
		if (managementPort==null) {
			managementPort = Integer.toString(serviceUrl.getPort());
		}

		return UriComponentsBuilder.fromUri(serviceUrl).port(managementPort).pathSegment(managamentPath).build().toUri();
	}

	private URI getHealthUrl(EurekaServiceInstance aServiceInstance) {
		InstanceInfo instanceInfo = aServiceInstance.getInstanceInfo();
		String healthUrlStr = instanceInfo.getSecureHealthCheckUrl();
		if (StringUtils.isEmpty(healthUrlStr)) {
			healthUrlStr = instanceInfo.getHealthCheckUrl();
		}
		return URI.create(healthUrlStr);
	}

}
