package com.ddv.graph.model;

import java.net.URI;

public class Service {

	private String id;
	private String appName;
	private URI managementUrl;
	private URI healthUrl;
	private ServiceStatusEnum status = ServiceStatusEnum.UNKNOWN;
	private long statusSetTimestampInMsec = System.currentTimeMillis();
	
	public Service(String anId, String anAppName, URI aManagementUrl, URI aHealthUrl) {
		id = anId;
		updateService(anAppName, aManagementUrl, aHealthUrl);
	}
	
	public String getId() {
		return id;
	}

	public String getAppName() {
		return appName;
	}
	
	public ServiceStatusEnum getStatus() {
		return status;
	}
	
	public long getStatusSetTimestampInMsec() {
		return statusSetTimestampInMsec;
	}
	
	public URI getManagementUrl() {
		return managementUrl;
	}
	
	public URI getHealthUrl() {
		return healthUrl;
	}
	
	public void updateStatus(ServiceStatusEnum aStatus, long aStatusSetTimestampInMsec) {
		status = aStatus;
		statusSetTimestampInMsec = aStatusSetTimestampInMsec;
	}
	
	public void updateService(String anAppName, URI aManagementUrl, URI aHealthUrl) {
		appName = anAppName;
		managementUrl = aManagementUrl;
		healthUrl = aHealthUrl;
	}
	
	public Service clone() {
		Service rslt = new Service(id, appName, managementUrl, healthUrl);
		rslt.status = status;
		rslt.statusSetTimestampInMsec = statusSetTimestampInMsec;
		return rslt;
	}
}
