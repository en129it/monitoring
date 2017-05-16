package com.ddv.graph.model;

public class StatusUI {

	private int nodeId;
	private String serviceId;
	private String status;
	
	public StatusUI() {
		
	}
	
	public StatusUI(int aNodeId, String aServiceId, String aStatus) {
		nodeId = aNodeId;
		serviceId = aServiceId;
		status = aStatus;
	}
	
	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
