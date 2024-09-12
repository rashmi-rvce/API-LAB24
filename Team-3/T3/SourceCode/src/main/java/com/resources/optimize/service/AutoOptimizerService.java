package com.resources.optimize.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AutoOptimizerService {
    private final RestTemplate restTemplate;
    private final String actuatorUrl = "http://localhost:8080/actuator";

    @Autowired
    public AutoOptimizerService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void adjustThreadPool(int newSize) {
        // Use Spring Boot Actuator to dynamically adjust thread pool size
        restTemplate.postForObject(actuatorUrl + "/threadpool", newSize, Void.class);
    }
}
