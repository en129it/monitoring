package com.ddv.graph.model;

public class StatusUI {

	private int nodeId;
	private String serviceId;
	private String status;
	// NEW START
	private int heapCommittedInBytes;
	private int heapUsedInBytes;
	// NEW END
	
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

	// NEW START
	public int getHeapCommittedInBytes() {
		return heapCommittedInBytes;
	}

	public void setHeapCommittedInBytes(int heapCommittedInBytes) {
		this.heapCommittedInBytes = heapCommittedInBytes;
	}

	public int getHeapUsedInBytes() {
		return heapUsedInBytes;
	}

	public void setHeapUsedInBytes(int heapUsedInBytes) {
		this.heapUsedInBytes = heapUsedInBytes;
	}
	// NEW END
}
