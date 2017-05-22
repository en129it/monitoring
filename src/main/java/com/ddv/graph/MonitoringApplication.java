package com.ddv.graph;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.ddv.graph.config.EnableMonitoring;

import de.codecentric.boot.admin.config.EnableAdminServer;

@SpringBootApplication
@EnableEurekaServer
@EnableDiscoveryClient
@EnableMonitoring
@EnableAdminServer
public class MonitoringApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonitoringApplication.class, args);
	}
	//http://blog.abhijitsarkar.org/technical/netflix-eureka/
// NEW START	
	/*
	 *http://localhost:8761/metrics/gc.*
http://localhost:8761/metrics/mem.*%7Cheap.*
http://localhost:8761/metrics/systemload.*%7Cclasses.*%7Cuptime%7Cprocessors%7Cthreads.*
http://localhost:8761/metrics/httpsessions.*
http://localhost:8761/dump

	 */
// NEW END	
}
