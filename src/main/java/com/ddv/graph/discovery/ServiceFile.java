package com.ddv.graph.discovery;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.ddv.graph.model.LayeredGraph;
import com.ddv.graph.model.LayeredGraphFactory;
import com.ddv.graph.service.IServiceProvider;
import com.ddv.graph.service.ServiceRepository;
import com.ddv.graph.xml.graph.GraphType;
import com.ddv.graph.xml.graph.NodeType;
import com.ddv.graph.xml.service.ServiceType;
import com.ddv.graph.xml.service.ServicesType;

public class ServiceFile implements IServiceProvider {

	private ServiceRepository serviceRepository;

	public ServiceFile(ServiceRepository aServiceRepository) {
		serviceRepository = aServiceRepository;
	}
	
	public void init() throws JAXBException {
		JAXBContext context = JAXBContext.newInstance("com.ddv.graph.xml.service");
		Unmarshaller unmarshaller = context.createUnmarshaller();
		JAXBElement<ServicesType> servicesElem = (JAXBElement<ServicesType>)unmarshaller.unmarshal(new File("C:/Users/ddev/workspace/graph/src/main/resources/b.xml"));
		
		for (ServiceType service : servicesElem.getValue().getService()) {
			System.out.println("Materialize service " + service.getActuatorBaseUrl() + " - " + service.getAppName());
		}
	}
	
}
