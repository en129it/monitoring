package com.ddv.graph;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.ddv.graph.config.EnableMonitoring;

@SpringBootApplication
@EnableEurekaServer
@EnableDiscoveryClient
@EnableMonitoring
public class MonitoringApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonitoringApplication.class, args);
	}
	//http://blog.abhijitsarkar.org/technical/netflix-eureka/
}
