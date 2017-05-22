/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ddv.graph.config;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.noop.NoopDiscoveryClientAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.ddv.graph.discovery.ServiceDiscoveryListener;
import com.ddv.graph.model.LayeredGraph;
import com.ddv.graph.model.LayeredGraphFactory;
import com.ddv.graph.service.ServiceRepository;
import com.ddv.graph.service.StatusUpdaterService;
import com.ddv.graph.xml.graph.GraphType;
import com.ddv.graph.xml.graph.NodeType;

@Configuration
@AutoConfigureAfter({ NoopDiscoveryClientAutoConfiguration.class })
public class DiscoveryClientConfiguration {

	public DiscoveryClientConfiguration() {
	}
	
    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private ServiceRepository serviceRepository;

    @Bean
    public ServiceDiscoveryListener serviceDiscoveryListener() {
    	System.out.println("##### serviceDiscoveryListener");
        return new ServiceDiscoveryListener(discoveryClient, serviceRepository);
    }
    
    @Bean
    public ServiceRepository serviceRepository() {
    	return new ServiceRepository();
    }

    @Bean
    public LayeredGraph layeredGraph() throws JAXBException {
		JAXBContext context = JAXBContext.newInstance("com.ddv.graph.xml.graph");
		Unmarshaller unmarshaller = context.createUnmarshaller();
		JAXBElement<GraphType> graphElem = (JAXBElement<GraphType>)unmarshaller.unmarshal(new File("C:/Users/ddev/workspace/graph/src/main/resources/a.xml"));
		
		LayeredGraphFactory graphFactory = new LayeredGraphFactory();
		for (NodeType node : graphElem.getValue().getNode()) {
			graphFactory.addNode(node);
		}
		LayeredGraph graph = graphFactory.build();
		
		System.out.println(graph.toString());
		
		return graph;
    }
/*    
	@Bean
	public ThreadPoolTaskScheduler statusUpdaterTaskScheduler() {
		ThreadPoolTaskScheduler rslt = new ThreadPoolTaskScheduler();
		rslt.setPoolSize(1);
		rslt.setRemoveOnCancelPolicy(true);
		return rslt;
	}
  */  
    @Bean
    public StatusUpdaterService statusUpdaterService() {
    	return new StatusUpdaterService();
    }
    
	@Bean
    public RestTemplate restTemplate(RestTemplateBuilder aRestTemplateBuilder) {
		RestTemplate rslt = aRestTemplateBuilder
				.messageConverters(new MappingJackson2HttpMessageConverter())
				.errorHandler(new DefaultResponseErrorHandler() {
					@Override
					protected boolean hasError(HttpStatus statusCode) {
						return false;
					}
				}).build();
		return rslt;
    }
}
