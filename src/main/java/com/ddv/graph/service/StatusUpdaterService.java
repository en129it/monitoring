package com.ddv.graph.service;

import static java.util.Arrays.asList;

import java.io.Serializable;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.ddv.graph.model.Service;
import com.ddv.graph.model.ServiceStatusEnum;


public class StatusUpdaterService {
	private ScheduledFuture<?> scheduledTask;
	
	@Autowired
	private ServiceRepository serviceRepository;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private ThreadPoolTaskScheduler taskScheduler;
	
	@Value("${status.update.time.interval.in.sec}")
	private Integer updateTimeIntervalInSec;
	
	public StatusUpdaterService() {
	}
/*	
	@EventListener
	public void onApplicationReady(ApplicationReadyEvent event) {
		if ((scheduledTask==null) || (scheduledTask.isDone())) {
			scheduledTask = taskScheduler.scheduleAtFixedRate(new Runnable(){
				@Override
				public void run() {
					updateStatus();
				}
			}, updateTimeIntervalInSec*1000);
		}
	}

	@EventListener
	public void onContextClosed(ContextClosedEvent event) {
		if (scheduledTask!=null) {
			scheduledTask.cancel(true);
		}
	}

	private void updateStatus() {
		System.out.println("### Update service status");
		List<String> serviceIds = serviceRepository.getAllServiceIds();
		for (String serviceId : serviceIds) {
			System.out.println("### Update service status " + serviceId);
			Service service = serviceRepository.getService(serviceId);
			
			URI healthUrl = UriComponentsBuilder.fromUri(service.getHealthUrl()).build().toUri();
			
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

			ResponseEntity<?> response = restTemplate.exchange(healthUrl, HttpMethod.GET, new HttpEntity<Void>(headers), Object.class);
			service.updateStatus((response.getStatusCode().is2xxSuccessful()) ? ServiceStatusEnum.UP : ServiceStatusEnum.DOWN, System.currentTimeMillis());
		}
	}
	*/
}
