package com.resources.optimize.service;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TargetAppMetricsService {

    private final RestTemplate restTemplate = new RestTemplate();

    public String fetchHealthStatus() {
        String targetAppHealthUrl = "http://localhost:8080/health";
        return restTemplate.getForObject(targetAppHealthUrl, String.class);
    }
}
